package zx.soft.sina.IO.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.core.IOHBase;

@Service
public class HBaseService {

	@Inject
	private IOHBase iOHBase;

	public <T> void insert(List<T> objects) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		for (T object : objects) {
			Field field = object.getClass().getDeclaredField("id");
			field.setAccessible(true);
			iOHBase.write(field.get(object).toString(), object);
		}
	}

	public void close() throws IOException {
		iOHBase.close();
	}
}
