package zx.soft.readwrite.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.readwrite.domain.SimpleUser;
import zx.soft.weibo.mapred.readwrite.WriterSolr;

@Service
public class UserToSolrService {

	@Inject
	private WriterSolr writerSolr;

	public void insertUser(SimpleUser user) {
		writerSolr.write(user.getIdstr(), user);
	}

	public void close() {
		writerSolr.close();
	}
}
