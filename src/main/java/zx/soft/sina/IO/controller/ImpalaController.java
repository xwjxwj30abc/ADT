package zx.soft.sina.IO.controller;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.sina.IO.domain.AccessList;
import zx.soft.sina.IO.domain.AlertList;
import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.service.ImpalaService;

@Controller
@RequestMapping("/impala")
public class ImpalaController {

	@Inject
	private ImpalaService impalaService;

	//多条件查询parquet_compression.accesslist表
	@RequestMapping(value = "/access", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<AccessList> getAccessResult(@RequestBody List<QueryParameters> queryParams,
			@RequestParam("page") int page) throws SQLException {
		return impalaService.queryAccessList("parquet_compression.accesslist", queryParams, page);
	}

	//多条件查询parquet_compression.alertlist表
	@RequestMapping(value = "/alert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<AlertList> getAlertResult(@RequestBody List<QueryParameters> queryParams,
			@RequestParam("page") int page) throws SQLException {
		return impalaService.queryAlertList("parquet_compression.alertlist", queryParams, page);
	}

	//多条件查询parquet_compression.plcclient表
	@RequestMapping(value = "/plcclient", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<PlcClient> getPlcClientResult(@RequestBody List<QueryParameters> queryParams,
			@RequestParam("page") int page) throws SQLException {
		return impalaService.queryPlcClient("parquet_compression.plcclient", queryParams, page);
	}

	//单条件查询parquet_compression.accesslist表
	@RequestMapping(value = "/access", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<AccessList> getAccess(@RequestParam("field") String field,
			@RequestParam("value") Object value, @RequestParam("page") int page) {

		String sqlStatement = "SELECT * FROM parquet_compression.accesslist" + " WHERE " + field + " = " + value
				+ " ORDER BY id LIMIT 20 OFFSET " + page;
		System.out.println(sqlStatement);
		return impalaService.getAccessListQueryResult(sqlStatement);
	}

	//单条件查询parquet_compression.alertlist表
	@RequestMapping(value = "/alert", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<AlertList> getAlert(@RequestParam("field") String field,
			@RequestParam("value") Object value, @RequestParam("page") int page) {

		String sqlStatement = "SELECT * FROM parquet_compression.alertlist" + " WHERE " + field + " = " + value
				+ " ORDER BY id LIMIT 100 OFFSET " + page;
		return impalaService.getAlertListQueryResult(sqlStatement);
	}

	//单条件查询parquet_compression.plcclient表
	@RequestMapping(value = "/plcclient", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<PlcClient> getPlcClient(@RequestParam("field") String field,
			@RequestParam("value") Object value, @RequestParam("page") int page) {

		String sqlStatement = "SELECT * FROM parquet_compression.plcclient" + " WHERE " + field + " = " + value
				+ " ORDER BY id LIMIT 100 OFFSET " + page;
		return impalaService.getPlcClientQueryResult(sqlStatement);
	}

}
