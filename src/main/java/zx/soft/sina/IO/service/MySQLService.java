package zx.soft.sina.IO.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sina.IO.core.IOMySQL;
import zx.soft.sina.IO.domain.PlcNetInfo;

@Service
public class MySQLService {

	@Inject
	private IOMySQL iOMySQL;

	public <T> void insert(List<T> values) {
		for (T value : values) {
			iOMySQL.write("", value);
		}

	}

	public void insertPlcNetInfo(PlcNetInfo info) {
		iOMySQL.insertPlcNetInfo(info);
	}
}
