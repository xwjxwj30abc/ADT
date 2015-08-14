package zx.soft.sina.IO.mapred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zx.soft.sina.IO.domain.Weibo;
import zx.soft.sina.IO.domain.Weibo.Builder;
import zx.soft.utils.json.JsonUtils;

public class App {

	public static void main(String[] args) throws IOException {
		Builder builder1 = new Builder("weibo", "weibo", "weibo", new Date());
		Weibo user = builder1.build();
		List<Weibo> users = new ArrayList<>();
		users.add(user);
		System.out.println(JsonUtils.toJsonWithoutPretty(users));
		//post数据时，修改将对象转换为json的JsonUtis包中map的日期转换格式

	}
}
