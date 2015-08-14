package zx.soft.sina.IO.core;

import java.io.IOException;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.CacheFactory;

public class IORedis implements SinaIO {

	private static Cache cache;

	public IORedis() {
		cache = CacheFactory.getInstance();
	}

	@Override
	public <T> void write(String key, T value) {
		cache.sadd(key, value.toString());
	}

	@Override
	public void close() throws IOException {
		cache.close();
	}

}
