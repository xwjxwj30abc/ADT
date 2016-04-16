package zx.soft.sina.IO.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import zx.soft.impala.adt.core.DataTrans;
import zx.soft.impala.adt.core.Tools;
import zx.soft.sina.IO.domain.AccessList;
import zx.soft.sina.IO.domain.AlertList;
import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.domain.VPNTraffic;
import zx.soft.sina.IO.domain.WanIpv4;
import zx.soft.sina.IO.util.ImpalaConnection;
import zx.soft.sina.IO.util.JsonUtils;
import zx.soft.utils.log.LogbackUtil;

@Service
public class ImpalaService {

	public static Logger logger = LoggerFactory.getLogger(ImpalaService.class);

	public AccessList getSpecAccess(String tableName, String rowkey) {
		String sqlStatement = "SELECT * FROM " + tableName + " WHERE rowkey =\'" + rowkey + "\'";
		AccessList accessList = null;
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						accessList = DataTrans.resultSet2Access(resultSet);
						long Service_code_tmp = resultSet.getLong(3);
						String Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
						if (Service_name_tmp == null) {
							DataTrans.updatePlcClientMap();
							Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
							if (Service_name_tmp == null) {
								Service_name_tmp = "";
							}
						}
						accessList.setService_name(Service_name_tmp);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		} catch (Exception e1) {
			throw new RuntimeException();
		}

