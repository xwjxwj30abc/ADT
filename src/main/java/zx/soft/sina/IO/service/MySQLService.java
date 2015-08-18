package zx.soft.sina.IO.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.core.IOMySQL;

@Service
public class MySQLService {

	@Inject
	private IOMySQL iOMySQL;

	public <T> void insert(List<T> values) {
		for (T value : values) {
			iOMySQL.write("", value);
		}

	}

}
