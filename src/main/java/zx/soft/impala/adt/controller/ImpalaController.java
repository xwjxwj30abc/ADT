package zx.soft.impala.adt.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
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
import zx.soft.impala.adt.domain.AccessList;
import zx.soft.impala.adt.domain.AlertList;
import zx.soft.impala.adt.domain.HotPlugLog;
import zx.soft.impala.adt.domain.IP2GEO;
import zx.soft.impala.adt.domain.Params;
import zx.soft.impala.adt.domain.QueryParameters;
import zx.soft.impala.adt.domain.QueryResult;
import zx.soft.impala.adt.domain.Stat;
import zx.soft.impala.adt.domain.WanIpv4;
import zx.soft.impala.adt.service.ImpalaService;
import zx.soft.impala.adt.service.MySQLService;

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
		List<QueryParameters> queryParameters = p.getQueryParameters();
		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
		p.setQueryParameters(queryParameters);
		List<AccessList> lists = impalaService.getAccessListQueryResult(ConstADT.TABLE_ACCESS, p);
		int number = impalaService.getSum(ConstADT.TABLE_ACCESS, p);
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
		List<QueryParameters> queryParameters = p.getQueryParameters();
		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
		List<AlertList> lists = impalaService.getAlertListQueryResult(ConstADT.TABLE_ALERT, p);
		int number = impalaService.getSum(ConstADT.TABLE_ALERT, p);
		return new QueryResult(number, lists);
	}

	//多条件查询hot_plug_log表
	@RequestMapping(value = "/hotpluglog", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody QueryResult getHotPlugLog(@RequestBody Params p) throws SQLException {
		if (p.getOrder() == "") {
			p.setOrder("DESC");
		}
		if (p.getOrder_by() == "") {
			p.setOrder_by("add_time");
		}
		if (p.getPage_size() == 0) {
			p.setPage_size(20);
		}
		List<QueryParameters> queryParameters = p.getQueryParameters();
		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
		p.setQueryParameters(queryParameters);
		List<HotPlugLog> lists = impalaService.getHotPlugLogQueryResult(ConstADT.TABLE_HOT_PLUG_LOG, p);
		int number = impalaService.getSum(ConstADT.TABLE_HOT_PLUG_LOG, p);
		return new QueryResult(number, lists);
	}

	//查看具体一条accesslist记录
	@RequestMapping(value = "/access/info", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Object getSpecAccess(@RequestParam("rowkey") String rowkey) {
		return impalaService.getSpecObject(ConstADT.TABLE_ACCESS, rowkey, AccessList.class);
	}

	//查看具体一条alertlist记录
	@RequestMapping(value = "/alert/info", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Object getSpecAlert(@RequestParam("rowkey") String rowkey) {
		return impalaService.getSpecObject(ConstADT.TABLE_ALERT, rowkey, AlertList.class);
	}

	//查看具体一条hot_plug_log记录
	@RequestMapping(value = "/hotpluglog/info", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Object getSpecHotPlugLog(@RequestParam("rowkey") String rowkey) {
		return impalaService.getSpecObject(ConstADT.TABLE_HOT_PLUG_LOG, rowkey, HotPlugLog.class);
	}

	//上网结果统计accesslist
	@RequestMapping(value = "/access/stats/service_code", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map getStatistics(@RequestBody Params p) {
		List<QueryParameters> queryParameters = p.getQueryParameters();
		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
		return impalaService.getAccessStatByServiceCode(ConstADT.TABLE_ACCESS, queryParameters, 10);
	}

	//一段时间内上网趋势统计
	@RequestMapping(value = "/access/trend", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map getAccessTrendency(@RequestBody Params p) {
		List<QueryParameters> queryParameters = p.getQueryParameters();
		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
		return impalaService.getAccessTrendency(ConstADT.TABLE_ACCESS, p);
	}

	//	@RequestMapping(value = "/access/trend", method = RequestMethod.POST)
	//	@ResponseStatus(HttpStatus.OK)
	//	public @ResponseBody String getTrendency(@RequestBody Params p, @RequestParam("start") long start,
	//			@RequestParam("end") long end) {
	//		if (p.getOrder() == "") {
	//			p.setOrder("DESC");
	//		}
	//		if (p.getOrder_by() == "") {
	//			p.setOrder_by("Time");
	//		}
	//		if (p.getPage_size() == 0) {
	//			p.setPage_size(20);
	//		}
	//		List<QueryParameters> queryParameters = p.getQueryParameters();
	//		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
	//		QueryParameters q = new QueryParameters();
	//		q.setField("time");
	//		q.setOpera(2);
	//		q.setValue(String.valueOf(start) + "," + String.valueOf(end));
	//		queryParameters.add(q);
	//		return impalaService.getTrendency(ConstADT.TABLE_ACCESS, p);
	//	}

	//过滤结果表：根据国家，省名称获取一定查询条件下的访问不同国家的数据总数以及目的地址的经纬度
	@RequestMapping(value = "/alert/stats/country", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Stat> getAlertStatByCountryName(@RequestBody Params p) {
		List<QueryParameters> queryParameters = p.getQueryParameters();
		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
		List<Stat> stats = new ArrayList<>();
		stats = impalaService.getAlertStatByCountryName(ConstADT.TABLE_ALERT, queryParameters);
		//      List<Stat> ast = new ArrayList<>();
		//		if (geoMap == null) {
		//			geoMap = mySQLService.initGEO("countryinfo");
		//		}
		//		Stat as = null;
		//		if (map.size() > 0) {
		//			for (Entry<String, Long> entry : map.entrySet()) {
		//				as = new Stat();
		//				as.setCount(entry.getValue());
		//				as.setCountry_name(entry.getKey());
		//				try {
		//					if (!entry.getKey().startsWith("中国") | entry.getKey().equals("中国")) {//中国和不是以中国开始的字符串国家，直接获取经纬度
		//						as.setJd(geoMap.get(entry.getKey()).getJD());
		//						as.setWd(geoMap.get(entry.getKey()).getWD());
		//					} else {
		//						//对于中国的不同省的统计额外处理（数据库中存安徽，而根据国家名称统计时以中国安徽出现）
		//						as.setJd(geoMap.get(entry.getKey().substring(2, entry.getKey().length())).getJD());
		//						as.setWd(geoMap.get(entry.getKey().substring(2, entry.getKey().length())).getWD());
		//					}
		//
		//				} catch (NullPointerException e) {
		//					as.setJd(0.0);
		//					as.setWd(0.0);
		//				}
		//				ast.add(as);
		//			}
		//		}
		return stats;
	}

	//过滤结果表统计分析接口－根据设备统计过滤结果
	@RequestMapping(value = "/alert/stats/{group_by}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map getAlertStats(@RequestBody Params p, @PathVariable("group_by") String group_by) {
		List<QueryParameters> queryParameters = p.getQueryParameters();
		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
		Map<String, Integer> map = new HashMap<>();
		if (group_by.equalsIgnoreCase("service_rule")) {
			map = impalaService.getAlertStatsByServiceRule(ConstADT.TABLE_ALERT, queryParameters, p.getPage_size());
		} else if (group_by.equalsIgnoreCase("service_code")) {
			map = impalaService.getAlertStatsByServiceCode(ConstADT.TABLE_ALERT, queryParameters, p.getPage_size());
		}
		return map;
	}

	//指定设备的上网流量统计结果
	@RequestMapping(value = "/traffic/stats", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map getTraffic(@RequestBody Params p) {
		List<QueryParameters> queryParameters = p.getQueryParameters();
		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
		Map map = impalaService.getSummaryTrafficByServiceName(ConstADT.TABLE_VPN_TRAFFIC, queryParameters);
		return map;
	}

	//查询具体一段时间内的流量所涉及到的ip
	@RequestMapping(value = "/traffic/stats/ip", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map getSpecificTraffic(@RequestBody Params p) {
		List<QueryParameters> queryParameters = p.getQueryParameters();
		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
		Map map = impalaService.getSpecificIPTranffic(ConstADT.TABLE_VPN_TRAFFIC, queryParameters);
		return map;
	}

	//查看不同设备出口的公网地址
	@RequestMapping(value = "/wanipv4/ip", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody QueryResult getWanIpv4(@RequestBody Params p) {
		if (p.getOrder() == "") {
			p.setOrder("DESC");
		}
		if (p.getOrder_by() == "") {
			p.setOrder_by("add_time");
		}
		if (p.getPage_size() == 0) {
			p.setPage_size(10);
		}
		List<QueryParameters> queryParameters = p.getQueryParameters();
		queryParameters = this.changeQueryServiceName2ServiceCode(queryParameters);
		p.setQueryParameters(queryParameters);
		int num = impalaService.getSum(ConstADT.TABLE_VPN_WAN_IPV4, p);
		List<WanIpv4> lists = impalaService.getWanIpv4(ConstADT.TABLE_VPN_WAN_IPV4, p);
		return new QueryResult(num, lists);
	}

	//将查询条件是Service_name的转换为Service_code并且将Service_code字段用IN进行限制
	private List<QueryParameters> changeQueryServiceName2ServiceCode(List<QueryParameters> queryParameters) {
		for (int i = 0; i < queryParameters.size(); i++) {
			QueryParameters queryParameter = queryParameters.get(i);
			if (queryParameter.getField().equalsIgnoreCase("service_name")) {
				List<Long> Service_codes = mySQLService.getMappingServiceCodeByServiceName(ConstADT.TABLE_PLCCLIENT,
						queryParameter.getValue());
				QueryParameters query_tmp = new QueryParameters();
				query_tmp.setField("Service_code");
				query_tmp.setOpera(-2);
				query_tmp.setValue("(" + StringUtils.join(Service_codes.toArray(), ",") + ")");
				queryParameters.set(i, query_tmp);
			}
			if (queryParameter.getField().equalsIgnoreCase("service_code")) {
				QueryParameters query_tmp = new QueryParameters();
				String service_codes = queryParameter.getValue();
				query_tmp.setField("Service_code");
				query_tmp.setOpera(-2);
				query_tmp.setValue("(" + service_codes + ")");
				queryParameters.set(i, query_tmp);
			}
		}
		return queryParameters;
	}

}
