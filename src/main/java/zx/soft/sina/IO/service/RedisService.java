package zx.soft.sina.IO.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.core.IORedis;

@Service
public class RedisService {

	@Inject
	private IORedis iORedis;

	public <T> void insert(List<T> objects) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		for (T object : objects) {
			Field field = object.getClass().getDeclaredField("id");
			field.setAccessible(true);
			iORedis.write(field.get(object).toString(), object.toString());
		}
	}

	public void close() throws IOException {
		iORedis.close();
	}
}
