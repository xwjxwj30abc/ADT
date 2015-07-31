package zx.soft.weibo.mapred.readwrite;

import java.io.Closeable;

import zx.soft.readwrite.domain.SimpleUser;

public interface SinaWriter extends Closeable {

	public void write(String key, SimpleUser user);

}
