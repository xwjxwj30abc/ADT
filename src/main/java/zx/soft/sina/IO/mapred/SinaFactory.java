package zx.soft.sina.IO.mapred;


public class SinaFactory {

	public static SinaWriter createCDH5Writer() {
		return new WriterCDH5();
	};

	public static SinaWriter createMySQLWriter() {
		return new WriterMySQL();
	};

}
