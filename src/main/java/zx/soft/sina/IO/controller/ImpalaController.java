package zx.soft.sina.IO.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import zx.soft.sina.IO.domain.IP2GEO;
import zx.soft.sina.IO.domain.Params;
import zx.soft.sina.IO.domain.QueryParameters;
import zx.soft.sina.IO.domain.QueryResult;
import zx.soft.sina.IO.domain.Stat;
import zx.soft.sina.IO.service.ImpalaService;
import zx.soft.sina.IO.service.MySQLService;

@Controller
@RequestMapping("/impala")
public class ImpalaController {

	@Inject
	private ImpalaService impalaService;
	@Inject
	private MySQLService mySQLService;

	public static Map<String, IP2GEO> geoMap = null;

	@RequestMapping(value = "/access", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody QueryResult getAccessResult(@RequestBody Params p) throws SQLException {
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
	public @ResponseBody QueryResult getAlertResult(@RequestBody Params p) throws SQLException {
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

	//上网结果统计accesslist
	@RequestMapping(value = "/access/stats/{group_by}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getStatistics(@RequestBody Params p, @PathVariable("group_by") String group_by) {
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

	//上网日志表：根据国家，省名称获取一定查询条件下的访问不同国家的数据总数以及目的地址的经纬度
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alert/stats/country", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Stat> getAccessStat(@RequestBody Params p) {
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
		@SuppressWarnings("unchecked")
		Map<String, Long> map = impalaService.getStat(ConstADT.TABLE_ALERT, p.getQueryParameters(), "Country_name");
		List<Stat> ast = new ArrayList<>();
		if (geoMap == null) {
			geoMap = mySQLService.initGEO("countryinfo");
		}
		Stat as = null;
		if (map.size() > 0) {
			for (Entry<String, Long> entry : map.entrySet()) {
				as = new Stat();
				as.setCount(entry.getValue());
				as.setCountry_name(entry.getKey());
				try {
					if (!entry.getKey().startsWith("中国") | entry.getKey().equals("中国")) {//中国和不是以中国开始的字符串国家，直接获取经纬度
						as.setJd(geoMap.get(entry.getKey()).getJD());
						as.setWd(geoMap.get(entry.getKey()).getWD());
					} else {
						//对于中国的不同省的统计额外处理（数据库中存安徽，而根据国家名称统计时以中国安徽出现）
						as.setJd(geoMap.get(entry.getKey().substring(2, entry.getKey().length())).getJD());
						as.setWd(geoMap.get(entry.getKey().substring(2, entry.getKey().length())).getWD());
					}

				} catch (NullPointerException e) {
					as.setJd(0.0);
					as.setWd(0.0);
				}
				ast.add(as);
			}
		}
		return ast;
	}

	//一段时间内上网趋势统计
	@RequestMapping(value = "/access/trend", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getTrendency(@RequestParam("start") long start, @RequestParam("end") long end) {
		return impalaService.getTrendency(ConstADT.TABLE_ACCESS, start, end);
	}

	//过滤结果终端来源统计接口
	@RequestMapping(value = "/alert/stats/{group_by}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getAlert(@RequestBody Params p, @PathVariable("group_by") String group_by) {
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
