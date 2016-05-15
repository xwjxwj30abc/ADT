package zx.soft.impala.adt.domain;

public class AlertList {

	private String rowkey = "";
	private double Jd;
	private double Wd;
	private String Country_name = "";
	private long Destination_ip;
	private long Service_code;
	private String Service_name = "";
	private String Rule_id = "";
	private String Rule_name = "";
	private String Service_rule = "";

	private int Matching_level;
	private int Rule_action;
	private int Service_type;
	private String Keyword1 = "";
	private String Keyword2 = "";
	private String Keyword3 = "";
	private int Matching_word;
	private long Set_time;
	private long Validation_time;
	private long Manual_pause_time;
	private int Filter_method;
	private String Filter_argument = "";

	private long Net_ending_ip;
	private long Net_ending_mac;
	private String Destination_ipv6 = "";
	private String Net_ending_ipv6 = "";
	private long Matching_time;

	public String getRowkey() {
		return rowkey;
	}

	public double getJd() {
		return Jd;
	}

	public double getWd() {
		return Wd;
	}

	public String getCountry_name() {
		return Country_name;
	}

	public long getDestination_ip() {
		return Destination_ip;
	}

	public long getService_code() {
		return Service_code;
	}

	public String getService_name() {
		return Service_name;
	}

	public String getRule_id() {
		return Rule_id;
	}

	public String getRule_name() {
		return Rule_name;
	}

	public String getService_rule() {
		return Service_rule;
	}

	public int getMatching_level() {
		return Matching_level;
	}

	public int getRule_action() {
		return Rule_action;
	}

	public int getService_type() {
		return Service_type;
	}

	public String getKeyword1() {
		return Keyword1;
	}

	public String getKeyword2() {
		return Keyword2;
	}

	public String getKeyword3() {
		return Keyword3;
	}

	public int getMatching_word() {
		return Matching_word;
	}

	public long getSet_time() {
		return Set_time;
	}

	public long getValidation_time() {
		return Validation_time;
	}

	public long getManual_pause_time() {
		return Manual_pause_time;
	}

	public int getFilter_method() {
		return Filter_method;
	}

	public String getFilter_argument() {
		return Filter_argument;
	}

	public long getNet_ending_ip() {
		return Net_ending_ip;
	}

	public long getNet_ending_mac() {
		return Net_ending_mac;
	}

	public String getDestination_ipv6() {
		return Destination_ipv6;
	}

	public String getNet_ending_ipv6() {
		return Net_ending_ipv6;
	}

	public long getMatching_time() {
		return Matching_time;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public void setJd(double jd) {
		Jd = jd;
	}

	public void setWd(double wd) {
		Wd = wd;
	}

	public void setCountry_name(String country_name) {
		Country_name = country_name;
	}

	public void setDestination_ip(long destination_ip) {
		Destination_ip = destination_ip;
	}

	public void setService_code(long service_code) {
		Service_code = service_code;
	}

	public void setService_name(String service_name) {
		Service_name = service_name;
	}

	public void setRule_id(String rule_id) {
		Rule_id = rule_id;
	}

	public void setRule_name(String rule_name) {
		Rule_name = rule_name;
	}

	public void setService_rule(String service_rule) {
		Service_rule = service_rule;
	}

	public void setMatching_level(int matching_level) {
		Matching_level = matching_level;
	}

	public void setRule_action(int rule_action) {
		Rule_action = rule_action;
	}

	public void setService_type(int service_type) {
		Service_type = service_type;
	}

	public void setKeyword1(String keyword1) {
		Keyword1 = keyword1;
	}

	public void setKeyword2(String keyword2) {
		Keyword2 = keyword2;
	}

	public void setKeyword3(String keyword3) {
		Keyword3 = keyword3;
	}

	public void setMatching_word(int matching_word) {
		Matching_word = matching_word;
	}

	public void setSet_time(long set_time) {
		Set_time = set_time;
	}

	public void setValidation_time(long validation_time) {
		Validation_time = validation_time;
	}

	public void setManual_pause_time(long manual_pause_time) {
		Manual_pause_time = manual_pause_time;
	}

	public void setFilter_method(int filter_method) {
		Filter_method = filter_method;
	}

	public void setFilter_argument(String filter_argument) {
		Filter_argument = filter_argument;
	}

	public void setNet_ending_ip(long net_ending_ip) {
		Net_ending_ip = net_ending_ip;
	}

	public void setNet_ending_mac(long net_ending_mac) {
		Net_ending_mac = net_ending_mac;
	}

	public void setDestination_ipv6(String destination_ipv6) {
		Destination_ipv6 = destination_ipv6;
	}

	public void setNet_ending_ipv6(String net_ending_ipv6) {
		Net_ending_ipv6 = net_ending_ipv6;
	}

	public void setMatching_time(long matching_time) {
		Matching_time = matching_time;
	}

	@Override
	public String toString() {
		return "AlertList [rowkey=" + rowkey + ", Jd=" + Jd + ", Wd=" + Wd + ", Country_name=" + Country_name
				+ ", Destination_ip=" + Destination_ip + ", Service_code=" + Service_code + ", Service_name="
				+ Service_name + ", Rule_id=" + Rule_id + ", Rule_name=" + Rule_name + ", Service_rule=" + Service_rule
				+ ", Matching_level=" + Matching_level + ", Rule_action=" + Rule_action + ", Service_type="
				+ Service_type + ", Keyword1=" + Keyword1 + ", Keyword2=" + Keyword2 + ", Keyword3=" + Keyword3
				+ ", Matching_word=" + Matching_word + ", Set_time=" + Set_time + ", Validation_time="
				+ Validation_time + ", Manual_pause_time=" + Manual_pause_time + ", Filter_method=" + Filter_method
				+ ", Filter_argument=" + Filter_argument + ", Net_ending_ip=" + Net_ending_ip + ", Net_ending_mac="
				+ Net_ending_mac + ", Destination_ipv6=" + Destination_ipv6 + ", Net_ending_ipv6=" + Net_ending_ipv6
				+ ", Matching_time=" + Matching_time + "]";
	}

}
