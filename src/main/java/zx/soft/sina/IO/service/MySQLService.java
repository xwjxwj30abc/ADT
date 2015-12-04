package zx.soft.sina.IO.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.core.IOMySQL;
import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.domain.PlcNetInfo;
import zx.soft.sina.IO.domain.QueryParameters;

@Service
public class MySQLService {

	@Inject
	private IOMySQL iOMySQL;

	public <T> void insert(List<T> values) {
		for (T value : values) {
			iOMySQL.write("", value);
		}

	}

	public void insertPlcNetInfo(PlcNetInfo info) {
		iOMySQL.insertPlcNetInfo(info);
	}

	public void insertPlcClient(PlcClient plcClient) {
		iOMySQL.insertPlcClient(plcClient);
	}

	public void deletePlcClient(long Service_code) {
		iOMySQL.deletePlcClient(Service_code);
	}

	public void updatePlcClient(PlcClient plcClient) {
		iOMySQL.updatePlcClient(plcClient);
	}

	public List<PlcClient> getPlcClientQueryResult(String tableName, List<QueryParameters> queryParams, String orderBy,
			String order, int pageSize, int page) {
		return iOMySQL.getPlcClientQueryResult(tableName, queryParams, orderBy, order, pageSize, page);
	}

	public int getSum(String tableName, List<QueryParameters> queryParams) {
		return iOMySQL.getSum(tableName, queryParams);
	}

	public PlcClient getSpecPlcClient(String tableName, long Service_code) {
		return iOMySQL.getSpecPlcClient(tableName, Service_code);
	}

}
