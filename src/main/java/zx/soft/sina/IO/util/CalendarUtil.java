package zx.soft.sina.IO.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	public static long getZeroTime(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(time * 1000));
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTimeInMillis() / 1000;
	}

}
