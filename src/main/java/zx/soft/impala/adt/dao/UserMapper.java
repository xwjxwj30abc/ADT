package zx.soft.impala.adt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zx.soft.impala.adt.domain.IP2GEO;
import zx.soft.impala.adt.domain.PlcClient;
import zx.soft.impala.adt.domain.PlcNetInfo;

public interface UserMapper {

	public void insertPlcNetInfo(PlcNetInfo plcNetInfo);

	public int insertPlcClient(PlcClient plcClient);

	@Delete("DELETE  FROM plcClient WHERE Service_code=#{Service_code}")
	public int deletePlcClient(long Service_code);

	public int updatePlcClient(PlcClient plcClient);

	@Select("SELECT COUNT(*) FROM plcClient WHERE Service_code=#{serviceCode}")
	public int existsServiceCode(long serviceCode);

	@Select("SELECT COUNTRY,JD,WD FROM ${tablename} ")
	public List<IP2GEO> getGEO(@Param("tablename") String tablename);

}
