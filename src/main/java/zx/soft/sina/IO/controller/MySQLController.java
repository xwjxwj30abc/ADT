package zx.soft.sina.IO.controller;

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

import zx.soft.impala.adt.core.ConstADT;
import zx.soft.sina.IO.domain.Params;
import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.domain.QueryResult;
import zx.soft.sina.IO.domain.Status;
import zx.soft.sina.IO.service.MySQLService;

@Controller
@RequestMapping("/mysql")
public class MySQLController {
	@Inject
	private MySQLService mySQLService;

	@RequestMapping(value = "/plcclient/insert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Status insertPlcClientToMySQL(@RequestBody PlcClient plcClient) {
		Status st = null;
		if (plcClient != null) {
			st = mySQLService.insertPlcClient(plcClient);
		} else {
			st.setErrorCode("2");
			st.setErrorMessage("plcClient为空");
		}
		return st;
	}

	@RequestMapping(value = "/plcclient/delete/{service_code}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Status deletePlcClientToMySQL(@PathVariable("service_code") long Service_code) {
		return mySQLService.deletePlcClient(Service_code);
	}

	@RequestMapping(value = "/plcclient/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Status updatePlcClientToMySQL(@RequestBody PlcClient plcClient) {
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
		if (p.getQueryParameters().size() == 0) {
			p.getQueryParameters().add(new QueryParameters(1, "id", "0"));
		}
		List<PlcClient> lists = mySQLService.getPlcClientQueryResult(ConstADT.TABLE_PLCCLIENT, p.getQueryParameters(),
				p.getOrder_by(), p.getOrder(), p.getPage_size(), p.getPage());
		int number = mySQLService.getSum(ConstADT.TABLE_PLCCLIENT, p.getQueryParameters());

		return new QueryResult(number, lists);
	}

	//查看具体一条plcclient记录
	@RequestMapping(value = "/plcclient/info", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PlcClient getSpecPlcClient(@RequestParam("service_code") long service_code) {
		return mySQLService.getSpecPlcClient(ConstADT.TABLE_PLCCLIENT, service_code);
	}

}
