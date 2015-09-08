package zx.soft.sina.IO.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.core.IORedis;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;

@Service
public class RedisService {

	@Inject
	private IORedis iORedis;

	public <T> void insert(List<T> objects) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
	IllegalAccessException {
		if (objects.size() > 0) {
			if (objects.get(0) instanceof User) {
				for (T object : objects) {
					iORedis.write("sina_user", object);
				}

			}
			if (objects.get(0) instanceof Weibo) {
				for (T object : objects) {
					iORedis.write("sina_weibo", object);
				}
			}
		}
	}

	public void close() throws IOException {
		iORedis.close();
	}
}
