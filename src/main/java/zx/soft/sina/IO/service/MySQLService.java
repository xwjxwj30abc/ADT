package zx.soft.sina.IO.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.core.IOMySQL;
import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.domain.PlcNetInfo;
import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.domain.Status;

@Service
public class MySQLService {

	@Inject
	private IOMySQL iOMySQL;

	public Status insertPlcNetInfo(PlcNetInfo info) {
		return iOMySQL.insertPlcNetInfo(info);
	}

	public Status insertPlcClient(PlcClient plcClient) {
		return iOMySQL.insertPlcClient(plcClient);
	}

	public Status deletePlcClient(long Service_code) {
		return iOMySQL.deletePlcClient(Service_code);
	}

	public Status updatePlcClient(PlcClient plcClient) {
		return iOMySQL.updatePlcClient(plcClient);
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

	public List<Long> getMappingServiceCodeByServiceName(String tableName, String Service_name) {
		return iOMySQL.getMappingServiceCodeByServiceName(tableName, Service_name);
	}

	public Map initGEO(String tablename) {
		return iOMySQL.initGEO(tablename);
	}
}
