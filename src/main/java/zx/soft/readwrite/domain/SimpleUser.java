package zx.soft.readwrite.domain;

public class SimpleUser {

	private long id;
	private String idstr;
	private String name;
	private long created_at;

	public long getId() {
		return id;
	}

	public String getIdstr() {
		return idstr;
	}

	public String getName() {
		return name;
	}

	public long getCreated_at() {
		return created_at;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}

	public SimpleUser() {

	}

	public SimpleUser(Builder builder) {
		this.id = builder.id;
		this.idstr = builder.idstr;
		this.name = builder.name;
		this.created_at = builder.create_at;
	}

	public static class Builder {
		private long id;
		private String idstr = "";
		private String name = "";
		private long create_at;

		public Builder(long id, long create_at) {
			this.id = id;
			this.create_at = create_at;
			this.idstr = Long.toString(id);
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		@Override
		public String toString() {
			return "Builder [id=" + id + ", idstr=" + idstr + ", name=" + name + ", create_at=" + create_at + "]";
		}

		public SimpleUser build() {
			return new SimpleUser(this);
		}

	}
}
