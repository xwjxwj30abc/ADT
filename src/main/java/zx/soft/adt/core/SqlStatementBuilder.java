package zx.soft.adt.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.domain.AccessList;
import zx.soft.adt.domain.AlertList;
import zx.soft.adt.domain.Params;
import zx.soft.adt.domain.PlcClient;
import zx.soft.adt.domain.PlcNetInfo;
import zx.soft.adt.domain.QueryParameters;

public class SqlStatementBuilder {

	public static Logger logger = LoggerFactory.getLogger(SqlStatementBuilder.class);
	private static Map<Integer, String> operation = new HashMap<>();
	static {
		operation.put(-2, " IN ");
		operation.put(-1, "<");
		operation.put(0, "=");
		operation.put(1, ">");
		operation.put(2, " BETWEEN ");
	}

	public static String getPartSqlStatement(List<QueryParameters> queryParams) {
		StringBuilder condition = new StringBuilder();
		if (queryParams.size() > 0) {
			condition.append(" WHERE ");
			for (int j = 0; j < queryParams.size(); j++) {
				if (j > 0) {
					condition.append(" AND ");
				}
				if (queryParams.get(j).getOpera() == 2) {
					condition.append(queryParams.get(j).getField())
					.append(operation.get(queryParams.get(j).getOpera()))
					.append(queryParams.get(j).getValue().split(",")[0]).append(" AND ")
					.append(queryParams.get(j).getValue().split(",")[1]);
				} else {
					String fieldName = queryParams.get(j).getField();
					if (isStringType(fieldName)) {
						condition.append(queryParams.get(j).getField()).append(" LIKE ");
						condition.append("\'%").append(URLDecoder.decode(queryParams.get(j).getValue())).append("%\'");
					} else {
						condition.append(queryParams.get(j).getField()).append(
								operation.get(queryParams.get(j).getOpera()));
						condition.append(String.valueOf(queryParams.get(j).getValue()));
					}
				}
			}

		}
		return condition.toString();
	}

	//根据参数构造基本查询语句
	public static String getBasicSqlStatement(String tableName, List<QueryParameters> queryParams, String orderBy,
			String order, int pageSize, int page) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM ").append(tableName);
		if (queryParams.size() > 0) {
			String condition = SqlStatementBuilder.getPartSqlStatement(queryParams);
			builder.append(condition);
		}
		if (orderBy != null && orderBy != "") {
			builder.append(" ORDER BY ").append(orderBy);
		}
		if (order != null && order != "") {
			builder.append(" ").append(order);
		}
		if (pageSize != 0) {
			builder.append(" LIMIT ").append(pageSize);
		}
		if (page != 0) {
			builder.append(" OFFSET ").append(page);
		}
		logger.info(builder.toString());
		return builder.toString();
	}

	//根据Params参数构造基本查询语句
	public static String getBasicSqlStatement(String tableName, Params p) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM ").append(tableName);
		if (p.getQueryParameters().size() > 0) {
			String condition = SqlStatementBuilder.getPartSqlStatement(p.getQueryParameters());
			builder.append(condition);
		}
		if (p.getOrder_by() != null && p.getOrder_by() != "") {
			builder.append(" ORDER BY ").append(p.getOrder_by());
		}
		if (p.getOrder() != null && p.getOrder() != "") {
			builder.append(" ").append(p.getOrder());
		}
		if (p.getPage_size() != 0) {
			builder.append(" LIMIT ").append(p.getPage_size());
		}
		if (p.getPage() != 0) {
			builder.append(" OFFSET ").append(p.getPage() * p.getPage_size());
		}
		logger.info(builder.toString());
		return builder.toString();
	}

	//查询满足该条件的数据总数
	public static String getSumSqlStatement(String tableName, Params p) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(*) FROM ").append(tableName);
		if (p.getQueryParameters().size() > 0) {
			String condition = SqlStatementBuilder.getPartSqlStatement(p.getQueryParameters());
			builder.append(condition);
		}
		return builder.toString();
	}

	//判断该查询字段是否为String类型
	private static boolean isStringType(String fieldName) {
		Field field = null;
		try {
			field = AlertList.class.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			logger.info("非AlertList里面字段");
			try {
				field = AccessList.class.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e1) {
				logger.info("非AccessList里面字段");
				try {
					field = PlcClient.class.getDeclaredField(fieldName);
				} catch (NoSuchFieldException e2) {
					logger.info("非PlcClient里面字段");
					try {
						field = PlcNetInfo.class.getDeclaredField(fieldName);
					} catch (NoSuchFieldException e3) {
						logger.info("非PlcNetInfo里面字段");
						return false;
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (field.getType() != null && field.getType() == String.class) {
			return true;
		}
		return false;
	}
}
