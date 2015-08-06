package zx.soft.sina.IO.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.domain.SimpleUser;
import zx.soft.sina.IO.mapred.WriterMySQL;

@Service
public class UserToMySQLService {

	@Inject
	private WriterMySQL writerMySQL;

	public void insertUser(SimpleUser user) {
		writerMySQL.write("", user);
	}

}
