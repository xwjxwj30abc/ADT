package zx.soft.weibo.mapred.readwrite;

import java.io.IOException;
import java.util.Date;

import zx.soft.readwrite.domain.SimpleUser;
import zx.soft.readwrite.domain.SimpleUser.Builder;
import zx.soft.utils.json.JsonUtils;

public class App {

	public static void main(String[] args) throws IOException {
		SinaWriter writer = SinaFactory.createCDH5Writer("/user/hdfs/sina/user");
		Builder builder = new Builder(114L, new Date().getTime());
		SimpleUser user = new SimpleUser(builder);
		System.out.println(JsonUtils.toJsonWithoutPretty(user));
		writer.write(user.getIdstr(), user);
		writer.close();
	}
}
