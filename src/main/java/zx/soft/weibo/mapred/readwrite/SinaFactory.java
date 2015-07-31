package zx.soft.weibo.mapred.readwrite;

import java.net.MalformedURLException;

public class SinaFactory {

	public static SinaWriter createCDH5Writer(String path) {
		return new WriterCDH5(path);
	};

	public static SinaWriter createMySQLWriter() {
		return new WriterMySQL();
	};

	public static SinaWriter createRedisWriter() {
		return new WriterRedis();
	};

	public static SinaWriter createSolrWriter() throws MalformedURLException {
		return new WriterSolr();
	};

}
