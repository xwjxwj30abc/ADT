package zx.soft.adt.domain;

import java.util.ArrayList;
import java.util.List;

import zx.soft.adt.util.JsonUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Params {

	private List<QueryParameters> queryParameters = new ArrayList<>();
	private String order_by = "";
	private String order = "";
	private int page_size;
	private int page;

	public Params() {
	}

	@JsonProperty
	public List<QueryParameters> getQueryParameters() {
		return queryParameters;
	}

	@JsonProperty
	public String getOrder_by() {
		return order_by;
	}

	@JsonProperty
	public String getOrder() {
		return order;
	}

	@JsonProperty
	public int getPage_size() {
		return page_size;
	}

	@JsonProperty
	public int getPage() {
		return page;
	}

	@JsonIgnore
	public void setQueryParameters(List<QueryParameters> queryParameters) {
		this.queryParameters = queryParameters;
	}

	@JsonIgnore
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}

	@JsonIgnore
	public void setOrder(String order) {
		this.order = order;
	}

	@JsonIgnore
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	@JsonIgnore
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
		queryParameters.add(new QueryParameters(1, "id", "0"));
		queryParameters.add(new QueryParameters(2, "Time", "1446267600,1446270000"));
		p.setQueryParameters(queryParameters);
		p.setOrder("DESC");
		p.setOrder_by("Time");
		p.setPage_size(20);
		//{"queryParameters":[{"opera":0,"field":"id","value":"1"}],"order_by":"id","order":"DESC","page_size":2,"page":1}
		String ps = JsonUtils.toJsonWithoutPretty(p);
		System.out.println(ps);
		Params pa = JsonUtils.getObject(ps, Params.class);
		System.out.println(JsonUtils.toJson(pa));
	}
}
