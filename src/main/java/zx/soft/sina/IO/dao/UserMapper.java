package zx.soft.sina.IO.dao;

import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;

public interface UserMapper {

	public void insertWeibo(Weibo weibo);

	public void insertUser(User user);

}
