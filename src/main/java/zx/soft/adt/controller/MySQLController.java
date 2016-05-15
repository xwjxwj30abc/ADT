package zx.soft.adt.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.adt.core.ConstADT;
import zx.soft.adt.core.DataTrans;
import zx.soft.adt.domain.Params;
import zx.soft.adt.domain.PlcClient;
import zx.soft.adt.domain.QueryParameters;
import zx.soft.adt.domain.QueryResult;
import zx.soft.adt.domain.Status;
import zx.soft.adt.service.MySQLService;

@Controller
@RequestMapping("/mysql")
public class MySQLController {
	@Inject
	private MySQLService mySQLService;

	@RequestMapping(value = "/plcclient/insert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Status insertPlcClientToMySQL(@RequestBody PlcClient plcClient) {
		Status st = new Status();
		if (plcClient != null) {
			st = mySQLService.insertPlcClient(plcClient);
			DataTrans.plcClientMAP.put(plcClient.getService_code(), plcClient.getService_name());
		} else {
			st.setErrorCode("2");
			st.setErrorMessage("plcClient为空");
		}
		return st;
	}

	@RequestMapping(value = "/plcclient/delete/{service_code}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Status deletePlcClientToMySQL(@PathVariable("service_code") long Service_code) {
		DataTrans.plcClientMAP.remove(Service_code);
		return mySQLService.deletePlcClient(Service_code);
	}

	@RequestMapping(value = "/plcclient/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Status updatePlcClientToMySQL(@RequestBody PlcClient plcClient) {
		DataTrans.plcClientMAP.put(plcClient.getService_code(), plcClient.getService_name());
		return mySQLService.updatePlcClient(plcClient);
	}

	//多条件查询parquet_compression.plcclient表
	@RequestMapping(value = "/plcclient", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody QueryResult getPlcClientQueryResult(@RequestBody Params p) {
		if (p.getOrder() == "") {
			p.setOrder("ASC");
		}
		if (p.getOrder_by() == "") {
			p.setOrder_by("Service_code");
		}
		if (p.getPage_size() == 0) {
			p.setPage_size(20);
		}
		List<QueryParameters> queryParameters = this.addServiceCodeLimits(p.getQueryParameters());
		p.setQueryParameters(queryParameters);
		List<PlcClient> lists = mySQLService.getPlcClientQueryResult(ConstADT.TABLE_PLCCLIENT, p);
		int number = mySQLService.getSum(ConstADT.TABLE_PLCCLIENT, p);

		return new QueryResult(number, lists);
	}

	//查看具体一条plcclient记录
	@RequestMapping(value = "/plcclient/info", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PlcClient getSpecPlcClient(@RequestParam("service_code") long service_code) {
		return mySQLService.getSpecPlcClient(ConstADT.TABLE_PLCCLIENT, service_code);
	}

	//限制service_code取值范围
	private List<QueryParameters> addServiceCodeLimits(List<QueryParameters> queryParameters) {
		for (int i = 0; i < queryParameters.size(); i++) {
			QueryParameters queryParameter = queryParameters.get(i);
			if (queryParameter.getField().equalsIgnoreCase("service_code")) {
				QueryParameters query_tmp = new QueryParameters();
				String service_codes = queryParameter.getValue();
				query_tmp.setField("Service_code");
				query_tmp.setOpera(-2);
				query_tmp.setValue("(" + service_codes + ")");
				queryParameters.set(i, query_tmp);
				break;
			}
		}
		return queryParameters;
	}
}
