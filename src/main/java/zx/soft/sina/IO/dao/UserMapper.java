package zx.soft.sina.IO.dao;

import org.apache.ibatis.annotations.Delete;

import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.domain.PlcNetInfo;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;

public interface UserMapper {

	public void insertWeibo(Weibo weibo);

	public void insertUser(User user);

	public void insertPlcNetInfo(PlcNetInfo plcNetInfo);

	public void insertPlcClient(PlcClient plcClient);

	@Delete("DELETE  FROM plcClient WHERE Service_code=#{Service_code}")
	public void deletePlcClient(long Service_code);

	public void updatePlcClient(PlcClient plcClient);

}
