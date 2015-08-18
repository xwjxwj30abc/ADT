package zx.soft.sina.IO.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.core.IOCDH5;

@Service
public class CDH5Service {
	@Inject
	private IOCDH5 iOCDH5;

	public <T> void insertUser(List<T> values) {

		if (values.size() > 0) {
			try {
				for (T value : values) {
					Field field = value.getClass().getDeclaredField("id");
					field.setAccessible(true);
					String key = field.get(value).toString();
					iOCDH5.write(key, value);
				}

			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

	}

	public void close() {
		try {
			iOCDH5.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
