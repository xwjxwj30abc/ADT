package zx.soft.sina.IO.core;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;

public class IOCDH5 implements SinaIO {

	private final Logger logger = LoggerFactory.getLogger(IOCDH5.class);
	private Writer writerUser;
	private Writer writerWeibo;
	private static final Configuration conf = new Configuration();
	private String fileNameUser;
	private String fileNameWeibo;
	private final Text key = new Text();
	private final Text value = new Text();

	private final String path = "/user/hdfs/sina";

	public IOCDH5() {
		createNewWriter();
	}

	@Override
	public void close() throws IOException {
		try {
			writerUser.close();
			writerWeibo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T> void write(String key, T value) {
		this.key.set(key);
		this.value.set(value.toString());
		try {
			if (value instanceof User) {
				writerUser.append(this.key, this.value);
			}
			if (value instanceof Weibo) {
				writerWeibo.append(this.key, this.value);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void createNewWriter() {
		try {
			this.fileNameUser = this.path + "/user/" + System.currentTimeMillis();
			this.fileNameWeibo = this.path + "/weibo/" + System.currentTimeMillis();
			FileSystem fs = FileSystem.get(URI.create(""), conf);
			Path pathUser = new Path(fileNameUser);
			if (fs.exists(pathUser)) {
				throw new RuntimeException(fileNameUser);
			}
			logger.info("Create HDFS writer, fileName: " + fileNameUser);
			writerUser = SequenceFile.createWriter(fs, conf, pathUser, Text.class, Text.class);
			Path pathWeibo = new Path(fileNameWeibo);
			if (fs.exists(pathWeibo)) {
				throw new RuntimeException(fileNameUser);
			}
			logger.info("Create HDFS writer, fileName: " + fileNameWeibo);
			writerWeibo = SequenceFile.createWriter(fs, conf, pathWeibo, Text.class, Text.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
