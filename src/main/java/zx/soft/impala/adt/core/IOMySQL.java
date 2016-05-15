package zx.soft.impala.adt.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.impala.adt.dao.UserMapper;
import zx.soft.impala.adt.domain.IP2GEO;
import zx.soft.impala.adt.domain.Params;
import zx.soft.impala.adt.domain.PlcClient;
import zx.soft.impala.adt.domain.PlcNetInfo;
import zx.soft.impala.adt.domain.Status;
import zx.soft.impala.adt.util.MySQLConnection;

public class IOMySQL {

	public static Logger logger = LoggerFactory.getLogger(IOMySQL.class);

	@Inject
	private UserMapper userMapper;

	public Map initGEO(String tablename) {
		Map<String, IP2GEO> map = new HashMap<>();
		List<IP2GEO> lists = userMapper.getGEO(tablename);
		if (lists != null && lists.size() > 0) {
			for (IP2GEO ip2GEO : lists) {
				map.put(ip2GEO.getCOUNTRY(), ip2GEO);
			}
		}
		return map;
	}

	public Status insertPlcNetInfo(PlcNetInfo plcNetInfo) {
		userMapper.insertPlcNetInfo(plcNetInfo);
		return new Status("1", "insert successfully");
	}

	public Status insertPlcClient(PlcClient plcClient) {
		int success = userMapper.existsServiceCode(plcClient.getService_code());
		Status st = new Status();
		if (success == 1) {
			logger.info("表中存在该设备编码，不可插入");
			st.setErrorCode("2");
			st.setErrorMessage("表中存在该设备编码，请更换设备编码.");
		} else if (success == 0) {
			logger.info("表中不存在该设备编码，可插入.");
			try {
				userMapper.insertPlcClient(plcClient);
				st.setErrorCode("1");
				st.setErrorMessage("插入成功.");
			} catch (RuntimeException e) {
				st.setErrorCode("2");
				st.setErrorMessage(e.getCause().getMessage());
			}

		}
		return st;
	}

	public Status deletePlcClient(long Service_code) {
		Status st = new Status();
		int success = userMapper.deletePlcClient(Service_code);
		if (success == 0) {
			st.setErrorCode("2");
			st.setErrorMessage("不存在该设备编码.");
		} else {
			st.setErrorCode("1");
			st.setErrorMessage("删除 " + Service_code + " 成功.");
		}
		return st;
	}

	public Status updatePlcClient(PlcClient plcClient) {
		Status st = new Status();
		int success = 0;
		try {
			success = userMapper.updatePlcClient(plcClient);
			if (success == 0) {
				st.setErrorCode("2");
				st.setErrorMessage("不存在该设备编码对应的记录，无法更新.");
			} else {
				st.setErrorCode("1");
				st.setErrorMessage("更新成功.");
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			st.setErrorCode("2");
			st.setErrorMessage(e.getCause().getMessage());
		}
		return st;
	}

	public List<PlcClient> getPlcClientQueryResult(String tableName, Params p) {
		List<PlcClient> temp = new ArrayList<>();
		String sqlStatement = SqlStatementBuilder.getBasicSqlStatement(tableName, p);
		try (Connection conn = MySQLConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						temp.add(DataTrans.resultSet2PlcClient(resultSet));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return temp;
	}

	public int getSum(String tableName, Params p) {
		String condition = SqlStatementBuilder.getPartSqlStatement(p.getQueryParameters());
		String sqlStatement = "SELECT COUNT(*) FROM " + tableName + condition;
		int s = 0;
		try (Connection conn = MySQLConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					s = resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	public PlcClient getSpecPlcClient(String tableName, long Service_code) {
		String sqlStatement = "SELECT * FROM " + tableName + " WHERE Service_code = " + Service_code;
		PlcClient plcClient = null;
		try (Connection conn = MySQLConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						plcClient = DataTrans.resultSet2PlcClient(resultSet);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return plcClient;
	}

	public List<Long> getMappingServiceCodeByServiceName(String tableName, String Service_name) {
		String sqlStatement = "SELECT Service_code FROM " + tableName + " WHERE Service_name LIKE  \'%" + Service_name
				+ "%\'";
		logger.info(sqlStatement);
		List<Long> Service_codes = new ArrayList<>();
		try (Connection conn = MySQLConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						Service_codes.add(resultSet.getLong(1));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		logger.info(Service_codes.size() + "");
		return Service_codes;
	}
}
