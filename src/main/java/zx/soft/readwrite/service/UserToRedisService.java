package zx.soft.readwrite.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.readwrite.domain.SimpleUser;
import zx.soft.weibo.mapred.readwrite.WriterRedis;

@Service
public class UserToRedisService {

	@Inject
	private WriterRedis writerRedis;

	public void insertUser(SimpleUser user) {
		writerRedis.write(user.getIdstr(), user);
	}

	public String getUser(String id) {
		return writerRedis.get(id);
	}

	public void close() {
		writerRedis.close();
	}

}
