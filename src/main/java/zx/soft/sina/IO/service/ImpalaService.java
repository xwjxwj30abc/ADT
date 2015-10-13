package zx.soft.sina.IO.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.domain.Result;
import zx.soft.sina.IO.query.ClouderaImpalaJdbc;
import zx.soft.sina.IO.util.JsonUtils;

@Service
public class ImpalaService {

	private static Logger logger = LoggerFactory.getLogger(ImpalaService.class);
	private static ClouderaImpalaJdbc impala = new ClouderaImpalaJdbc();
	private static String sqlStatement;
	private static Map<Integer, String> operation = new HashMap<>();
	static {
		operation.put(-1, "<");
		operation.put(0, "=");
		operation.put(1, ">");
		operation.put(2, " BETWEEN ");
	}

	public List<Result> query(String tableName, List<QueryParameters> queryParams) {
		StringBuilder condition = new StringBuilder();
		if (queryParams.size() > 0) {
			if (queryParams.get(0).getOperator() == 2) {
				condition.append(queryParams.get(0).getField()).append(operation.get(queryParams.get(0).getOperator()))
				.append(queryParams.get(0).getValue().split(",")[0]).append(" AND ")
				.append(queryParams.get(0).getValue().split(",")[1]);
			} else {
				condition.append(queryParams.get(0).getField()).append(operation.get(queryParams.get(0).getOperator()))
				.append(queryParams.get(0).getValue());
			}

			for (int j = 1; j < queryParams.size(); j++) {
				if (queryParams.get(j).getOperator() == 2) {
					condition.append(queryParams.get(j).getField()).append(queryParams.get(j).getOperator())
					.append(queryParams.get(j).getValue().split(",")[0]).append(" AND ")
					.append(queryParams.get(j).getValue().split(",")[1]);
				} else {
					condition.append(" AND ").append(queryParams.get(j).getField())
							.append(operation.get(queryParams.get(j).getOperator()))
							.append(queryParams.get(j).getValue());
				}
			}
		}
		sqlStatement = "SELECT * FROM " + tableName + " WHERE " + condition + " LIMIT 100";
		logger.info(sqlStatement);
		return impala.Query(sqlStatement);
	}

	public static void main(String[] args) throws SQLException {
		ImpalaService service = new ImpalaService();
		List<QueryParameters> queryParams = new ArrayList<>();
		QueryParameters param = new QueryParameters();
		param.setField("commentscount");
		param.setOperator(1);
		param.setValue("200");
		queryParams.add(param);
		QueryParameters param2 = new QueryParameters();
		param2.setField("repostscount");
		param2.setOperator(1);
		param2.setValue("0");
		queryParams.add(param2);
		System.out.println(JsonUtils.toJsonWithoutPretty(queryParams));
	}

}
