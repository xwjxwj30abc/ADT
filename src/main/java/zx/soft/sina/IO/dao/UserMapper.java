package zx.soft.sina.IO.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.domain.PlcNetInfo;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;

public interface UserMapper {

	public void insertWeibo(Weibo weibo);

	public void insertUser(User user);

	public void insertPlcNetInfo(PlcNetInfo plcNetInfo);

	public int insertPlcClient(PlcClient plcClient);

	@Delete("DELETE  FROM plcClientNew WHERE Service_code=#{Service_code}")
	public int deletePlcClient(long Service_code);

	public int updatePlcClient(PlcClient plcClient);

	@Select("SELECT COUNT(*) FROM jdadt.plcClientNew WHERE Service_code=#{serviceCode}")
	public int existsServiceCode(long serviceCode);

}
