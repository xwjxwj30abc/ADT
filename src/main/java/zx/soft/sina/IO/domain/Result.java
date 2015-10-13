package zx.soft.sina.IO.domain;

public class Result {

	private int id;
	private long wid;
	private long username;
	private long repostscount;
	private long commentscount;
	private String text;
	private int createat;
	private long owid;
	private int ousername;
	private boolean favorited;
	private String geo;
	private float latitude;
	private float longitude;
	private String originalpic;
	private String source;
	private String visible;
	private int lasttime;

	public int getId() {
		return id;
	}

	public long getWid() {
		return wid;
	}

	public long getUsername() {
		return username;
	}

	public long getRepostscount() {
		return repostscount;
	}

	public long getCommentscount() {
		return commentscount;
	}

	public String getText() {
		return text;
	}

	public int getCreateat() {
		return createat;
	}

	public long getOwid() {
		return owid;
	}

	public int getOusername() {
		return ousername;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public String getGeo() {
		return geo;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public String getOriginalpic() {
		return originalpic;
	}

	public String getVisible() {
		return visible;
	}

	public int getLasttime() {
		return lasttime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setWid(long wid) {
		this.wid = wid;
	}

	public void setUsername(long username) {
		this.username = username;
	}

	public void setRepostscount(long repostscount) {
		this.repostscount = repostscount;
	}

	public void setCommentscount(long commentscount) {
		this.commentscount = commentscount;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setCreateat(int createat) {
		this.createat = createat;
	}

	public void setOwid(long owid) {
		this.owid = owid;
	}

	public void setOusername(int ousername) {
		this.ousername = ousername;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public void setOriginalpic(String originalpic) {
		this.originalpic = originalpic;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public void setLasttime(int lasttime) {
		this.lasttime = lasttime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "Result [id=" + id + ", wid=" + wid + ", username=" + username + ", repostscount=" + repostscount
				+ ", commentscount=" + commentscount + ", text=" + text + ", createat=" + createat + ", owid=" + owid
				+ ", ousername=" + ousername + ", favorited=" + favorited + ", geo=" + geo + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", originalpic=" + originalpic + ", source=" + source + ", visible="
				+ visible + ", lasttime=" + lasttime + "]";
	}

}
