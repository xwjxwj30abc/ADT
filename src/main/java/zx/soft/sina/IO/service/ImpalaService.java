package zx.soft.sina.IO.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import zx.soft.sina.IO.domain.AccessList;
import zx.soft.sina.IO.domain.AlertList;
import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.util.Constant;
import zx.soft.sina.IO.util.ImpalaConnection;

@Service
public class ImpalaService {

	private static Logger logger = LoggerFactory.getLogger(ImpalaService.class);
	private static Map<Integer, String> operation = new HashMap<>();
	static {
		operation.put(-1, "<");
		operation.put(0, "=");
		operation.put(1, ">");
		operation.put(2, " BETWEEN ");
	}

	public List<AccessList> queryAccessList(String tableName, List<QueryParameters> queryParams, String orderBy,
			String order, int pageSize, int page) {
		return getAccessListQueryResult(getSqlStatement(tableName, queryParams, orderBy, order, pageSize, page));
	}

	public List<AlertList> queryAlertList(String tableName, List<QueryParameters> queryParams, String orderBy,
			String order, int pageSize, int page) {
		return getAlertListQueryResult(getSqlStatement(tableName, queryParams, orderBy, order, pageSize, page));
	}

	public List<PlcClient> queryPlcClient(String tableName, List<QueryParameters> queryParams, String orderBy,
			String order, int pageSize, int page) {
		return getPlcClientQueryResult(getSqlStatement(tableName, queryParams, orderBy, order, pageSize, page));
	}

