package zx.soft.sina.IO.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.core.IOHBase;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;

@Service
public class HBaseService {

	@Inject
	private IOHBase iOHBase;

	public void insertUsers(List<User> users) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
	IllegalAccessException {
		iOHBase.insertUsers(users);
	}

	public void inserUserScores(Map<String, String> ids_scores) {
		iOHBase.insertUserScore(ids_scores);
	}

	public void insertlastestWeibos(List<Weibo> weibos) {
		iOHBase.insertLastestWeido(weibos);

	}

	public void insertHistoryWeibos(List<Weibo> weibos) {
		iOHBase.insertHistoryWeido(weibos);

	}

	public String getHistoryWeiboCount(long timeStamp) {
		return iOHBase.getHistoryWeiboCountByTime(timeStamp);

	}

	public void close() throws IOException {
		iOHBase.close();
	}

}
