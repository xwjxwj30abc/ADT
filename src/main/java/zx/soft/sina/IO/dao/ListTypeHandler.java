package zx.soft.sina.IO.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(List.class)
@MappedJdbcTypes({ JdbcType.VARCHAR })
public class ListTypeHandler extends BaseTypeHandler<List<String>> {

	private List<String> deserializeFromString(String columValue) {
		if (columValue == null) {
			return null;
		}
		List<String> list = new ArrayList<>();
		for (String value : columValue.split(",")) {
			list.add(value);
		}
		return list;

	}

	private String serializeToString(List<String> values) {
		StringBuffer result = new StringBuffer();
		for (String value : values) {
			result.append(value).append(",");
		}
		result.deleteCharAt(result.length() - 1);
		return result.toString();
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, serializeToString(parameter));
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return deserializeFromString(rs.getString(columnName));
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return deserializeFromString(rs.getString(columnIndex));
	}

	@Override
	public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return deserializeFromString(cs.getString(columnIndex));
	}
}
