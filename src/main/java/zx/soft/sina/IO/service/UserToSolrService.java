package zx.soft.sina.IO.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.domain.SimpleUser;
import zx.soft.sina.IO.mapred.WriterSolr;

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
