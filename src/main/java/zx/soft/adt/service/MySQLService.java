package zx.soft.adt.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jodd.util.URLDecoder;

import org.springframework.stereotype.Service;

import zx.soft.adt.core.ConstADT;
import zx.soft.adt.core.IOMySQL;
import zx.soft.adt.domain.Params;
import zx.soft.adt.domain.PlcClient;
import zx.soft.adt.domain.PlcNetInfo;
import zx.soft.adt.domain.Status;
import zx.soft.adt.util.MySQLConnection;

@Service
public class MySQLService {

	@Inject
	private IOMySQL iOMySQL;
	public static Map<Long, String> plcClientMAP = new HashMap<>();

	static {
		updatePlcClientMap();
	}

	/**
	 * 从mysql查询adt.plcClient表,获得service_code和service名称的对应关系
	 */
	public static void updatePlcClientMap() {

		String sqlStatement = "SELECT Service_code,Service_name FROM " + ConstADT.TABLE_PLCCLIENT;
		try (Connection conn = MySQLConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					if (resultSet.getLong(1) != 0 && resultSet.getString(2) != null) {
						plcClientMAP.put(resultSet.getLong(1), resultSet.getString(2));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Status insertPlcNetInfo(PlcNetInfo info) {
		return iOMySQL.insertPlcNetInfo(info);
	}

	public Status insertPlcClient(PlcClient plcClient) {
		return iOMySQL.insertPlcClient(plcClient);
	}

	public Status deletePlcClient(long Service_code) {
		return iOMySQL.deletePlcClient(Service_code);
	}

	public Status updatePlcClient(PlcClient plcClient) {
		return iOMySQL.updatePlcClient(plcClient);
	}

	public List<PlcClient> getPlcClientQueryResult(String tableName, Params p) {
		return iOMySQL.getPlcClientQueryResult(tableName, p);
	}

	public int getSum(String tableName, Params p) {
		return iOMySQL.getSum(tableName, p);
	}

	public PlcClient getSpecPlcClient(String tableName, long Service_code) {
		return iOMySQL.getSpecPlcClient(tableName, Service_code);
	}

	public List<Long> getMappingServiceCodeByServiceName(String tableName, String Service_name) {
		return iOMySQL.getMappingServiceCodeByServiceName(tableName, URLDecoder.decode(Service_name));
	}

	public Map initGEO(String tablename) {
		return iOMySQL.initGEO(tablename);
	}
}
