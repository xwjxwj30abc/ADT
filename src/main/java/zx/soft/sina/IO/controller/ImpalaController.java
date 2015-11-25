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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.sina.IO.domain.AccessList;
import zx.soft.sina.IO.domain.AlertList;
import zx.soft.sina.IO.domain.Params;
import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.service.ImpalaService;
import zx.soft.sina.IO.util.JsonUtils;

@Controller
@RequestMapping("/impala")
public class ImpalaController {

	@Inject
	private ImpalaService impalaService;

	@RequestMapping(value = "/access", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<AccessList> getAccessResult(@RequestBody String query) throws SQLException {
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
		return impalaService.queryAccessList("parquet_compression.accesslist", p.getQueryParameters(), p.getOrder_by(),
				p.getOrder(), p.getPage_size(), p.getPage());
	}

	//多条件查询parquet_compression.alertlist表
	@RequestMapping(value = "/alert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<AlertList> getAlertResult(@RequestBody String query) throws SQLException {
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
		return impalaService.queryAlertList("parquet_compression.alertlist", p.getQueryParameters(), p.getOrder_by(),
				p.getOrder(), p.getPage_size(), p.getPage());
	}

	//多条件查询parquet_compression.plcclient表
	@RequestMapping(value = "/plcclient", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<PlcClient> getPlcClientResult(@RequestBody String query) throws SQLException {
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
		return impalaService.queryPlcClient("parquet_compression.plcclient", p.getQueryParameters(), p.getOrder_by(),
				p.getOrder(), p.getPage_size(), p.getPage());
	}

}
