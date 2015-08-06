package zx.soft.sina.IO.mapred;

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

import zx.soft.sina.IO.domain.SimpleUser;

public class WriterCDH5 implements SinaWriter {

	private final Logger logger = LoggerFactory.getLogger(WriterCDH5.class);
	private Writer writer;
	private static final Configuration conf = new Configuration();
	private String fileName;
	private final Text key = new Text();
	private final Text value = new Text();

	private final String path = "/user/hdfs/sina/user";

	public WriterCDH5() {
		createNewWriter();
	}

	@Override
	public void write(String key, SimpleUser user) {
		this.key.set(key);
		this.value.set(user.toString());
		try {
			writer.append(this.key, this.value);
		} catch (IOException e) {
			close();
		}

	}

	@Override
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createNewWriter() {
		try {
			this.fileName = this.path + "/" + System.currentTimeMillis();
			FileSystem fs = FileSystem.get(URI.create(""), conf);
			Path path = new Path(fileName);
			if (fs.exists(path)) {
				throw new RuntimeException(fileName);
			}
			logger.info("Create HDFS writer, fileName: " + fileName);
			writer = SequenceFile.createWriter(fs, conf, path, Text.class, Text.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
