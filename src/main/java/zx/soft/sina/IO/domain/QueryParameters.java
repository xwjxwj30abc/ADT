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

	private int operator;
	private String field;
	private String value;

	public int getOperator() {
		return operator;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "QueryParameters [operator=" + operator + ", field=" + field + ", value=" + value + "]";
	}

	public static void main(String[] args) {
		List<QueryParameters> queryParams = new ArrayList<>();
		QueryParameters param = new QueryParameters();
		param.setField("net_type");
		param.setOperator(0);
		param.setValue("99");
		QueryParameters param2 = new QueryParameters();
		param2.setField("id");
		param2.setOperator(1);
		param2.setValue("10");
		queryParams.add(param);
		queryParams.add(param2);
		System.out.println(JsonUtils.toJsonWithoutPretty(queryParams));
	}

}
