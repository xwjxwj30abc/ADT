package zx.soft.sina.IO.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpalaQuery {

	private static ClouderaImpalaJdbc impala = new ClouderaImpalaJdbc();

	//获取活跃用户
	public static List<String> getTopNActiveUser(int from) throws SQLException {
		String query = "SELECT key FROM sina_user_score ORDER BY score DESC LIMIT 1000 OFFSET " + from;
		ResultSet result = impala.Query(query);
		List<String> topN = new ArrayList<>();
		while (result.next()) {
			topN.add(result.getString(1));
		}
		return topN;
	}

	//从最近爬取的微博表active_user_lastest_weibos中获取最大的微博id,以便在循环爬取活跃用户最新微博时进行since_id更新
	public static String getMaxId() throws SQLException {
		String query = " select max(id) from active_user_lastest_weibos";
		ResultSet result = impala.Query(query);
		while (result.next()) {
			String max = result.getString(1);
			return max;
		}
		return null;
	}

	public static void main(String[] args) throws SQLException {
		List<String> topN = ImpalaQuery.getTopNActiveUser(0);
		System.out.println(topN.toString());
		System.out.println(ImpalaQuery.getMaxId());
	}
}
