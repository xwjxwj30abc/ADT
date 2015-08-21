package zx.soft.sina.IO.core;

import java.io.IOException;

import javax.inject.Inject;

import zx.soft.sina.IO.dao.UserMapper;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;

public class IOMySQL implements SinaIO {

	@Inject
	private UserMapper userMapper;

	@Override
	public <T> void write(String key, T value) {
		if (value instanceof User) {
			User user = (User) value;
			userMapper.insertUser(user);
		}
		if (value instanceof Weibo) {
			Weibo weibo = (Weibo) value;
			userMapper.insertWeibo(weibo);
		}
	}

	@Override
	public void close() throws IOException {
		//
	}

}
