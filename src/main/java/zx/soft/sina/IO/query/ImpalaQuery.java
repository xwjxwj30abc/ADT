package zx.soft.sina.IO.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpalaQuery {

	private static ClouderaImpalaJdbc impala = new ClouderaImpalaJdbc();

	public static List<String> getTopNActiveUser(int from) throws SQLException {
		String query = "SELECT key FROM sina_user_score ORDER BY score DESC LIMIT 1000 OFFSET " + from;
		ResultSet result = impala.Query(query);
		List<String> topN = new ArrayList<>();
		while (result.next()) {
			topN.add(result.getString(1));
		}
		return topN;
	}

	public static void main(String[] args) throws SQLException {
		List<String> topN = ImpalaQuery.getTopNActiveUser(0);
		System.out.println(topN.toString());
	}
}
