package zx.soft.weibo.mapred.readwrite;

import javax.inject.Inject;

import zx.soft.readwrite.dao.UserDao;
import zx.soft.readwrite.domain.SimpleUser;

public class WriterMySQL implements SinaWriter {

	@Inject
	private UserDao userDao;

	@Override
	public void write(String key, SimpleUser user) {
		userDao.insertUser(user);
	}

	@Override
	public void close() {
	}

}
