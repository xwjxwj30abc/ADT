package zx.soft.impala.adt.domain;

public class HotPlugLog {

	private String rowkey = "";//唯一行键
	private int id;//自增ID
	private int action;//热拔插动作：0-拔起 1-插入
	private String device = "";//网卡设备名称
	private long add_time;//添加时间
	private String note = "";//备注，扩展字段
	private long Service_code;//设备id
	private String Service_name = "";//设备名称

	public HotPlugLog() {

	}

	public String getRowkey() {
		return rowkey;
	}

	public int getId() {
		return id;
	}

	public int getAction() {
		return action;
	}

	public String getDevice() {
		return device;
	}

	public long getAdd_time() {
		return add_time;
	}

	public String getNote() {
		return note;
	}

	public long getService_code() {
		return Service_code;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setService_code(long service_code) {
		Service_code = service_code;
	}

	public String getService_name() {
		return Service_name;
	}

	public void setService_name(String service_name) {
		Service_name = service_name;
	}

	@Override
	public String toString() {
		return "HotPlugLog [rowkey=" + rowkey + ", id=" + id + ", action=" + action + ", device=" + device
				+ ", add_time=" + add_time + ", note=" + note + ", Service_code=" + Service_code + ", Service_name="
				+ Service_name + "]";
	}

}
