package zx.soft.sina.IO.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import zx.soft.sina.IO.domain.SimpleUser;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;

public interface UserMapper {

	public void insertSimpleUser(SimpleUser user);

	public void insertWeibo(Weibo weibo);

	@Insert("INSERT INTO `weibo` (`pic_urls`,`user`,`visible`,`darwin_tags`) VALUES (#{pic_urls},#{userId},#{visible},#{darwin_tags}) WHERE `uid`=#{weiboId}")
	public void insertSpecial(@Param("pic_urls") String pic_urls, @Param("userId") String userId,
			@Param("visible") String visible, @Param("darwin_tags") String darwin_tags, @Param("weiboId") String weiboId);

	public void insertUser(User user);

}
