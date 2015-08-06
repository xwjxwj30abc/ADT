package zx.soft.sina.IO.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class App {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		ClouderaImpalaJdbc impala = new ClouderaImpalaJdbc();
		//"select count(*) from sina_user_baseinfo"
		String query1 = "select * from sina_user_baseinfo where key=\"1000148680\"";
		String query = "SELECT idstr,friends_count,statuses_count,created_at FROM sina_user_baseinfo ORDER BY statuses_count DESC LIMIT 15";

		//SELECT user_type, COUNT(user_id) AS cnt FROM test_info WHERE gender='M' GROUP BY user_type ORDER BY cnt DESC LIMIT 10;
		ResultSet result = impala.Query(query);
		int i = 0;
		while (result.next()) {
			System.out.println(result.getString(1) + ":" + result.getString(2) + "," + result.getString(3));
			long time = Date.parse(result.getString(4));
			System.out.println(time);
		}
		impala.closeConnection();
	}

}
