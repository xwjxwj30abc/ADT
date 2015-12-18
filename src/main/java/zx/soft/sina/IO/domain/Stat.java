package zx.soft.sina.IO.domain;

public class Stat {

	private String Country_name = "";
	private double Jd;
	private double Wd;
	private long Count;

	public String getCountry_name() {
		return Country_name;
	}

	public double getJd() {
		return Jd;
	}

	public double getWd() {
		return Wd;
	}

	public long getCount() {
		return Count;
	}

	public void setCountry_name(String country_name) {
		Country_name = country_name;
	}

	public void setJd(double jd) {
		Jd = jd;
	}

	public void setWd(double wd) {
		Wd = wd;
	}

	public void setCount(long count) {
		Count = count;
	}

	@Override
	public String toString() {
		return "Stat [Country_name=" + Country_name + ", Jd=" + Jd + ", Wd=" + Wd + ", Count=" + Count + "]";
	}

}
