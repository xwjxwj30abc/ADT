package zx.soft.adt.driver;

import org.apache.hadoop.util.ProgramDriver;

import zx.soft.adt.server.VPNServer;

public class VPNDriver {

	public static void main(String[] args) {
		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {
			pgd.addClass("VPNServer", VPNServer.class, "VPN查询服务");
			pgd.driver(args);
			exitCode = 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		System.exit(exitCode);
	}
}
