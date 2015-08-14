package zx.soft.sina.IO.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.hbase.api.core.HBaseTable;
import zx.soft.hbase.api.core.HConn;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;

import com.google.protobuf.ServiceException;

public class IOHBase implements SinaIO {
	private static Logger logger = LoggerFactory.getLogger(IOHBase.class);
	private static final String[] COLUMNFAMILYS = { "user", "weibo" };
	private HBaseTable table;
	private static final String TABLENAME = "sina";

	public IOHBase() throws IOException, ServiceException {
		//创建表,并建立hbase的连接
		//		HBaseClient client = new HBaseClient();
		//		if (!client.isTableExists(TABLENAME)) {
		//			client.createTable(TABLENAME, COLUMNFAMILYS);
		//		}
		//		client.close();
		try {
			this.table = new HBaseTable(HConn.getHConnection(), TABLENAME);
			logger.info("创建HBaseTable实例成功");
		} catch (IOException e) {
			logger.error("创建HBaseTable实例错误");
			e.printStackTrace();
		}
	}

	@Override
	public <T> void write(String key, T value) {
		try {
			if (value instanceof User) {
				table.putObject(key, COLUMNFAMILYS[0], value);
				logger.info("add to user");
			}
			if (value instanceof Weibo) {
				table.putObject(key, COLUMNFAMILYS[1], value);
				logger.info("add to weibo");
			}

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
		table.close();
	}

}
