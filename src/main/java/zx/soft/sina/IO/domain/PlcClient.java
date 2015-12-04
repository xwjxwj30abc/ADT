package zx.soft.sina.IO.domain;

import zx.soft.sina.IO.util.JsonUtils;

public class PlcClient {

	private long Service_code;
	private String Service_name = "";
	private String Address = "";
	private String Zip = "";
	private String Principal = "";
	private String Principal_tel = "";
	private String Infor_man = "";
	private String Infor_man_tel = "";
	private String Infor_man_email = "";
	private int Producer_code = 99;
	private int Status = 4;
	private int Ending_number;
	private int Server_number;
	private String Ip = "";
	private int Net_type = 99;
	private int Practitioner_number;
	private String Net_monitor_department = "";
	private String Net_monitor_man = "";
	private String Net_monitor_man_tel = "";
	private String Remark = "";
	private int NewSystem;
	private int UnitNo;
	private String SessionID = "";
	private String UdpHost = "";
	private int UdpPort;
	private String UdpVer = "";
	private int ComputerOnline;
	private long ClientTime;
	private int LogDays = 60;
	private int CommStatus = 60;
	private int CommNormal = 10;
	private int CommTiming = 10;
	private int AlertLogAttr;
	private int UserLogAttr;
	private int DefaultAccessRule;
	private int Device_ipv4;
	private String Device_ipv6 = "";
	private int Device_port;
	private int Udp_online;
	private String Device_serial = "";
	private String Device_version = "20120801";
	private long Device_flow1;
	private long Device_flow2;
	private String Device_note = "";

	public PlcClient() {
	}

	public long getService_code() {
		return Service_code;
	}

	public String getService_name() {
		return Service_name;
	}

	public void setService_code(long service_code) {
		Service_code = service_code;
	}

	public void setService_name(String service_name) {
		Service_name = service_name;
	}

	public String getAddress() {
		return Address;
	}

	public String getZip() {
		return Zip;
	}

	public String getPrincipal() {
		return Principal;
	}

	public String getPrincipal_tel() {
		return Principal_tel;
	}

	public String getInfor_man() {
		return Infor_man;
	}

	public String getInfor_man_tel() {
		return Infor_man_tel;
	}

	public String getInfor_man_email() {
		return Infor_man_email;
	}

	public int getProducer_code() {
		return Producer_code;
	}

	public int getStatus() {
		return Status;
	}

	public int getEnding_number() {
		return Ending_number;
	}

	public int getServer_number() {
		return Server_number;
	}

	public String getIp() {
		return Ip;
	}

	public int getNet_type() {
		return Net_type;
	}

	public int getPractitioner_number() {
		return Practitioner_number;
	}

	public String getNet_monitor_department() {
		return Net_monitor_department;
	}

	public String getNet_monitor_man() {
		return Net_monitor_man;
	}

	public String getNet_monitor_man_tel() {
		return Net_monitor_man_tel;
	}

	public String getRemark() {
		return Remark;
	}

	public int getNewSystem() {
		return NewSystem;
	}

	public int getUnitNo() {
		return UnitNo;
	}

	public String getSessionID() {
		return SessionID;
	}

	public String getUdpHost() {
		return UdpHost;
	}

	public int getUdpPort() {
		return UdpPort;
	}

	public String getUdpVer() {
		return UdpVer;
	}

	public int getComputerOnline() {
		return ComputerOnline;
	}

	public long getClientTime() {
		return ClientTime;
	}

	public int getLogDays() {
		return LogDays;
	}

	public int getCommStatus() {
		return CommStatus;
	}

	public int getCommNormal() {
		return CommNormal;
	}

	public int getCommTiming() {
		return CommTiming;
	}

	public int getAlertLogAttr() {
		return AlertLogAttr;
	}

	public int getUserLogAttr() {
		return UserLogAttr;
	}

	public int getDefaultAccessRule() {
		return DefaultAccessRule;
	}

	public int getDevice_ipv4() {
		return Device_ipv4;
	}

	public String getDevice_ipv6() {
		return Device_ipv6;
	}

	public int getDevice_port() {
		return Device_port;
	}

	public int getUdp_online() {
		return Udp_online;
	}

	public String getDevice_serial() {
		return Device_serial;
	}

	public String getDevice_version() {
		return Device_version;
	}

	public long getDevice_flow1() {
		return Device_flow1;
	}

	public long getDevice_flow2() {
		return Device_flow2;
	}

