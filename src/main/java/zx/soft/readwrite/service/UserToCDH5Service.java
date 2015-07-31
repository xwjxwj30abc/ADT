package zx.soft.readwrite.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.readwrite.domain.SimpleUser;
import zx.soft.weibo.mapred.readwrite.WriterCDH5;

@Service
public class UserToCDH5Service {

	@Inject
	private WriterCDH5 writerCDH5;

	public void insertUser(SimpleUser user) {
		writerCDH5.write(user.getIdstr(), user);
	}

	public void close() {
		writerCDH5.close();
	}
}
