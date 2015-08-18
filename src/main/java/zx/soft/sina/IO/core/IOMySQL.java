package zx.soft.sina.IO.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import zx.soft.sina.IO.dao.UserMapper;
import zx.soft.sina.IO.domain.SimpleUser;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;
import zx.soft.sina.IO.domain.Weibo.Builder;
import zx.soft.sina.IO.util.JsonUtils;

public class IOMySQL implements SinaIO {

	@Inject
	private UserMapper userMapper;

	@Override
	public <T> void write(String key, T value) {
		if (value instanceof SimpleUser) {
			SimpleUser user = (SimpleUser) value;
			userMapper.insertSimpleUser(user);
		}
		if (value instanceof User) {
			User user = (User) value;
			userMapper.insertUser(user);
		}
		if (value instanceof Weibo) {
			Weibo weibo = (Weibo) value;
			User user = weibo.getUser();
			if (user.getId() != 0) {
				userMapper.insertWeibo(weibo);
				System.out.println(weibo.getId());
				userMapper.insertSpecial(weibo.getPic_urls() == null ? "" : weibo.getPic_urls().toString(),
						user.getIdstr(), weibo.getVisible() == null ? "" : weibo.getVisible().toString(),
								weibo.getDarwin_tags() == null ? "" : weibo.getVisible().toString(), weibo.getId());
			}
		}
	}

	@Override
	public void close() throws IOException {
		//
	}

	public static void main(String[] args) {
		zx.soft.sina.IO.domain.User.Builder user = new zx.soft.sina.IO.domain.User.Builder(123, "123", "screen_name",
				"name", new Date());
		Builder builder1 = new Builder("weibo3", "weibo3", "weibo3", new Date()).setUser(user.build());
		Weibo weibo = builder1.build();
		List<Weibo> weibos = new ArrayList<>();
		weibos.add(weibo);
		System.out.println(JsonUtils.toJsonWithoutPretty(weibos));
		IOMySQL ioMysql = new IOMySQL();
		ioMysql.write("", weibo);

	}

}
