package zx.soft.sina.IO.mapred;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.CacheFactory;
import zx.soft.sina.IO.domain.SimpleUser;

public class WriterRedis implements SinaWriter {

	private static Logger logger = LoggerFactory.getLogger(WriterRedis.class);
	private static Cache cache;

	public WriterRedis() {
		cache = CacheFactory.getInstance();
	}

	@Override
	public void write(String key, SimpleUser user) {
		logger.info("add " + key + ":" + user + " to redis");
		cache.sadd(key, user.toString());
	}

	@Override
	public void close() {
		cache.close();
	}

	public String get(String key) {
		return cache.spop(key);
	}

}
