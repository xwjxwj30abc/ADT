package zx.soft.sina.IO.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.User.Builder;
import zx.soft.sina.IO.domain.Weibo;
import zx.soft.sina.IO.util.JsonUtils;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.log.LogbackUtil;

public class RedisMQ {

	private static Logger logger = LoggerFactory.getLogger(RedisMQ.class);

	private static JedisPool pool;

	private static final ObjectMapper OBJECT_MAPPER = JsonUtils.getObjectMapper();

	public RedisMQ() {
		init();
	}

	private void init() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(256);
		poolConfig.setMinIdle(64);
		poolConfig.setMaxWaitMillis(10_000);
		poolConfig.setMaxTotal(1024);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTimeBetweenEvictionRunsMillis(30000);
		Properties props = ConfigUtil.getProps("cache-config.properties");
		pool = new JedisPool(poolConfig, props.getProperty("redis.mq.server"), Integer.parseInt(props
				.getProperty("redis.port")), 30_000, props.getProperty("redis.password"));
	}

	public synchronized static Jedis getJedis() {
		try {
			if (pool != null) {
				return pool.getResource();
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return null;
		}
	}

	/**
	 * 添加数据，members不能为空
	 */
	public synchronized void addRecord(String cacheKey, String... members) {
		Jedis jedis = getJedis();
		if (jedis == null) {
			return;
		}
		try {
			jedis.sadd(cacheKey, members);
		} catch (Exception e) {
			logger.error("Exception:{},Records'size={}.", LogbackUtil.expection2Str(e), members.length);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			// 这里很重要，一旦拿到的jedis实例使用完毕，必须要返还给池中
			if (jedis != null && jedis.isConnected())
				pool.returnResource(jedis);
		}
	}

	public synchronized void addRecord(String cacheKey, String member) {
		Jedis jedis = getJedis();
		if (jedis == null) {
			return;
		}
		try {
			jedis.sadd(cacheKey, member);
		} catch (Exception e) {
			logger.error("Exception:{}.", LogbackUtil.expection2Str(e));
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			// 这里很重要，一旦拿到的jedis实例使用完毕，必须要返还给池中
			if (jedis != null && jedis.isConnected())
				pool.returnResource(jedis);
		}
	}

	/**
	 * 获取集合大小
	 */
	public synchronized long getSetSize(String cacheKey) {
		long result = 0L;
		Jedis jedis = getJedis();
		if (jedis == null) {
			return result;
		}
		try {
			// 在事务和管道中不支持同步查询
			result = jedis.scard(cacheKey).longValue();
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			if (jedis != null && jedis.isConnected())
				pool.returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取数据
	 */
	public synchronized List<String> getRecords(String cacheKey) {
		List<String> records = new ArrayList<>();
		Jedis jedis = getJedis();
		if (jedis == null) {
			return records;
		}
		try {
			String value = jedis.spop(cacheKey);
			while (value != null) {
				records.add(value);
				value = jedis.spop(cacheKey);
			}
			logger.info("Records'size = {}", records.size());
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			if (jedis != null && jedis.isConnected())
				pool.returnResource(jedis);
		}
		return records;
	}

	/**
	 * 将数据从String映射到Object
	 */
	public List<Weibo> mapper(List<String> records) {
		List<Weibo> recordInfos = new ArrayList<>();
		for (String record : records) {
			try {
				recordInfos.add(OBJECT_MAPPER.readValue(record, Weibo.class));
			} catch (Exception e) {
				logger.error("Record:{}", record);
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			}
		}
		return recordInfos;
	}

	public void close() {
		// 程序关闭时，需要调用关闭方法
		pool.destroy();
	}

	public static void main(String[] args) {
		//		Builder builder1 = new Builder("weibo1", "weibo1", "weibo1", new Date());
		//		Weibo weibo1 = builder1.build();
		//		Builder builder2 = new Builder("weibo2", "weibo2", "weibo2", new Date());
		//		Weibo weibo2 = builder2.build();
		//		List<String> weibos = new ArrayList<>();
		//		weibos.add(JsonUtils.toJsonWithoutPretty(weibo1));
		//		weibos.add(JsonUtils.toJsonWithoutPretty(weibo2));
		RedisMQ redisMQ = new RedisMQ();
		Builder builder = new Builder(111, "111", "screen_name", "name", new Date());
		User user = builder.build();
		redisMQ.addRecord("sina_user", user.toString());
		//redisMQ.addRecord("sina_weibo", weibos.toArray(new String[weibos.size()]));
		//		List<Weibo> wei = redisMQ.mapper(redisMQ.getRecords("sina_weibo"));
		//		System.out.println(wei.get(0).toString());
		//System.out.println(wei.get(1).toString());
		redisMQ.close();
	}
}
