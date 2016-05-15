package zx.soft.adt.service;

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

import zx.soft.adt.core.DataTrans;
import zx.soft.adt.core.SqlStatementBuilder;
import zx.soft.adt.domain.AccessList;
import zx.soft.adt.domain.AlertList;
import zx.soft.adt.domain.HotPlugLog;
import zx.soft.adt.domain.Params;
import zx.soft.adt.domain.QueryParameters;
import zx.soft.adt.domain.Stat;
import zx.soft.adt.domain.VPNTraffic;
import zx.soft.adt.domain.WanIpv4;
import zx.soft.adt.util.ImpalaConnection;
import zx.soft.utils.log.LogbackUtil;

@Service
public class ImpalaService {

	public static Logger logger = LoggerFactory.getLogger(ImpalaService.class);

	//根据行键获取单个对象
	public <T> Object getSpecObject(String tableName, String rowkey, Class<T> t) {
		Object object = new Object();
		String sqlStatement = "SELECT * FROM " + tableName + " WHERE rowkey =\'" + rowkey + "\'";
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				if (t.equals(AccessList.class)) {
					AccessList accessList = new AccessList();
					while (resultSet.next()) {
						accessList = DataTrans.resultSet2Access(resultSet);
						long Service_code_tmp = resultSet.getLong(3);
						String Service_name_tmp = this.getServiceNameByServiceCode(Service_code_tmp);
						accessList.setService_name(Service_name_tmp);
					}
					return accessList;
				} else if (t.equals(AlertList.class)) {
					AlertList alertList = new AlertList();
					while (resultSet.next()) {
						alertList = DataTrans.resultSet2AlertList(resultSet);
						long Service_code_tmp = resultSet.getLong(6);
						String Service_name_tmp = this.getServiceNameByServiceCode(Service_code_tmp);
						alertList.setService_name(Service_name_tmp);
					}
					return alertList;
				} else if (t.equals(HotPlugLog.class)) {
					HotPlugLog hotPlugLog = new HotPlugLog();
					while (resultSet.next()) {
						hotPlugLog = DataTrans.result2HotPlugLog(resultSet);
						long Service_code_tmp = resultSet.getLong(7);
						String Service_name_tmp = this.getServiceNameByServiceCode(Service_code_tmp);
						hotPlugLog.setService_name(Service_name_tmp);
					}
					return hotPlugLog;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object;
	}

	//多条件查询上网日志表
	public List<AccessList> getAccessListQueryResult(String tableName, Params p) {
		String sqlStatement = SqlStatementBuilder.getBasicSqlStatement(tableName, p);
		logger.info(sqlStatement);
		List<AccessList> temp = new ArrayList<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						AccessList access = DataTrans.resultSet2Access(resultSet);
						long Service_code_tmp = resultSet.getLong(3);
						String Service_name_tmp = this.getServiceNameByServiceCode(Service_code_tmp);
						access.setService_name(Service_name_tmp);
						temp.add(access);
					}
				} catch (SQLException e) {
					logger.error(LogbackUtil.expection2Str(e));
				}
			}

		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		} catch (Exception e1) {
			logger.error(LogbackUtil.expection2Str(e1));
		}
		return temp;
	}

	public List<AlertList> getAlertListQueryResult(String tableName, Params p) {
		String sqlStatement = SqlStatementBuilder.getBasicSqlStatement(tableName, p);
		logger.info(sqlStatement);
		List<AlertList> temp = new ArrayList<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						long Service_code_tmp = resultSet.getLong(6);
						AlertList alertList = DataTrans.resultSet2AlertList(resultSet);
						String Service_name_tmp = this.getServiceNameByServiceCode(Service_code_tmp);
						alertList.setService_name(Service_name_tmp);
						temp.add(alertList);
					}
				} catch (SQLException e) {
					logger.error(LogbackUtil.expection2Str(e));
				}
			}

		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		} catch (Exception e1) {
			logger.error(LogbackUtil.expection2Str(e1));
		}
		return temp;
	}

	public List<HotPlugLog> getHotPlugLogQueryResult(String tableName, Params p) {
		String sqlStatement = SqlStatementBuilder.getBasicSqlStatement(tableName, p);
		logger.info(sqlStatement);
		List<HotPlugLog> temp = new ArrayList<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						HotPlugLog hotPlugLog = DataTrans.result2HotPlugLog(resultSet);
						long Service_code_tmp = resultSet.getLong(7);
						String Service_name_tmp = this.getServiceNameByServiceCode(Service_code_tmp);
						hotPlugLog.setService_name(Service_name_tmp);
						temp.add(hotPlugLog);
					}
				} catch (SQLException e) {
					logger.error(LogbackUtil.expection2Str(e));
				}
			}

		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		} catch (Exception e1) {
			logger.error(LogbackUtil.expection2Str(e1));
		}
		return temp;
	}

	//获取总数
	public int getSum(String tableName, Params p) {
		String sqlStatement = SqlStatementBuilder.getSumSqlStatement(tableName, p);
		logger.info(sqlStatement);
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
	public Map getAccessStatByServiceCode(String tableName, List<QueryParameters> queryParams, int limit) {
		String condition = SqlStatementBuilder.getPartSqlStatement(queryParams);
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT service_code,COUNT(*) AS number FROM ").append(tableName).append(condition)
		.append(" GROUP BY service_code ORDER BY number DESC");
		if (limit != 0) {
			builder.append(" LIMIT ").append(limit);
		}
		String sqlStatement = builder.toString();
		logger.info(sqlStatement);
		Map<String, Integer> map = new HashMap<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					long service_code_tmp = resultSet.getLong(1);
					String service_name = getServiceNameByServiceCode(service_code_tmp);
					map.put(service_name, resultSet.getInt(2));
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return map;
	}

	//根据国家类型group_by，返回具体的访问不同国家的数据记录数
	public List<Stat> getAlertStatByCountryName(String tableName, List<QueryParameters> queryParams) {
		String condition = SqlStatementBuilder.getPartSqlStatement(queryParams);
		String sqlStatement = "SELECT  Country_name,Jd,Wd,COUNT(*) AS number FROM " + tableName + condition
				+ " GROUP BY Country_name,Jd,Wd";
		logger.info(sqlStatement);
		List<Stat> stats = new ArrayList<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					Stat s = new Stat();
					s.setCountry_name(resultSet.getString(1));
					s.setJd(resultSet.getDouble(2));
					s.setWd(resultSet.getDouble(3));
					s.setCount(resultSet.getInt(4));
					stats.add(s);
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return stats;
	}

	//用户一段时间上网趋势统计分析
	public Map getAccessTrendency(String tableName, Params p) {
		String condition = SqlStatementBuilder.getPartSqlStatement(p.getQueryParameters());
		String sqlStatement = "SELECT unix_timestamp(to_date(from_unixtime(time+28800 )))-28800 AS DAY , COUNT(*) AS NUM FROM "
				+ tableName + condition + " GROUP BY DAY ORDER BY NUM";
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
		return map;
	}

	//过滤结果表不同规则匹配结果的趋势统计分析，groupBy为“Service_rule”,service_code和rule_id的联合字段
	public Map getAlertStatsByServiceRule(String tableName, List<QueryParameters> queryParams, int limit) {
		String condition = SqlStatementBuilder.getPartSqlStatement(queryParams);
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT rule_id,service_code, COUNT(*)  AS NUM FROM ").append(tableName).append(condition);
		builder.append(" GROUP BY rule_id,service_code ORDER BY NUM DESC ");
		if (limit != 0) {
			builder.append("LIMIT ").append(limit);
		}
		String sqlStatement = builder.toString();
		logger.info(sqlStatement);
		Map<String, Integer> map = new HashMap<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					String rule_id_tmp = resultSet.getString(1);
					long Service_code_tmp = resultSet.getLong(2);
					String rule_name_tmp = DataTrans.plcNetInfoMap.get(String.valueOf(Service_code_tmp) + rule_id_tmp);
					if (rule_name_tmp == null) {
						DataTrans.updatePlcNetInfoMap();
						rule_name_tmp = DataTrans.plcNetInfoMap.get(resultSet.getString(1));
					}
					String Service_name_tmp = this.getServiceNameByServiceCode(Service_code_tmp);
					map.put(Service_name_tmp + "-" + rule_name_tmp, resultSet.getInt(3));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(LogbackUtil.expection2Str(e));
		}
		return map;
	}

	//统计过滤结果来自哪台设备groupBy="Service_code"
	public Map getAlertStatsByServiceCode(String tableName, List<QueryParameters> queryParams, int limit) {
		String condition = SqlStatementBuilder.getPartSqlStatement(queryParams);
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT service_code,COUNT(*)  AS NUM FROM ").append(tableName).append(condition)
		.append(" GROUP BY service_code ORDER BY NUM DESC ");
		if (limit != 0) {
			builder.append("LIMIT ").append(limit);
		}
		String sqlStatement = builder.toString();
		logger.info(sqlStatement);
		Map<String, Integer> map = new HashMap<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					String Service_name_tmp = DataTrans.plcClientMAP.get(resultSet.getLong(1));
					if (Service_name_tmp == null) {
						DataTrans.updatePlcClientMap();
						Service_name_tmp = DataTrans.plcClientMAP.get(resultSet.getLong(1));
						if (Service_name_tmp == null) {
							Service_name_tmp = String.valueOf(resultSet.getLong(1));
						}
					}
					map.put(Service_name_tmp, resultSet.getInt(2));
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return map;
	}

	//流量统计表
	public List<VPNTraffic> getVPNTraffic(String tableName, Params p) {
		String sqlStatement = SqlStatementBuilder.getBasicSqlStatement(tableName, p);
		logger.info(sqlStatement);
		List<VPNTraffic> traffics = new ArrayList<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					VPNTraffic traffic_tmp = DataTrans.resultSet2VPNTraffic(resultSet);
					long Service_code_tmp = resultSet.getLong(6);
					String Service_name_tmp = this.getServiceNameByServiceCode(Service_code_tmp);
					traffic_tmp.setService_name(Service_name_tmp);
					traffics.add(traffic_tmp);
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return traffics;
	}

	//设备出口公网地址
	public List<WanIpv4> getWanIpv4(String tableName, Params p) {
		String sqlStatement = SqlStatementBuilder.getBasicSqlStatement(tableName, p);
		logger.info(sqlStatement);
		List<WanIpv4> wanIpv4s = new ArrayList<>();
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					WanIpv4 wanIpv4_tmp = DataTrans.resultSet2WanIpv4(resultSet);
					long Service_code_tmp = resultSet.getLong(5);
					String Service_name_tmp = this.getServiceNameByServiceCode(Service_code_tmp);
					wanIpv4_tmp.setService_name(Service_name_tmp);
					wanIpv4s.add(wanIpv4_tmp);
				}
			}
		} catch (SQLException e) {
			logger.error(LogbackUtil.expection2Str(e));
		}
		return wanIpv4s;
	}

	//具体设备总流量统计表
	public Map getSummaryTrafficByServiceName(String tableName, List<QueryParameters> queryParams) {
		String condition = SqlStatementBuilder.getPartSqlStatement(queryParams);
		String sqlStatement = "select unix_timestamp(trunc(from_unixtime(begin_time),\"HH24\")) as times_tmp,sum(traffic) from "
				+ tableName + condition + " group by times_tmp order by times_tmp";
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
		return map;
	}

	//新版本流量统计表
	public Map getSpecificIPTranffic(String tableName, List<QueryParameters> queryParams) {
		String condition = SqlStatementBuilder.getPartSqlStatement(queryParams);
		String sqlStatement = "select ipv4,sum(traffic) as total from " + tableName + condition
				+ " group by ipv4  order by total";
		logger.info(sqlStatement);
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
		return map;
	}

	//根据service_code匹配设备名称service_code
	private String getServiceNameByServiceCode(long service_code) {
		String Service_name_tmp = DataTrans.plcClientMAP.get(service_code);
		if (Service_name_tmp == null) {
			DataTrans.updatePlcClientMap();
			Service_name_tmp = DataTrans.plcClientMAP.get(service_code);
			if (Service_name_tmp == null) {
				Service_name_tmp = "";
			}
		}
		return Service_name_tmp;
	}
}
