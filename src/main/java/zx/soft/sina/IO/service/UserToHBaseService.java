package zx.soft.sina.IO.service;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.domain.SimpleUser;
import zx.soft.sina.IO.mapred.WriterHBase;

@Service
public class UserToHBaseService {

	@Inject
	WriterHBase writerHBase;

	public void insertUser(SimpleUser user) {
		writerHBase.write(user.getIdstr(), user);
	}

	public void close() {
		try {
			writerHBase.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
