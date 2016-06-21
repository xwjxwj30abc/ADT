package zx.soft.adt.core;

import java.sql.ResultSet;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.domain.AccessList;
import zx.soft.adt.domain.AlertList;
import zx.soft.adt.domain.HotPlugLog;
import zx.soft.adt.domain.PlcClient;
import zx.soft.adt.domain.WanIpv4;

public class DataTrans {

	public static Logger logger = LoggerFactory.getLogger(DataTrans.class);

	/**
	 * 将查询结果ResultSet转换为AccessList
	 * @param resultSet
	 * @return AccessList
	 */
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
			result.setVpn1_ip(resultSet.getLong(31));
			result.setJd_vpn1(resultSet.getDouble(32));
			result.setWd_vpn1(resultSet.getDouble(33));
			result.setCountry_name_vpn1(resultSet.getString(34));
			result.setVpn2_ip(resultSet.getLong(35));
			result.setJd_vpn2(resultSet.getDouble(36));
			result.setWd_vpn2(resultSet.getDouble(37));
			result.setCountry_name_vpn2(resultSet.getString(38));
			result.setVpn3_ip(resultSet.getLong(39));
			result.setJd_vpn3(resultSet.getDouble(40));
			result.setWd_vpn3(resultSet.getDouble(41));
			result.setCountry_name_vpn3(resultSet.getString(42));
			result.setCjsj(new Date(resultSet.getLong(43) * 1000));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 将查询结果ResultSet转换为AlertList
	 * @param resultSet
	 * @return AlertList
	 */
	public static AlertList resultSet2AlertList(ResultSet resultSet) {
		AlertList result = new AlertList();
		try {
			result.setRowkey(resultSet.getString(1));
			result.setJd(resultSet.getDouble(2));
			result.setWd(resultSet.getDouble(3));
			result.setCountry_name(resultSet.getString(4));
			result.setDestination_ip(resultSet.getLong(5));
			result.setService_code(resultSet.getLong(6));
			result.setRule_id(resultSet.getString(7));
			result.setService_rule(resultSet.getString(8));
			result.setRule_name(resultSet.getString(9));
			result.setMatching_level(resultSet.getInt(10));
			result.setRule_action(resultSet.getInt(11));
			result.setService_type(resultSet.getInt(12));
			result.setKeyword1(resultSet.getString(13));
			result.setKeyword2(resultSet.getString(14));
			result.setKeyword3(resultSet.getString(15));
			result.setMatching_word(resultSet.getInt(16));
			result.setSet_time(resultSet.getLong(17));
			result.setValidation_time(resultSet.getLong(18));
			result.setManual_pause_time(resultSet.getLong(19));
			result.setFilter_method(resultSet.getInt(20));
			result.setFilter_argument(resultSet.getString(21));
			result.setNet_ending_ip(resultSet.getLong(22));
			result.setNet_ending_mac(resultSet.getLong(23));
			result.setDestination_ipv6(resultSet.getString(24));
			result.setNet_ending_ipv6(resultSet.getString(25));
			result.setMatching_time(resultSet.getLong(26));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 将查询结果ResultSet转换为PlcClient
	 * @param resultSet
	 * @return　PlcClient
	 */
	public static PlcClient resultSet2PlcClient(ResultSet resultSet) {
		PlcClient result = new PlcClient();
		try {
			result.setService_code(resultSet.getLong(2));
			result.setService_name(resultSet.getString(3));
			result.setAddress(resultSet.getString(4));
			result.setZip(resultSet.getString(5));
			result.setPrincipal(resultSet.getString(6));
			result.setPrincipal_tel(resultSet.getString(7));
			result.setInfor_man(resultSet.getString(8));
			result.setInfor_man_tel(resultSet.getString(9));
			result.setInfor_man_email(resultSet.getString(10));
			result.setProducer_code(resultSet.getInt(11));
			result.setStatus(resultSet.getInt(12));
			result.setEnding_number(resultSet.getInt(13));
			result.setServer_number(resultSet.getInt(14));
			result.setIp(resultSet.getString(15));
			result.setNet_type(resultSet.getInt(16));
			result.setPractitioner_number(resultSet.getInt(17));
			result.setNet_monitor_department(resultSet.getString(18));
			result.setNet_monitor_man(resultSet.getString(19));
			result.setNet_monitor_man_tel(resultSet.getString(20));
			result.setRemark(resultSet.getString(21));
			result.setNewSystem(resultSet.getInt(22));
			result.setUnitNo(resultSet.getInt(23));
			result.setSessionID(resultSet.getString(24));
			result.setUdpHost(resultSet.getString(25));
			result.setUdpPort(resultSet.getInt(26));
			result.setUdpVer(resultSet.getString(27));
			result.setComputerOnline(resultSet.getInt(28));
			result.setClientTime(resultSet.getLong(29));
			result.setLogDays(resultSet.getInt(30));
			result.setCommStatus(resultSet.getInt(31));
			result.setCommNormal(resultSet.getInt(32));
			result.setCommTiming(resultSet.getInt(33));
			result.setAlertLogAttr(resultSet.getInt(34));
			result.setUserLogAttr(resultSet.getInt(35));
			result.setDefaultAccessRule(resultSet.getInt(36));
			result.setDevice_ipv4(resultSet.getLong(37));
			result.setDevice_ipv6(resultSet.getString(38));
			result.setDevice_port(resultSet.getInt(39));
			result.setUdp_online(resultSet.getInt(40));
			result.setDevice_serial(resultSet.getString(41));
			result.setDevice_version(resultSet.getString(42));
			result.setDevice_flow1(resultSet.getLong(43));
			result.setDevice_flow2(resultSet.getLong(44));
			result.setDevice_note(resultSet.getString(45));
			result.setUser_name(resultSet.getString(46));
			result.setCertificate_type(resultSet.getString(47));
			result.setCertificate_code(resultSet.getString(48));
			result.setOrg_name(resultSet.getString(49));
			result.setCountry(resultSet.getString(50));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 将查询结果ResultSet转换为WanIpv4
	 * @param resultSet
	 * @return WanIpv4
	 */
	public static WanIpv4 resultSet2WanIpv4(ResultSet resultSet) {
		WanIpv4 result = new WanIpv4();
		try {
			result.setRowkey(resultSet.getString(1));
			result.setAdd_time(resultSet.getLong(2));
			result.setId(resultSet.getInt(3));
			result.setIpv4(resultSet.getString(4));
			result.setService_code(resultSet.getLong(5));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 将查询结果ResultSet转换为HotPlugLog
	 * @param resultSet
	 * @return HotPlugLog
	 */
	public static HotPlugLog result2HotPlugLog(ResultSet resultSet) {
		HotPlugLog result = new HotPlugLog();
		try {
			result.setRowkey(resultSet.getString(1));
			result.setAction(resultSet.getInt(2));
			result.setAdd_time(resultSet.getLong(3));
			result.setDevice(resultSet.getString(4));
			result.setId(resultSet.getInt(5));
			result.setNote(resultSet.getString(6));
			result.setService_code(resultSet.getLong(7));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
}
