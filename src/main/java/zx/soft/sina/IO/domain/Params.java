package zx.soft.sina.IO.domain;

import java.util.ArrayList;
import java.util.List;

import zx.soft.sina.IO.util.JsonUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Params {

	@JsonIgnore
	private List<QueryParameters> queryParameters = new ArrayList<>();
	@JsonIgnore
	private String order_by = "";
	@JsonIgnore
	private String order = "";
	@JsonIgnore
	private int page_size;
	@JsonIgnore
	private int page;

	public Params() {
	}

	public List<QueryParameters> getQueryParameters() {
		return queryParameters;
	}

	public String getOrder_by() {
		return order_by;
	}

	public String getOrder() {
		return order;
	}

	public int getPage_size() {
		return page_size;
	}

	public int getPage() {
		return page;
	}

	public void setQueryParameters(List<QueryParameters> queryParameters) {
		this.queryParameters = queryParameters;
	}

	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "Params [queryParameters=" + queryParameters + ", order_by=" + order_by + ", order=" + order
				+ ", page_size=" + page_size + ", page=" + page + "]";
	}

	public static void main(String[] args) {

		Params p = new Params();
		List<QueryParameters> queryParameters = new ArrayList<>();
		queryParameters.add(new QueryParameters(0, "rule_id", "34010101201507220211"));
		p.setQueryParameters(queryParameters);
		//{"queryParameters":[{"opera":0,"field":"id","value":"1"}],"order_by":"id","order":"DESC","page_size":2,"page":1}
		String ps = JsonUtils.toJsonWithoutPretty(p);
		System.out.println(ps);
		Params pa = JsonUtils.getObject(ps, Params.class);
		System.out.println(JsonUtils.toJson(pa));
	}
}
