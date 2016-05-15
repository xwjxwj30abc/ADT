package zx.soft.adt.domain;

import java.util.List;

public class QueryResult {

	private int sum;
	private List lists;

	public QueryResult() {

	}

	public QueryResult(int sum, List lists) {
		this.sum = sum;
		this.lists = lists;

	}

	public int getNumber() {
		return sum;
	}

	public List getLists() {
		return lists;
	}

	public void setNumber(int sum) {
		this.sum = sum;
	}

	public void setLists(List lists) {
		this.lists = lists;
	}

	@Override
	public String toString() {
		return "QueryResult [sum=" + sum + ", lists=" + lists + "]";
	}

}