	public String getDevice_note() {
		return Device_note;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public void setZip(String zip) {
		Zip = zip;
	}

	public void setPrincipal(String principal) {
		Principal = principal;
	}

	public void setPrincipal_tel(String principal_tel) {
		Principal_tel = principal_tel;
	}

	public void setInfor_man(String infor_man) {
		Infor_man = infor_man;
	}

	public void setInfor_man_tel(String infor_man_tel) {
		Infor_man_tel = infor_man_tel;
	}

	public void setInfor_man_email(String infor_man_email) {
		Infor_man_email = infor_man_email;
	}

	public void setProducer_code(int producer_code) {
		Producer_code = producer_code;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public void setEnding_number(int ending_number) {
		Ending_number = ending_number;
	}

	public void setServer_number(int server_number) {
		Server_number = server_number;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	public void setNet_type(int net_type) {
		Net_type = net_type;
	}

	public void setPractitioner_number(int practitioner_number) {
		Practitioner_number = practitioner_number;
	}

	public void setNet_monitor_department(String net_monitor_department) {
		Net_monitor_department = net_monitor_department;
	}

	public void setNet_monitor_man(String net_monitor_man) {
		Net_monitor_man = net_monitor_man;
	}

	public void setNet_monitor_man_tel(String net_monitor_man_tel) {
		Net_monitor_man_tel = net_monitor_man_tel;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public void setNewSystem(int newSystem) {
		NewSystem = newSystem;
	}

	public void setUnitNo(int unitNo) {
		UnitNo = unitNo;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public void setUdpHost(String udpHost) {
		UdpHost = udpHost;
	}

	public void setUdpPort(int udpPort) {
		UdpPort = udpPort;
	}

	public void setUdpVer(String udpVer) {
		UdpVer = udpVer;
	}

	public void setComputerOnline(int computerOnline) {
		ComputerOnline = computerOnline;
	}

	public void setClientTime(long clientTime) {
		ClientTime = clientTime;
	}

	public void setLogDays(int logDays) {
		LogDays = logDays;
	}

	public void setCommStatus(int commStatus) {
		CommStatus = commStatus;
	}

	public void setCommNormal(int commNormal) {
		CommNormal = commNormal;
	}

	public void setCommTiming(int commTiming) {
		CommTiming = commTiming;
	}

	public void setAlertLogAttr(int alertLogAttr) {
		AlertLogAttr = alertLogAttr;
	}

	public void setUserLogAttr(int userLogAttr) {
		UserLogAttr = userLogAttr;
	}

	public void setDefaultAccessRule(int defaultAccessRule) {
		DefaultAccessRule = defaultAccessRule;
	}

	public void setDevice_ipv4(int device_ipv4) {
		Device_ipv4 = device_ipv4;
	}

	public void setDevice_ipv6(String device_ipv6) {
		Device_ipv6 = device_ipv6;
	}

	public void setDevice_port(int device_port) {
		Device_port = device_port;
	}

	public void setUdp_online(int udp_online) {
		Udp_online = udp_online;
	}

	public void setDevice_serial(String device_serial) {
		Device_serial = device_serial;
	}

	public void setDevice_version(String device_version) {
		Device_version = device_version;
	}

	public void setDevice_flow1(long device_flow1) {
		Device_flow1 = device_flow1;
	}

	public void setDevice_flow2(long device_flow2) {
		Device_flow2 = device_flow2;
	}

	public void setDevice_note(String device_note) {
		Device_note = device_note;
	}

	@Override
	public String toString() {
		return "PlcClient [Service_code=" + Service_code + ", Service_name=" + Service_name + ", Address=" + Address
				+ ", Zip=" + Zip + ", Principal=" + Principal + ", Principal_tel=" + Principal_tel + ", Infor_man="
				+ Infor_man + ", Infor_man_tel=" + Infor_man_tel + ", Infor_man_email=" + Infor_man_email
				+ ", Producer_code=" + Producer_code + ", Status=" + Status + ", Ending_number=" + Ending_number
				+ ", Server_number=" + Server_number + ", Ip=" + Ip + ", Net_type=" + Net_type
				+ ", Practitioner_number=" + Practitioner_number + ", Net_monitor_department=" + Net_monitor_department
				+ ", Net_monitor_man=" + Net_monitor_man + ", Net_monitor_man_tel=" + Net_monitor_man_tel + ", Remark="
				+ Remark + ", NewSystem=" + NewSystem + ", UnitNo=" + UnitNo + ", SessionID=" + SessionID
				+ ", UdpHost=" + UdpHost + ", UdpPort=" + UdpPort + ", UdpVer=" + UdpVer + ", ComputerOnline="
				+ ComputerOnline + ", ClientTime=" + ClientTime + ", LogDays=" + LogDays + ", CommStatus=" + CommStatus
				+ ", CommNormal=" + CommNormal + ", CommTiming=" + CommTiming + ", AlertLogAttr=" + AlertLogAttr
				+ ", UserLogAttr=" + UserLogAttr + ", DefaultAccessRule=" + DefaultAccessRule + ", Device_ipv4="
				+ Device_ipv4 + ", Device_ipv6=" + Device_ipv6 + ", Device_port=" + Device_port + ", Udp_online="
				+ Udp_online + ", Device_serial=" + Device_serial + ", Device_version=" + Device_version
				+ ", Device_flow1=" + Device_flow1 + ", Device_flow2=" + Device_flow2 + ", Device_note=" + Device_note
				+ "]";
	}

	public static void main(String[] args) {
		PlcClient p = new PlcClient();
		p.setService_code(982354L);
		p.setService_name("服务名");
		System.out.println(JsonUtils.toJsonWithoutPretty(p));
	}
}
