package zx.soft.sina.IO.mapred;

import java.io.Closeable;

import zx.soft.sina.IO.domain.SimpleUser;

public interface SinaWriter extends Closeable {

	public void write(String key, SimpleUser user);

}
