package zx.soft.impala.adt.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sina.IO.domain.AccessList;
import zx.soft.sina.IO.domain.AlertList;
import zx.soft.sina.IO.domain.PlcClient;
import zx.soft.sina.IO.util.ImpalaConnection;

public class DataTrans {

	public static Logger logger = LoggerFactory.getLogger(DataTrans.class);
	public static Map<String, String> map;
	static {
		map = new HashMap<String, String>();
		updateMap();
	}

	public static void updateMap() {

		String sqlStatement = "SELECT rule_id,rule_name FROM " + ConstADT.TABLE_PLCNETINFO;
		try (Connection conn = ImpalaConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					if (resultSet.getString(1) != null) {
						map.put(resultSet.getString(1), resultSet.getString(2));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static AccessList resultSet2Access(ResultSet resultSet) {
		AccessList result = new AccessList();
		try {
			result.setRowkey(resultSet.getString(1));
			result.setId(resultSet.getLong(2));
			result.setService_code(resultSet.getLong(3));
			result.setNet_ending_ip(resultSet.getLong(4));
			result.setNet_ending_name(resultSet.getString(5));
			result.setTime(resultSet.getLong(6));
			result.setNet_ending_mac(resultSet.getLong(7));
			result.setDestination_ip(resultSet.getLong(8));
			result.setPort(resultSet.getInt(9));
			result.setService_type(resultSet.getInt(10));
			result.setKeyword1(resultSet.getString(11));
			result.setKeyword2(resultSet.getString(12));
			result.setKeyword3(resultSet.getString(13));
			result.setMac(resultSet.getLong(14));
			result.setSource_port(resultSet.getInt(15));
			result.setNet_ending_ipv6(resultSet.getString(16));
			result.setDestination_ipv6(resultSet.getString(17));
			result.setKeyword11(resultSet.getInt(18));
			result.setKeyword12(resultSet.getInt(19));
			result.setKeyword13(resultSet.getInt(20));
			result.setKeyword14(resultSet.getInt(21));
			result.setKeyword15(resultSet.getLong(22));
			result.setKeyword21(resultSet.getString(23));
			result.setKeyword22(resultSet.getString(24));
			result.setKeyword23(resultSet.getString(25));
			result.setKeyword24(resultSet.getString(26));
			result.setKeyword25(resultSet.getString(27));
			result.setJd(resultSet.getDouble(28));
			result.setWd(resultSet.getDouble(29));
			result.setCountry_name(resultSet.getString(30));
			result.setCjsj(new Date(resultSet.getLong(31)));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public static AlertList resultSet2AlertList(ResultSet resultSet) {
		AlertList result = new AlertList();
		try {
			result.setRowkey(resultSet.getString(1));
			result.setId(resultSet.getLong(2));
			result.setService_code(resultSet.getLong(3));
			result.setRule_id(resultSet.getString(4));
			result.setDestination_ip(resultSet.getLong(5));
			result.setNet_ending_ip(resultSet.getLong(6));
			result.setNet_ending_mac(resultSet.getLong(7));
			result.setDestination_ipv6(resultSet.getString(8));
			result.setNet_ending_ipv6(resultSet.getString(9));
			result.setMatching_time(resultSet.getLong(10));
			result.setService_type(resultSet.getInt(11));
			result.setKeyword1(resultSet.getString(12));
			result.setKeyword2(resultSet.getString(13));
			result.setKeyword3(resultSet.getString(14));
			result.setUser_name(resultSet.getString(15));
			result.setCertificate_type(resultSet.getString(16));
			result.setCertificate_code(resultSet.getString(17));
			result.setOrg_name(resultSet.getString(18));
			result.setCountry(resultSet.getString(19));
			result.setJd(resultSet.getDouble(20));
			result.setWd(resultSet.getDouble(21));
			result.setCountry_name(resultSet.getString(22));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public static PlcClient resultSet2PlcClient(ResultSet resultSet) {
		PlcClient result = new PlcClient();
		try {
			result.setRowkey(resultSet.getString(1));
			result.setId(resultSet.getLong(2));
			result.setService_code(resultSet.getLong(3));
			result.setService_name(resultSet.getString(4));
			result.setAddress(resultSet.getString(5));
			result.setZip(resultSet.getString(6));
			result.setPrincipal(resultSet.getString(7));
			result.setPrincipal_tel(resultSet.getString(8));
			result.setInfor_man(resultSet.getString(9));
			result.setInfor_man_tel(resultSet.getString(10));
			result.setInfor_man_email(resultSet.getString(11));
			result.setProducer_code(resultSet.getInt(12));
			result.setStatus(resultSet.getInt(13));
			result.setEnding_number(resultSet.getInt(14));
			result.setServer_number(resultSet.getInt(15));
			result.setIp(resultSet.getString(16));
			result.setNet_type(resultSet.getInt(17));
			result.setPractitioner_number(resultSet.getInt(18));
			result.setNet_monitor_department(resultSet.getString(19));
			result.setNet_monitor_man(resultSet.getString(20));
			result.setNet_monitor_man_tel(resultSet.getString(21));
			result.setRemark(resultSet.getString(22));
			result.setNewSystem(resultSet.getInt(23));
			result.setUnitNo(resultSet.getInt(24));
			result.setSessionID(resultSet.getString(25));
			result.setUdpHost(resultSet.getString(26));
			result.setUdpPort(resultSet.getInt(27));
			result.setUdpVer(resultSet.getString(28));
			result.setComputerOnline(resultSet.getInt(29));
			result.setClientTime(resultSet.getLong(30));
			result.setLogDays(resultSet.getInt(31));
			result.setCommStatus(resultSet.getInt(32));
			result.setCommNormal(resultSet.getInt(33));
			result.setCommTiming(resultSet.getInt(34));
			result.setAlertLogAttr(resultSet.getInt(35));
			result.setUserLogAttr(resultSet.getInt(36));
			result.setDefaultAccessRule(resultSet.getInt(37));
			result.setDevice_ipv4(resultSet.getString(38));
			result.setDevice_ipv6(resultSet.getString(39));
			result.setDevice_port(resultSet.getInt(40));
			result.setUdp_online(resultSet.getInt(41));
			result.setDevice_serial(resultSet.getString(42));
			result.setDevice_version(resultSet.getString(43));
			result.setDevice_flow1(resultSet.getLong(44));
			result.setDevice_flow2(resultSet.getLong(45));
			result.setDevice_note(resultSet.getString(46));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
}
