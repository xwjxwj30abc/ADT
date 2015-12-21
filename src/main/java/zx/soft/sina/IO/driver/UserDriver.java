package zx.soft.sina.IO.driver;

import org.apache.hadoop.util.ProgramDriver;

import zx.soft.sina.IO.server.UserServer;

public class UserDriver {

	public static void main(String[] args) {
		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {
			pgd.addClass("adtServer", UserServer.class, "ADT服务");
			pgd.driver(args);
			exitCode = 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		System.exit(exitCode);
	}
}
