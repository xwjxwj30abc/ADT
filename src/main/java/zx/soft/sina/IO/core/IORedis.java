package zx.soft.sina.IO.core;

import java.io.IOException;

public class IORedis implements SinaIO {

	private RedisMQ redisMQ;

	public IORedis() {
		redisMQ = new RedisMQ();
	}

	@Override
	public <T> void write(String key, T value) {
		redisMQ.addRecord(key, value.toString());
	}

	@Override
	public void close() throws IOException {
		redisMQ.close();
	}

}
