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

import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.domain.Result;
import zx.soft.sina.IO.service.ImpalaService;

@Controller
@RequestMapping("/impala")
public class ImpalaController {

	@Inject
	private ImpalaService impalaService;

	@RequestMapping(value = "/weibo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Result> getQueryResult(@RequestBody List<QueryParameters> queryParams)
			throws SQLException {
		return impalaService.query("weibo_sina", queryParams);
	}
}
