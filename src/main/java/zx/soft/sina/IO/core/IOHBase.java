package zx.soft.sina.IO.core;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.hbase.api.core.HBaseClient;
import zx.soft.hbase.api.core.HBaseTable;
import zx.soft.hbase.api.core.HConn;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;
import zx.soft.sina.IO.util.Constant;

import com.google.protobuf.ServiceException;

public class IOHBase implements SinaIO {

	private static Logger logger = LoggerFactory.getLogger(IOHBase.class);
	private HConnection conn;

	public IOHBase() throws IOException, ServiceException {
		//创建表
		HBaseClient client = new HBaseClient();
		if (!client.isTableExists(Constant.HISTORY_WEIBO_TABLE)) {
			client.createTable(Constant.HISTORY_WEIBO_TABLE, Constant.HISTORY_WEIBO_CF);
		}
		if (!client.isTableExists(Constant.LASTEST_WEIBO_TABLE)) {
			client.createTable(Constant.LASTEST_WEIBO_TABLE, Constant.LASTEST_WEIBO_CF);
		}
		if (!client.isTableExists(Constant.USER_TABLE)) {
			client.createTable(Constant.USER_TABLE, Constant.USER_CF);
		}
		if (!client.isTableExists(Constant.USER_SCORE_TABLE)) {
			client.createTable(Constant.USER_SCORE_TABLE, Constant.USER_SCORE_CF);
		}
		client.close();
		//建立hbase的连接
		conn = HConn.getHConnection();
	}

	@Override
	public <T> void write(String key, T value) {
		try {
			if (value instanceof User) {
				HBaseTable user = new HBaseTable(conn, Constant.USER_TABLE);
				user.putObject(key, Constant.USER_CF, value);
				logger.info("add to user");
				user.close();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void insertUsers(List<User> users) {
		try {
			HBaseTable user = new HBaseTable(conn, Constant.USER_TABLE);
			for (User use : users) {
				user.putObject(use.getIdstr(), Constant.USER_CF, use);
			}
			user.execute();
			logger.info("add to user " + users.size());
			user.close();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void close() throws IOException {
		conn.close();
	}

	public void insertUserScore(Map<String, String> ids_scores) {
		try {
			HBaseTable user_score = new HBaseTable(conn, Constant.USER_SCORE_TABLE);
			for (Map.Entry<String, String> entry : ids_scores.entrySet()) {
				user_score.put(entry.getKey(), Constant.USER_SCORE_CF, Constant.USER_SCORE_Q, entry.getValue());
			}
			user_score.execute();
			logger.info("add to user_score " + ids_scores.size());
			user_score.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertLastestWeido(List<Weibo> weibos) {
		try {
			HBaseTable lastest_weibo = new HBaseTable(conn, Constant.LASTEST_WEIBO_TABLE);
			for (Weibo weibo : weibos) {
				transWeibo(lastest_weibo, weibo, Constant.LASTEST_WEIBO_CF);
			}
			lastest_weibo.execute();
			logger.info("add to lastest weibo " + weibos.size());
			lastest_weibo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertHistoryWeido(List<Weibo> weibos) {
		try {
			HBaseTable history_weibo = new HBaseTable(conn, Constant.HISTORY_WEIBO_TABLE);
			for (Weibo weibo : weibos) {
				transWeibo(history_weibo, weibo, Constant.HISTORY_WEIBO_CF);
			}
			history_weibo.execute();
			logger.info("add to history weibo " + weibos.size());
			history_weibo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getHistoryWeiboCountByTime(long timeStamp) {
		Result result = null;
		byte[] res = null;
		try {
			long minStamp = (timeStamp / (3600_000 * 24)) * 3600_000 * 24;
			long maxStamp = (timeStamp / 3600_000) * 3600_000;
			HBaseTable count = new HBaseTable(conn, Constant.DATA_COUNT);
			result = count.get(String.valueOf(minStamp), Constant.DATA_COUNT_CF, String.valueOf(maxStamp));
			res = result.getValue(Bytes.toBytes(Constant.DATA_COUNT_CF), Bytes.toBytes(String.valueOf(maxStamp)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(res);
	}

	private void transWeibo(HBaseTable table, Weibo weibo, String cf) {
		try {
			table.put(weibo.getIdstr(), cf, "id", weibo.getId());
			table.put(weibo.getIdstr(), cf, "mid", weibo.getMid());
			table.put(weibo.getIdstr(), cf, "idstr", weibo.getIdstr());
			table.put(weibo.getIdstr(), cf, "created_at", String.valueOf(weibo.getCreated_at().getTime()));
			table.put(weibo.getIdstr(), cf, "text", weibo.getText());
			table.put(weibo.getIdstr(), cf, "source_allowclick", String.valueOf(weibo.getSource_allowclick()));
			table.put(weibo.getIdstr(), cf, "source_type", String.valueOf(weibo.getSource_type()));
			table.put(weibo.getIdstr(), cf, "source", weibo.getSource());
			table.put(weibo.getIdstr(), cf, "favorited", String.valueOf(weibo.isFavorited()));
			table.put(weibo.getIdstr(), cf, "truncated", String.valueOf(weibo.isTruncated()));
			table.put(weibo.getIdstr(), cf, "in_reply_to_status_id", weibo.getIn_reply_to_status_id());
			table.put(weibo.getIdstr(), cf, "in_reply_to_user_id", weibo.getIn_reply_to_user_id());
			table.put(weibo.getIdstr(), cf, "in_reply_to_screen_name", weibo.getIn_reply_to_screen_name());
			table.put(weibo.getIdstr(), cf, "user_id", weibo.getUser().getIdstr());
			table.put(weibo.getIdstr(), cf, "user_screen_name", weibo.getUser().getName());
			table.put(weibo.getIdstr(), cf, "reposts_count", String.valueOf(weibo.getReposts_count()));
			table.put(weibo.getIdstr(), cf, "comments_count", String.valueOf(weibo.getComments_count()));
			table.put(weibo.getIdstr(), cf, "attitudes_count", String.valueOf(weibo.getAttitudes_count()));
			table.put(weibo.getIdstr(), cf, "mlevel", String.valueOf(weibo.getMlevel()));
			table.put(weibo.getIdstr(), cf, "owid", String.valueOf(weibo.getOwid()));
			table.put(weibo.getIdstr(), cf, "ousername", String.valueOf(weibo.getOusername()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
