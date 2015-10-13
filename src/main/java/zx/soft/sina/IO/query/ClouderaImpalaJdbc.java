package zx.soft.sina.IO.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import zx.soft.sina.IO.domain.Result;
import zx.soft.sina.IO.util.ConfigUtil;

public class ClouderaImpalaJdbc {

	Properties props;

	public ClouderaImpalaJdbc() {

		props = ConfigUtil.getProps("hive-connection.properties");
		try {
			Class.forName(props.getProperty("jdbc.driver.class.name"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<Result> Query(String sqlStatement) {

		try (Connection conn = getConnection();
				Statement statement = conn.createStatement();
				ResultSet result = statement.executeQuery(sqlStatement);) {
			return parseResult(result);
		} catch (SQLException e) {
			throw new RuntimeException();
		} catch (Exception e1) {
			throw new RuntimeException();
		}
	}

	private List<Result> parseResult(ResultSet resultSet) {
		List<Result> temp = new ArrayList<>();
		if (resultSet != null) {
			try {
				while (resultSet.next()) {
					Result result = new Result();
					result.setId(resultSet.getInt(1));
					result.setWid(resultSet.getLong(2));
					result.setUsername(resultSet.getLong(3));
					result.setRepostscount(resultSet.getLong(4));
					result.setCommentscount(resultSet.getLong(5));
					result.setText(resultSet.getString(6));
					result.setCreateat(resultSet.getInt(7));
					result.setOwid(resultSet.getLong(8));
					result.setOusername(resultSet.getInt(9));
					result.setFavorited(resultSet.getBoolean(10));
					result.setGeo(resultSet.getString(11));
					result.setLatitude(resultSet.getFloat(12));
					result.setLongitude(resultSet.getFloat(13));
					result.setOriginalpic(resultSet.getString(14));
					result.setSource(resultSet.getString(15));
					result.setVisible(resultSet.getString(16));
					result.setLasttime(resultSet.getInt(17));
					temp.add(result);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(props.getProperty("connection.url"));
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
