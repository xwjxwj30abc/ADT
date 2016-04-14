package zx.soft.sina.IO.domain;

/**
 * 网络出口的公网地址记录
 * @author fgq
 *
 */
public class WanIpv4 {

	private String rowkey = "";
	private long Service_code;
	private int id;
	private String ipv4;
	private long add_time;

	public WanIpv4() {

	}

	public String getRowkey() {
		return rowkey;
	}

	public long getService_code() {
		return Service_code;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public void setService_code(long service_code) {
		Service_code = service_code;
	}

	public int getId() {
		return id;
	}

	public String getIpv4() {
		return ipv4;
	}

	public long getAdd_time() {
		return add_time;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}

	@Override
	public String toString() {
		return "WanIpv4 [id=" + id + ", ipv4=" + ipv4 + ", add_time=" + add_time + "]";
	}

}
