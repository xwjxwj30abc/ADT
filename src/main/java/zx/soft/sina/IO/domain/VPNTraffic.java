package zx.soft.sina.IO.domain;

public class VPNTraffic {

	private String rowkey = "";
	private int id;
	private long Service_code;
	private String Service_name = "";
	private String ipv4 = "";
	private long begin_time;
	private long end_time;
	private long traffic;

	public VPNTraffic() {

	}

	public String getService_name() {
		return Service_name;
	}

	public void setService_name(String service_name) {
		Service_name = service_name;
	}

	public long getService_code() {
		return Service_code;
	}

	public void setService_code(long service_code) {
		Service_code = service_code;
	}

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public int getId() {
		return id;
	}

	public String getIpv4() {
		return ipv4;
	}

	public long getBegin_time() {
		return begin_time;
	}

	public long getEnd_time() {
		return end_time;
	}

	public long getTraffic() {
		return traffic;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public void setBegin_time(long begin_time) {
		this.begin_time = begin_time;
	}

	public void setEnd_time(long end_time) {
		this.end_time = end_time;
	}

	public void setTraffic(long traffic) {
		this.traffic = traffic;
	}

}
