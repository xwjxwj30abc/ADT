package zx.soft.sina.IO.controller;

import java.sql.SQLException;
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
import zx.soft.sina.IO.domain.AccessList;
import zx.soft.sina.IO.domain.AlertList;
import zx.soft.sina.IO.domain.Params;
import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.domain.QueryResult;
import zx.soft.sina.IO.service.ImpalaService;
import zx.soft.sina.IO.util.JsonUtils;

@Controller
@RequestMapping("/impala")
public class ImpalaController {

	@Inject
	private ImpalaService impalaService;

	@RequestMapping(value = "/access", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody QueryResult getAccessResult(@RequestBody String query) throws SQLException {
		Params p = JsonUtils.getObject(query, Params.class);
		if (p.getOrder() == "") {
			p.setOrder("DESC");
		}
		if (p.getOrder_by() == "") {
			p.setOrder_by("Time");
		}
		if (p.getPage_size() == 0) {
			p.setPage_size(20);
		}
		if (p.getQueryParameters().size() == 0) {
			p.getQueryParameters().add(new QueryParameters(1, "id", "0"));
		}
		List<AccessList> lists = impalaService.getAccessListQueryResult(ConstADT.TABLE_ACCESS, p.getQueryParameters(),
				p.getOrder_by(), p.getOrder(), p.getPage_size(), p.getPage());
		int number = impalaService.getSum(ConstADT.TABLE_ACCESS, p.getQueryParameters());
		return new QueryResult(number, lists);
	}

	//多条件查询parquet_compression.alertlist表
	@RequestMapping(value = "/alert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody QueryResult getAlertResult(@RequestBody String query) throws SQLException {
		Params p = JsonUtils.getObject(query, Params.class);
		if (p.getOrder() == "") {
			p.setOrder("DESC");
		}
		if (p.getOrder_by() == "") {
			p.setOrder_by("Matching_time");
		}
		if (p.getPage_size() == 0) {
			p.setPage_size(20);
		}
		if (p.getQueryParameters().size() == 0) {
			p.getQueryParameters().add(new QueryParameters(1, "id", "0"));
		}
		List<AlertList> lists = impalaService.getAlertListQueryResult(ConstADT.TABLE_ALERT, p.getQueryParameters(),
				p.getOrder_by(), p.getOrder(), p.getPage_size(), p.getPage());
		int number = impalaService.getSum(ConstADT.TABLE_ALERT, p.getQueryParameters());
		return new QueryResult(number, lists);
	}

	//多条件查询parquet_compression.plcclient表
	@RequestMapping(value = "/plcclient", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody QueryResult getPlcClientQueryResult(@RequestBody String query) throws SQLException {
		Params p = JsonUtils.getObject(query, Params.class);
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
		List<PlcClient> lists = impalaService.getPlcClientQueryResult(ConstADT.TABLE_PLCCLIENT, p.getQueryParameters(),
				p.getOrder_by(), p.getOrder(), p.getPage_size(), p.getPage());
		int number = impalaService.getSum(ConstADT.TABLE_PLCCLIENT, p.getQueryParameters());

		return new QueryResult(number, lists);
	}

	//查看具体一条accesslist记录
	@RequestMapping(value = "/access/info", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody AccessList getSpecAccess(@RequestParam("rowkey") String rowkey) {
		return impalaService.getSpecAccess(ConstADT.TABLE_ACCESS, rowkey);
	}

	//查看具体一条alertlist记录
	@RequestMapping(value = "/alert/info", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody AlertList getSpecAlert(@RequestParam("rowkey") String rowkey) {
		return impalaService.getSpecAlert(ConstADT.TABLE_ALERT, rowkey);
	}

	//查看具体一条plcclient记录
	@RequestMapping(value = "/plcclient/info", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PlcClient getSpecPlcClient(@RequestParam("rowkey") String rowkey) {
		return impalaService.getSpecPlcClient(ConstADT.TABLE_PLCCLIENT, rowkey);
	}

	//上网结果统计accesslist
	@RequestMapping(value = "/access/stats/{group_by}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getStatistics(@RequestBody String query, @PathVariable("group_by") String group_by) {
		Params p = JsonUtils.getObject(query, Params.class);
		if (p.getOrder() == "") {
			p.setOrder("DESC");
		}
		if (p.getOrder_by() == "") {
			p.setOrder_by("Time");
		}
		if (p.getPage_size() == 0) {
			p.setPage_size(20);
		}
		if (p.getQueryParameters().size() == 0) {
			p.getQueryParameters().add(new QueryParameters(1, "id", "0"));
		}
		return impalaService.getStat(ConstADT.TABLE_ACCESS, p.getQueryParameters(), group_by, 10);
	}

	//上网趋势统计
	@RequestMapping(value = "/access/trend", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getTrendency(@RequestParam("start") long start, @RequestParam("end") long end) {
		return impalaService.getTrendency(ConstADT.TABLE_ACCESS, start, end);
	}

	//过滤结果终端来源统计接口
	@RequestMapping(value = "/alert/stats/{group_by}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getAlert(@RequestBody String query, @PathVariable("group_by") String group_by) {
		Params p = JsonUtils.getObject(query, Params.class);
		if (p.getOrder() == "") {
			p.setOrder("DESC");
		}
		if (p.getOrder_by() == "") {
			p.setOrder_by("Matching_time");
		}
		if (p.getPage_size() == 0) {
			p.setPage_size(10);
		}
		if (p.getQueryParameters().size() == 0) {
			p.getQueryParameters().add(new QueryParameters(1, "id", "0"));
		}
		return impalaService.getAlertStats(ConstADT.TABLE_ALERT, p.getQueryParameters(), group_by, p.getPage_size());
	}

}
