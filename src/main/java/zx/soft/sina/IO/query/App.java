package zx.soft.sina.IO.query;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.CacheFactory;
import zx.soft.utils.http.ClientDao;
import zx.soft.utils.http.HttpClientDaoImpl;
import zx.soft.weibo.mapred.utils.Constant;
import zx.soft.weibo.sina.common.JsonNodeUtils;

public class App {

	public static final String ACTIVE_USER = "sent:sina:activeUser";

	public List<String> getActiveUserId(int from) {
		ClientDao clientDao = new HttpClientDaoImpl();
		String jsonStr = clientDao.doGet(Constant.USER_SCORE_GET + from);
		System.out.println(jsonStr);
		return parseJsonTreeIDs(jsonStr);
	}

	private List<String> parseJsonTreeIDs(String jsonStr) {
		List<String> result = new ArrayList<>();
		JsonNode node = JsonNodeUtils.getJsonNode(jsonStr);
		for (JsonNode no : node) {
			result.add(no.toString().substring(1, no.toString().length() - 1));
		}
		return result;
	}

	public static void main(String[] args) {
		App a = new App();
		Cache cache = CacheFactory.getInstance();
		List<String> users = a.getActiveUserId(0);
		String[] strarray = users.toArray(new String[0]);
		cache.sadd(ACTIVE_USER, strarray);
	}

}
