package zx.soft.impala.adt.core;

public class ConstADT {

	public static String TABLE_ACCESS = "adt.accesslist";//impala
	public static String TABLE_ALERT = "adt.alertlist";//impala
	public static String TABLE_PLCCLIENT = "adt.plcClient";//mysql
	public static String TABLE_PLCNETINFO = "adt.plcnetinfo";//impala
	public static String TABLE_COUNTRYINFO = "adt.countryinfo";//mysql
	public static String TABLE_VPN_TRAFFIC = "adt.vpn_traffic";//impala
	public static String TABLE_VPN_WAN_IPV4 = "adt.vpn_wlan_ip";//impala
	public static String TABLE_HOT_PLUG_LOG = "adt.hot_plug_log";//impala

	public static final String StringFields = "Net_ending_name,Keyword1,Keyword2,Keyword3,Net_ending_ipv6,Destination_ipv6,"
			+ "Keyword21,Keyword22,Keyword23,Keyword24,Keyword25,Country_name,Rule_id,Destination_ipv6,Net_ending_ipv6,User_name,"
			+ "Certificate_type,Certificate_code,Org_name,Country,Service_name,Address,Zip,Principal,Principal_tel,"
			+ "Infor_man,Infor_man_tel,Infor_man_email,Ip,Net_monitor_department,Net_monitor_man,Net_monitor_man_tel."
			+ "Remark,SessionID,UdpHost,UdpVer,Device_ipv6,Device_serial,Device_version,Device_note,ipv4";
}
