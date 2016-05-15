package zx.soft.adt.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	private static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	static {
		mapper.setDateFormat(dateFormat);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	public static ObjectMapper getObjectMapper() {
		return mapper;
	}

	public static String toJson(Object object) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	public static String toJsonWithoutPretty(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	public static <T> T getObject(String jsonStr, Class<T> t) {
		ObjectReader objectReader = mapper.reader(t);
		try {
			return objectReader.readValue(jsonStr);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 *  测试函数
	 */
	public static void main(String[] args) throws ParseException {

		//		System.out.println(dateFormat.format(new Date()));
		// Wed Oct 23 16:58:17 +0800 2013
		// Wed Oct 23 16:58:17 CST 2013
		Date parse = dateFormat.parse("Thu Apr 10 11:40:56 CST 2014");
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parse));
	}

}
