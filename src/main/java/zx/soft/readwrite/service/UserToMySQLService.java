package zx.soft.readwrite.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.readwrite.domain.SimpleUser;
import zx.soft.weibo.mapred.readwrite.WriterMySQL;

@Service
public class UserToMySQLService {

	@Inject
	private WriterMySQL writerMySQL;

	public void insertUser(SimpleUser user) {
		writerMySQL.write("", user);
	}

}
