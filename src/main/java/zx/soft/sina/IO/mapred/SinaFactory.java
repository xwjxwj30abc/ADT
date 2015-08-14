package zx.soft.sina.IO.mapred;

import java.net.MalformedURLException;

public class SinaFactory {

	public static SinaWriter createCDH5Writer() {
		return new WriterCDH5();
	};

	public static SinaWriter createMySQLWriter() {
		return new WriterMySQL();
	};

	public static SinaWriter createSolrWriter() throws MalformedURLException {
		return new WriterSolr();
	};

}
