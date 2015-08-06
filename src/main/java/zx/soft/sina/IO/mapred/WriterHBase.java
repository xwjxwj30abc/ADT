package zx.soft.sina.IO.mapred;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.hbase.api.core.HBaseClient;
import zx.soft.hbase.api.core.HBaseTable;
import zx.soft.hbase.api.core.HConn;
import zx.soft.sina.IO.domain.SimpleUser;
import zx.soft.sina.IO.domain.SimpleUser.Builder;

import com.google.protobuf.ServiceException;

public class WriterHBase implements SinaWriter {

	private static Logger logger = LoggerFactory.getLogger(WriterHBase.class);
	private static String columnFamily = "info";
	private HBaseTable table;

	public WriterHBase() throws IOException, ServiceException {
		//创建表,并建立hbase的连接
		HBaseClient client = new HBaseClient();
		if (!client.isTableExists("sina_simpleuser")) {
			client.createTable("sina_simpleuser", columnFamily);
		}
		client.close();
		try {
			this.table = new HBaseTable(HConn.getHConnection(), "sina_simpleuser");
		} catch (IOException e) {
			logger.error("创建HBaseTable实例错误");
			e.printStackTrace();
		}
	}

	@Override
	public void write(String key, SimpleUser user) {
		try {
			table.putObject(key, columnFamily, user);
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

	public static void main(String[] args) throws IOException, ServiceException {
		WriterHBase writerHBase = new WriterHBase();
		Builder builder = new Builder(115L, new Date().getTime());
		SimpleUser user = new SimpleUser(builder);
		writerHBase.write(user.getIdstr(), user);
		writerHBase.close();
	}
}
