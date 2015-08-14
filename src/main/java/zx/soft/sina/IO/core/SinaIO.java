package zx.soft.sina.IO.core;

import java.io.Closeable;

public interface SinaIO extends Closeable {

	public <T> void write(String key, T value);

}
