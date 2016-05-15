package zx.soft.impala.adt.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.impala.adt.domain.AccessList;
import zx.soft.impala.adt.domain.AlertList;
import zx.soft.impala.adt.domain.Params;
import zx.soft.impala.adt.domain.PlcClient;
import zx.soft.impala.adt.domain.PlcNetInfo;
import zx.soft.impala.adt.domain.QueryParameters;
import zx.soft.utils.json.JsonUtils;

public class Tools_TMP {

	public static Logger logger = LoggerFactory.getLogger(Tools_TMP.class);
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

			//			if (queryParams.get(0).getOpera() == 2) {
			//				condition.append(queryParams.get(0).getField()).append(operation.get(queryParams.get(0).getOpera()))
			//				.append(queryParams.get(0).getValue().split(",")[0]).append(" AND ")
			//				.append(queryParams.get(0).getValue().split(",")[1]);
			//			} else {
			//				if (ConstADT.StringFields.contains(queryParams.get(0).getField())
			//						&& !queryParams.get(0).getField().equals("id")) {
			//					condition.append(queryParams.get(0).getField()).append(" LIKE ");
			//					condition.append("\'%").append(URLDecoder.decode(queryParams.get(0).getValue())).append("%\'");
			//				} else {
			//					condition.append(queryParams.get(0).getField())
			//					.append(operation.get(queryParams.get(0).getOpera()));
			//					condition.append(String.valueOf(queryParams.get(0).getValue()));
			//				}
			//			}

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
					//					if (ConstADT.StringFields.contains(queryParams.get(j).getField())
					//							&& !queryParams.get(j).getField().equals("id")) {
					//						//对于字符串类型字段构造模糊查询语句查询
					//						condition.append(queryParams.get(j).getField()).append(" LIKE ");
					//						condition.append("\'%").append(URLDecoder.decode(queryParams.get(j).getValue())).append("%\'");
					//					} else {
					//						//对于数值类型字段，获取String包装的数值，并构造查询语句
					//						condition.append(queryParams.get(j).getField()).append(
					//								operation.get(queryParams.get(j).getOpera()));
					//						condition.append(String.valueOf(queryParams.get(j).getValue()));
					//					}

				}
			}

		}
		return condition.toString();
	}

	//根据参数构造基本查询语句
	public static String getBasicSqlStatement(String tableName, List<QueryParameters> queryParams, String orderBy,
			String order, int pageSize, int page) {
		String condition = Tools_TMP.getPartSqlStatement(queryParams);
		String sqlStatement = "SELECT * FROM " + tableName + " WHERE " + condition + " ORDER BY " + orderBy + " "
				+ order + " LIMIT " + pageSize + " OFFSET " + page * pageSize;
		logger.info(sqlStatement);
		return sqlStatement;
	}

	//根据Params参数构造基本查询语句
	public static String getBasicSqlStatement(String tableName, Params p) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM ").append(tableName);
		if (p.getQueryParameters().size() > 0) {
			String condition = Tools_TMP.getPartSqlStatement(p.getQueryParameters());
			builder.append(" WHERE ").append(condition);
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
			String condition = Tools_TMP.getPartSqlStatement(p.getQueryParameters());
			builder.append(" WHERE ").append(condition);
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

	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		Params p = new Params();
		List<QueryParameters> lists = new ArrayList<>();
		QueryParameters w = new QueryParameters();
		w.setField("Service_name");
		w.setOpera(0);
		w.setValue("存储");
		lists.add(w);
		QueryParameters ww = new QueryParameters();
		ww.setField("Time");
		ww.setOpera(2);
		ww.setValue("1461001046,1461051046");
		lists.add(ww);
		QueryParameters www = new QueryParameters();
		www.setField("Service_code");
		www.setOpera(-2);
		www.setValue("1000965093962574,0");
		lists.add(www);
		p.setQueryParameters(lists);
		p.setOrder("");
		p.setOrder_by("");
		p.setPage(0);
		p.setPage_size(0);
		System.out.println(JsonUtils.toJsonWithoutPretty(p));
		String sqlStatement = Tools_TMP.getBasicSqlStatement("alert", p);
		System.out.println(sqlStatement);
	}
}