		return accessList;
	}

	public AlertList getSpecAlert(String tableName, String rowkey) {
		String sqlStatement = "SELECT * FROM " + tableName + " WHERE rowkey =\'" + rowkey + "\'";
		AlertList alertList = null;
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						alertList = DataTrans.resultSet2AlertList(resultSet);
						long Service_code_tmp = resultSet.getLong(3);
						String Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
						if (Service_name_tmp == null) {
							DataTrans.updatePlcClientMap();
							Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
							if (Service_name_tmp == null) {
								Service_name_tmp = "";
							}
						}
						alertList.setService_name(Service_name_tmp);

					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		} catch (Exception e1) {
			logger.error(LogbackUtil.expection2Str(e1));
		}
		return alertList;
	}

	public List<AccessList> getAccessListQueryResult(String tableName, List<QueryParameters> queryParams,
			String orderBy, String order, int pageSize, int page) {
		String sqlStatement = Tools.getBasicSqlStatement(tableName, queryParams, orderBy, order, pageSize, page);
		List<AccessList> temp = new ArrayList<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						AccessList access = DataTrans.resultSet2Access(resultSet);
						long Service_code_tmp = resultSet.getLong(3);
						String Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
						if (Service_name_tmp == null) {
							DataTrans.updatePlcClientMap();
							Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
							if (Service_name_tmp == null) {
								Service_name_tmp = "";
							}
						}
						access.setService_name(Service_name_tmp);
						temp.add(access);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		} catch (Exception e1) {
			logger.error(LogbackUtil.expection2Str(e1));
		}
		return temp;
	}

	public List<AlertList> getAlertListQueryResult(String tableName, List<QueryParameters> queryParams, String orderBy,
			String order, int pageSize, int page) {
		String sqlStatement = Tools.getBasicSqlStatement(tableName, queryParams, orderBy, order, pageSize, page);
		List<AlertList> temp = new ArrayList<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						long Service_code_tmp = resultSet.getLong(3);
						AlertList alertList = DataTrans.resultSet2AlertList(resultSet);
						String Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
						if (Service_name_tmp == null) {
							DataTrans.updatePlcClientMap();
							Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
							if (Service_name_tmp == null) {
								Service_name_tmp = "";
							}
						}
						alertList.setService_name(Service_name_tmp);
						temp.add(alertList);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		} catch (Exception e1) {
			logger.error(LogbackUtil.expection2Str(e1));
		}
		return temp;
	}

	public int getSum(String tableName, List<QueryParameters> queryParams) {
		String condition = Tools.getPartSqlStatement(queryParams);
		String sqlStatement = "SELECT COUNT(*) FROM " + tableName + " WHERE " + condition;
		int s = 0;
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					s = resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return s;
	}

	//对查询的结果根据某字段类型进行分类统计
	public String getStat(String tableName, List<QueryParameters> queryParams, String groupBy, int limit) {
		String condition = Tools.getPartSqlStatement(queryParams);
		String sqlStatement = "SELECT " + groupBy + ",COUNT(*) AS number FROM " + tableName + " WHERE " + condition
				+ " GROUP BY " + groupBy + " ORDER BY number DESC LIMIT " + limit;
		Map<String, Integer> map = new HashMap<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					map.put(resultSet.getString(1), resultSet.getInt(2));
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return JsonUtils.toJson(map);
	}

	//根据国家类型group_by，返回具体的访问不同国家的数据记录数
	public Map getStat(String tableName, List<QueryParameters> queryParams, String groupBy) {
		String condition = Tools.getPartSqlStatement(queryParams);
		String sqlStatement = "SELECT " + groupBy + ",COUNT(*) AS number FROM " + tableName + " WHERE " + condition
				+ " GROUP BY " + groupBy;
		logger.info(sqlStatement);
		Map<String, Long> map = new HashMap<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					map.put(resultSet.getString(1), resultSet.getLong(2));
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return map;
	}

	//用户一段时间上网趋势统计分析
	public String getTrendency(String tableName, List<QueryParameters> queryParams, long start, long end) {
		String condition = Tools.getPartSqlStatement(queryParams);
		String sqlStatement = "SELECT unix_timestamp(to_date(from_unixtime(time+28800 )))-28800 AS DAY , COUNT(*) AS NUM FROM "
				+ tableName
				+ " WHERE time BETWEEN "
				+ start
				+ " AND "
				+ end
				+ " AND "
				+ condition
				+ " GROUP BY DAY ORDER BY NUM";
		logger.info(sqlStatement);
		Map<Long, Integer> map = new HashMap<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					map.put(resultSet.getLong(1), resultSet.getInt(2));
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return JsonUtils.toJson(map);
	}

	//过滤结果表统计分析
	//	public String getAlertStats(String tableName, List<QueryParameters> queryParams, String groupBy, int limit) {
	//		String condition = Tools.getPartSqlStatement(queryParams);
	//		String sqlStatement = "SELECT " + groupBy + " , COUNT(*) AS NUM FROM " + tableName + " WHERE " + condition
	//				+ " GROUP BY " + groupBy + " ORDER BY NUM DESC LIMIT " + limit;
	//		logger.info(sqlStatement);
	//		Map<String, Integer> map = new HashMap<>();
	//		try (Connection conn = ImpalaConnection.getConnection();
	//				Statement statement = conn.createStatement();
	//				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
	//			if (resultSet != null) {
	//				while (resultSet.next()) {
	//					if (!groupBy.equals("rule_id")) {
	//						map.put(resultSet.getString(1), resultSet.getInt(2));
	//					} else {
	//						if (resultSet.getString(1) == null) {
	//							map.put("ruleId_is_null", resultSet.getInt(2));
	//						} else {
	//							String rule_name = DataTrans.MAP.get(resultSet.getString(1));
	//							if (rule_name == null) {
	//								DataTrans.updateMap();
	//								rule_name = DataTrans.MAP.get(resultSet.getString(1));
	//								if (rule_name == null) {
	//									//当前库中不存在id对应的规则名称，抛出提示，暂时以id标识规则名称
	//									map.put(resultSet.getString(1), resultSet.getInt(2));
	//								} else {
	//									//map更新后，成功匹配规则id和name
	//									//map.put(rule_name.substring(1, rule_name.length() - 1), resultSet.getInt(2));
	//									map.put(rule_name, resultSet.getInt(2));
	//								}
	//							} else {
	//								map.put(rule_name, resultSet.getInt(2));
	//							}
	//						}
	//					}
	//
	//				}
	//			}
	//		} catch (SQLException e) {
	//			e.printStackTrace();
	//			logger.error(LogbackUtil.expection2Str(e));
	//		}
	//
	//		return JsonUtils.toJson(map);
	//	}

	//过滤结果表统计分析
	public String getAlertStats(String tableName, List<QueryParameters> queryParams, String groupBy, int limit) {
		String condition = Tools.getPartSqlStatement(queryParams);
		String sqlStatement = "SELECT " + groupBy + " , COUNT(*)  AS NUM FROM " + tableName + " WHERE " + condition
				+ " GROUP BY " + groupBy + " ORDER BY NUM DESC LIMIT " + limit;
		logger.info(sqlStatement);
		Map<String, Integer> map = new HashMap<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					String rule_name = DataTrans.MAP.get(resultSet.getString(1));
					if (rule_name == null) {
						DataTrans.updateMap();
						rule_name = DataTrans.MAP.get(resultSet.getString(1));
						if (rule_name == null) {
							//当前库中不存在id对应的规则名称，抛出提示，暂时以rowkey标识规则名称
							map.put(resultSet.getString(1), resultSet.getInt(2));
						} else {
							//map更新后，成功匹配规则id和name
							//map.put(rule_name.substring(1, rule_name.length() - 1), resultSet.getInt(2));
							map.put(rule_name, resultSet.getInt(2));
						}
					} else {
						map.put(rule_name, resultSet.getInt(2));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(LogbackUtil.expection2Str(e));
		}
		return JsonUtils.toJson(map);

	}

	//流量统计表
	public List<VPNTraffic> getTraffic(String tableName, List<QueryParameters> queryParams, String orderBy,
			String order, int pageSize, int page) {

		String sqlStatement = Tools.getBasicSqlStatement(tableName, queryParams, orderBy, order, pageSize, page);
		logger.info(sqlStatement);
		List<VPNTraffic> traffics = new ArrayList<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {

			if (resultSet != null) {
				while (resultSet.next()) {
					long Service_code_tmp = resultSet.getLong(6);
					String Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
					VPNTraffic traffic_tmp = DataTrans.resultSet2VPNTraffic(resultSet);
					if (Service_name_tmp == null) {
						DataTrans.updatePlcClientMap();
						Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
						if (Service_name_tmp == null) {
							//当前库中不存在设备名称，以空格标识
							traffic_tmp.setService_name("没有");
						} else {
							//更新后，成功获取设备名称
							traffic_tmp.setService_name(Service_name_tmp);
						}
					} else {
						traffic_tmp.setService_name(Service_name_tmp);
					}
					traffics.add(traffic_tmp);

				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return traffics;
	}

	//设备出口公网地址
	public List<WanIpv4> getWanIpv4(String tableName, List<QueryParameters> queryParams, String orderBy, String order,
			int pageSize, int page) {
		String sqlStatement = Tools.getBasicSqlStatement(tableName, queryParams, orderBy, order, pageSize, page);
		logger.info(sqlStatement);
		List<WanIpv4> wanIpv4s = new ArrayList<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					long Service_code_tmp = resultSet.getLong(5);
					String Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
					WanIpv4 wanIpv4_tmp = DataTrans.resultSet2WanIpv4(resultSet);
					if (Service_name_tmp == null) {
						DataTrans.updatePlcClientMap();
						Service_name_tmp = DataTrans.plcClientMAP.get(Service_code_tmp);
						if (Service_name_tmp == null) {
							//当前库中不存在设备名称，以空格标识
							wanIpv4_tmp.setService_name("");
						} else {
							//更新后，成功获取设备名称
							wanIpv4_tmp.setService_name(Service_name_tmp);
						}
					} else {
						wanIpv4_tmp.setService_name(Service_name_tmp);
					}
					wanIpv4s.add(wanIpv4_tmp);
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return wanIpv4s;
	}
}
