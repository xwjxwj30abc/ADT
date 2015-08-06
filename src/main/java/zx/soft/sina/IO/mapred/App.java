package zx.soft.sina.IO.mapred;

import java.io.IOException;
import java.util.Date;

import zx.soft.sina.IO.domain.SimpleUser;
import zx.soft.sina.IO.domain.SimpleUser.Builder;
import zx.soft.utils.json.JsonUtils;

public class App {

	public static void main(String[] args) throws IOException {
		SinaWriter writer = SinaFactory.createMySQLWriter();
		Builder builder = new Builder(115L, new Date().getTime());
		SimpleUser user = new SimpleUser(builder);
		System.out.println(JsonUtils.toJsonWithoutPretty(user));
		writer.write(user.getIdstr(), user);
		writer.close();
		//{"id":118,"idstr":"118","name":"","created_at":1438744698464}
	}
}
