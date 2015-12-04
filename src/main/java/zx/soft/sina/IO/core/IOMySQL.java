package zx.soft.sina.IO.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import zx.soft.impala.adt.core.DataTrans;
import zx.soft.impala.adt.core.Tools;
import zx.soft.sina.IO.dao.UserMapper;
import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.domain.PlcNetInfo;
import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;
import zx.soft.sina.IO.util.MySQLConnection;

public class IOMySQL implements SinaIO {

	@Inject
	private UserMapper userMapper;

	public void insertPlcNetInfo(PlcNetInfo plcNetInfo) {
		userMapper.insertPlcNetInfo(plcNetInfo);
	}

	public void insertPlcClient(PlcClient plcClient) {
		userMapper.insertPlcClient(plcClient);

	}

	public void deletePlcClient(long Service_code) {
		userMapper.deletePlcClient(Service_code);
	}

	public void updatePlcClient(PlcClient plcClient) {
		userMapper.updatePlcClient(plcClient);
	}

	@Override
	public <T> void write(String key, T value) {
		if (value instanceof User) {
			User user = (User) value;
			userMapper.insertUser(user);
		}
		if (value instanceof Weibo) {
			Weibo weibo = (Weibo) value;
			userMapper.insertWeibo(weibo);
		}
	}

	@Override
	public void close() throws IOException {
		//
	}

	public List<PlcClient> getPlcClientQueryResult(String tableName, List<QueryParameters> queryParams, String orderBy,
			String order, int pageSize, int page) {
		String sqlStatement = Tools.getBasicSqlStatement(tableName, queryParams, orderBy, order, pageSize, page);
		try (Connection conn = MySQLConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			List<PlcClient> temp = new ArrayList<>();
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						temp.add(DataTrans.resultSet2PlcClient(resultSet));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return temp;
		} catch (SQLException e) {
			throw new RuntimeException();
		} catch (Exception e1) {
			throw new RuntimeException();
		}
	}

	public int getSum(String tableName, List<QueryParameters> queryParams) {
		String condition = Tools.getPartSqlStatement(queryParams);
		String sqlStatement = "SELECT COUNT(*) FROM " + tableName + " WHERE " + condition;
		int s = 0;
		try (Connection conn = MySQLConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					s = resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	public PlcClient getSpecPlcClient(String tableName, long Service_code) {
		String sqlStatement = "SELECT * FROM " + tableName + " WHERE Service_code = " + Service_code;
		PlcClient plcClient = null;
		try (Connection conn = MySQLConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				try {
					while (resultSet.next()) {
						plcClient = DataTrans.resultSet2PlcClient(resultSet);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		} catch (Exception e1) {
			throw new RuntimeException();
		}

		return plcClient;
	}
}
