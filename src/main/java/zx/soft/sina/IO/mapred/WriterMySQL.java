package zx.soft.sina.IO.mapred;

import javax.inject.Inject;

import zx.soft.sina.IO.dao.UserMapper;
import zx.soft.sina.IO.domain.SimpleUser;

public class WriterMySQL implements SinaWriter {

	@Inject
	private UserMapper userMapper;

	@Override
	public void write(String key, SimpleUser user) {
		userMapper.insertUser(user);
	}

	@Override
	public void close() {
	}

}
