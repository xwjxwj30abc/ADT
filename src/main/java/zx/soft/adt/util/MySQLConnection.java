package zx.soft.adt.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import zx.soft.utils.config.ConfigUtil;

public class MySQLConnection {

	private static DataSource dataSource;

	public static Connection getConnection() {
		Connection con = null;
		if (dataSource == null) {
			initDataSource();
		}
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void initDataSource() {

		Properties props = ConfigUtil.getProps("db.properties");
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(props.getProperty("mysql.connection.url"));
		ds.setDriverClassName(props.getProperty("jdbc.driver.class.name"));
		ds.setUsername(props.getProperty("user.db.username"));
		ds.setPassword(props.getProperty("user.db.password"));
		ds.setMaxActive(Integer.parseInt(props.getProperty("mysql.dbcp.maxActive")));
		ds.setMinIdle(Integer.parseInt(props.getProperty("mysql.dbcp.minIdle")));
		ds.setMaxIdle(Integer.parseInt(props.getProperty("mysql.dbcp.maxIdle")));
		ds.setMaxWait(Integer.parseInt(props.getProperty("mysql.dbcp.maxWait")));
		ds.setMinEvictableIdleTimeMillis(Long.parseLong(props.getProperty("mysql.dbcp.maxActive.time")));
		ds.setRemoveAbandoned(true);
		ds.setRemoveAbandonedTimeout(2000);
		dataSource = ds;
	}

}
