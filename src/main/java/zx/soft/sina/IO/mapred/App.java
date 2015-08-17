package zx.soft.sina.IO.mapred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zx.soft.sina.IO.domain.Weibo;
import zx.soft.sina.IO.domain.Weibo.Builder;
import zx.soft.sina.IO.util.JsonUtils;

public class App {

	public static void main(String[] args) throws IOException {
		Builder builder1 = new Builder("weibo3", "weibo3", "weibo3", new Date());
		Weibo weibo = builder1.build();
		List<Weibo> weibos = new ArrayList<>();
		weibos.add(weibo);
		System.out.println(JsonUtils.toJsonWithoutPretty(weibos));
		//post数据时，修改将对象转换为json的JsonUtis包中map的日期转换格式
		//IOSolr so = new IOSolr();
		//so.write("", weibo);

	}
}