	public List<AccessList> getAccessListQueryResult(String sqlStatement) {
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			List<AccessList> temp = new ArrayList<>();
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						AccessList result = new AccessList();
						result.setId(resultSet.getLong(2));
						result.setService_code(resultSet.getLong(3));
						result.setNet_ending_ip(resultSet.getLong(4));
						result.setNet_ending_name(resultSet.getString(5));
						result.setTime(resultSet.getLong(6));
						result.setNet_ending_mac(resultSet.getLong(7));
						result.setDestination_ip(resultSet.getLong(8));
						result.setPort(resultSet.getInt(9));
						result.setService_type(resultSet.getInt(10));
						result.setKeyword1(resultSet.getString(11));
						result.setKeyword2(resultSet.getString(12));
						result.setKeyword3(resultSet.getString(13));
						result.setMac(resultSet.getLong(14));
						result.setSource_port(resultSet.getInt(15));
						result.setNet_ending_ipv6(resultSet.getString(16));
						result.setDestination_ipv6(resultSet.getString(17));
						result.setKeyword11(resultSet.getInt(18));
						result.setKeyword12(resultSet.getInt(19));
						result.setKeyword13(resultSet.getInt(20));
						result.setKeyword14(resultSet.getInt(21));
						result.setKeyword15(resultSet.getLong(22));
						result.setKeyword21(resultSet.getString(23));
						result.setKeyword22(resultSet.getString(24));
						result.setKeyword23(resultSet.getString(25));
						result.setKeyword24(resultSet.getString(26));
						result.setKeyword25(resultSet.getString(27));
						result.setJd(resultSet.getDouble(28));
						result.setWd(resultSet.getDouble(29));
						result.setCountry_name(resultSet.getString(30));
						result.setCjsj(new Date(resultSet.getLong(31)));
						temp.add(result);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return temp;
		} catch (SQLException e) {
			throw new RuntimeException();
		} catch (Exception e1) {
			throw new RuntimeException();
		}
	}

	public List<AlertList> getAlertListQueryResult(String sqlStatement) {
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			List<AlertList> temp = new ArrayList<>();
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						AlertList result = new AlertList();
						result.setId(resultSet.getLong(2));
						result.setService_code(resultSet.getLong(3));
						result.setRule_id(resultSet.getString(4));
						result.setDestination_ip(resultSet.getLong(5));
						result.setNet_ending_ip(resultSet.getLong(6));
						result.setNet_ending_mac(resultSet.getLong(7));
						result.setDestination_ipv6(resultSet.getString(8));
						result.setNet_ending_ipv6(resultSet.getString(9));
						result.setMatching_time(resultSet.getLong(10));
						result.setService_type(resultSet.getInt(11));
						result.setKeyword1(resultSet.getString(12));
						result.setKeyword2(resultSet.getString(13));
						result.setKeyword3(resultSet.getString(14));
						result.setUser_name(resultSet.getString(15));
						result.setCertificate_type(resultSet.getString(16));
						result.setCertificate_code(resultSet.getString(17));
						result.setOrg_name(resultSet.getString(18));
						result.setCountry(resultSet.getString(19));
						result.setJd(resultSet.getDouble(20));
						result.setWd(resultSet.getDouble(21));
						result.setCountry_name(resultSet.getString(22));
						temp.add(result);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return temp;
		} catch (SQLException e) {
			throw new RuntimeException();
		} catch (Exception e1) {
			throw new RuntimeException();
		}
	}

	public List<PlcClient> getPlcClientQueryResult(String sqlStatement) {
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			List<PlcClient> temp = new ArrayList<>();
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						PlcClient result = new PlcClient();
						result.setId(resultSet.getLong(2));
						result.setService_code(resultSet.getLong(3));
						result.setService_name(resultSet.getString(4));
						result.setAddress(resultSet.getString(5));
						result.setZip(resultSet.getString(6));
						result.setPrincipal(resultSet.getString(7));
						result.setPrincipal_tel(resultSet.getString(8));
						result.setInfor_man(resultSet.getString(9));
						result.setInfor_man_tel(resultSet.getString(10));
						result.setInfor_man_email(resultSet.getString(11));
						result.setProducer_code(resultSet.getInt(12));
						result.setStatus(resultSet.getInt(13));
						result.setEnding_number(resultSet.getInt(14));
						result.setServer_number(resultSet.getInt(15));
						result.setIp(resultSet.getString(16));
						result.setNet_type(resultSet.getInt(17));
						result.setPractitioner_number(resultSet.getInt(18));
						result.setNet_monitor_department(resultSet.getString(19));
						result.setNet_monitor_man(resultSet.getString(20));
						result.setNet_monitor_man_tel(resultSet.getString(21));
						result.setRemark(resultSet.getString(22));
						result.setNewSystem(resultSet.getInt(23));
						result.setUnitNo(resultSet.getInt(24));
						result.setSessionID(resultSet.getString(25));
						result.setUdpHost(resultSet.getString(26));
						result.setUdpPort(resultSet.getInt(27));
						result.setUdpVer(resultSet.getString(28));
						result.setComputerOnline(resultSet.getInt(29));
						result.setClientTime(resultSet.getLong(30));
						result.setLogDays(resultSet.getInt(31));
						result.setCommStatus(resultSet.getInt(32));
						result.setCommNormal(resultSet.getInt(33));
						result.setCommTiming(resultSet.getInt(34));
						result.setAlertLogAttr(resultSet.getInt(35));
						result.setUserLogAttr(resultSet.getInt(36));
						result.setDefaultAccessRule(resultSet.getInt(37));
						result.setDevice_ipv4(resultSet.getString(38));
						result.setDevice_ipv6(resultSet.getString(39));
						result.setDevice_port(resultSet.getInt(40));
						result.setUdp_online(resultSet.getInt(41));
						result.setDevice_serial(resultSet.getString(42));
						result.setDevice_version(resultSet.getString(43));
						result.setDevice_flow1(resultSet.getLong(44));
						result.setDevice_flow2(resultSet.getLong(45));
						result.setDevice_note(resultSet.getString(46));
						temp.add(result);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return temp;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} catch (Exception e1) {
			throw new RuntimeException();
		}
	}

	private String getSqlStatement(String tableName, List<QueryParameters> queryParams, String orderBy, String order,
			int pageSize, int page) {
		String sqlStatement = null;
		StringBuilder condition = new StringBuilder();
		if (queryParams.size() > 0) {

			if (queryParams.get(0).getOpera() == 2) {
				condition.append(queryParams.get(0).getField()).append(operation.get(queryParams.get(0).getOpera()))
				.append(queryParams.get(0).getValue().split(",")[0]).append(" AND ")
				.append(queryParams.get(0).getValue().split(",")[1]);
			} else {
				condition.append(queryParams.get(0).getField()).append(operation.get(queryParams.get(0).getOpera()));
				if (Constant.StringFields.contains(queryParams.get(0).getField())
						&& !queryParams.get(0).getField().equals("id")) {
					condition.append("\'").append(queryParams.get(0).getValue()).append("\'");
				} else {
					condition.append(queryParams.get(0).getValue());
				}
			}

			for (int j = 1; j < queryParams.size(); j++) {
				if (queryParams.get(j).getOpera() == 2) {
					condition.append(queryParams.get(j).getField()).append(queryParams.get(j).getOpera())
					.append(queryParams.get(j).getValue().split(",")[0]).append(" AND ")
					.append(queryParams.get(j).getValue().split(",")[1]);
				} else {
					condition.append(" AND ").append(queryParams.get(j).getField())
					.append(operation.get(queryParams.get(j).getOpera()));
					if (Constant.StringFields.contains(queryParams.get(j).getField())
							&& !queryParams.get(j).getField().equals("id")) {
						condition.append("\"").append(queryParams.get(j).getValue()).append("\"");
					} else {
						condition.append(queryParams.get(j).getValue());
					}

				}
			}
		}
		sqlStatement = "SELECT * FROM " + tableName + " WHERE " + condition + " ORDER BY " + orderBy + " " + order
				+ " LIMIT " + pageSize + " OFFSET " + page;
		logger.info(sqlStatement);
		return sqlStatement;
	}

	public static void main(String[] args) {
		ImpalaService service = new ImpalaService();
		List<QueryParameters> queryParams = new ArrayList<>();
		QueryParameters param = new QueryParameters();
		param.setField("Device_serial");
		param.setOpera(0);
		param.setValue("ZX-HB02152020515");
		queryParams.add(param);
		String sqlStatement = service.getSqlStatement("parquet_compression.plcclient", queryParams, "id", "ASC", 10, 0);
		System.out.println(service.getPlcClientQueryResult(sqlStatement).get(0));
	}
}
