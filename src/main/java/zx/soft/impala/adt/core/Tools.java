package zx.soft.impala.adt.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sina.IO.domain.QueryParameters;

public class Tools {

	public static Logger logger = LoggerFactory.getLogger(Tools.class);
	private static Map<Integer, String> operation = new HashMap<>();
	static {
		operation.put(-1, "<");
		operation.put(0, "=");
		operation.put(1, ">");
		operation.put(2, " BETWEEN ");
	}

	public static String getPartSqlStatement(List<QueryParameters> queryParams) {
		StringBuilder condition = new StringBuilder();
		if (queryParams.size() > 0) {

			if (queryParams.get(0).getOpera() == 2) {
				condition.append(queryParams.get(0).getField()).append(operation.get(queryParams.get(0).getOpera()))
						.append(queryParams.get(0).getValue().split(",")[0]).append(" AND ")
						.append(queryParams.get(0).getValue().split(",")[1]);
			} else {
				if (ConstADT.StringFields.contains(queryParams.get(0).getField())
						&& !queryParams.get(0).getField().equals("id")) {
					condition.append(queryParams.get(0).getField()).append(" LIKE ");
					condition.append("\'%").append(URLDecoder.decode(queryParams.get(0).getValue())).append("%\'");
				} else {
					condition.append(queryParams.get(0).getField())
							.append(operation.get(queryParams.get(0).getOpera()));
					condition.append(String.valueOf(queryParams.get(0).getValue()));
				}
			}

			for (int j = 1; j < queryParams.size(); j++) {
				condition.append(" AND ");
				if (queryParams.get(j).getOpera() == 2) {
					condition.append(queryParams.get(j).getField())
							.append(operation.get(queryParams.get(j).getOpera()))
							.append(queryParams.get(j).getValue().split(",")[0]).append(" AND ")
							.append(queryParams.get(j).getValue().split(",")[1]);
				} else {
					if (ConstADT.StringFields.contains(queryParams.get(j).getField())
							&& !queryParams.get(j).getField().equals("id")) {
						//对于字符串类型字段构造模糊查询语句查询
						condition.append(queryParams.get(j).getField()).append(" LIKE ");
						condition.append("\'%").append(URLDecoder.decode(queryParams.get(j).getValue())).append("%\'");
					} else {
						//对于数值类型字段，获取String包装的数值，并构造查询语句
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
		String condition = Tools.getPartSqlStatement(queryParams);
		String sqlStatement = "SELECT * FROM " + tableName + " WHERE " + condition + " ORDER BY " + orderBy + " "
				+ order + " LIMIT " + pageSize + " OFFSET " + page * pageSize;
		logger.info(sqlStatement);
		return sqlStatement;
	}
}
