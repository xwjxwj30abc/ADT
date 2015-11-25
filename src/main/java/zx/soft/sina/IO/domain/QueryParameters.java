package zx.soft.sina.IO.domain;

import java.util.ArrayList;
import java.util.List;

import zx.soft.utils.json.JsonUtils;

/**
 *　查询条件
 * @author xuwenjuan
 *
 */
public class QueryParameters {

	private int opera;
	private String field = "";
	private String value = "";

	public QueryParameters() {

	}

	public QueryParameters(int opera, String field, String value) {
		this.opera = opera;
		this.field = field;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getOpera() {
		return opera;
	}

	public void setOpera(int opera) {
		this.opera = opera;
	}

	@Override
	public String toString() {
		return "QueryParameters [opera=" + opera + ", field=" + field + ", value=" + value + "]";
	}

	public static void main(String[] args) {
		List<QueryParameters> queryParams = new ArrayList<>();
		QueryParameters param = new QueryParameters();
		param.setField("Device_version");
		param.setOpera(0);
		param.setValue("20150212");
		queryParams.add(param);
		System.out.println(JsonUtils.toJsonWithoutPretty(queryParams));
	}

}
