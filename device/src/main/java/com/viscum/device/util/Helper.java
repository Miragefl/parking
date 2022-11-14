package com.viscum.device.util;

import com.sun.jna.Memory;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.viscum.common.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Helper {

	private static final Logger log = LoggerFactory.getLogger(Helper.class);

	public static boolean isWindow = Platform.isWindows() || Platform.isWindowsCE();

	public static final String PARK_CODE = SpringEnvironmentUtil.getProperty("ydcloudos.parkCode").toString();

	public static String getImageFullPath(String plateNo, String color, LocalDateTime time) {
		String storePath = getImageStoreRootPath(PARK_CODE) + File.separator + PARK_CODE
				+ "-" + color + "-" + plateNo + "-" + DateTimeUtil.convent(time);
		return storePath.replace("\\", "/");
	}

	/**
	 * 获取图片存放路径
	 *
	 * @return String 图片存放根路径
	 */
	public static String getImageStoreRootPath(String parkCode) {
		String separator = File.separator;
		Calendar calendar = Calendar.getInstance();
		String month = (calendar.get(Calendar.MONTH) + 1 < 10) ? "0" + (calendar.get(Calendar.MONTH) + 1) : String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String day = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String dir = (isWindow ? SpringEnvironmentUtil.getProperty("ydcloudos.img.rootPathWIN") : SpringEnvironmentUtil.getProperty("ydcloudos.img.rootPathLINUX")) + separator + calendar.get(Calendar.YEAR) + separator + month + separator + day + separator + parkCode;
		File dirs = new File(dir);
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		return dirs.getPath();
	}

	public static Pointer convertToPointer(String text, String encoding) throws UnsupportedEncodingException {
		int length = getGBKLength(text, encoding);
		Memory pointer = new Memory(length + 1);
		pointer.setString(0, text, encoding);
		return pointer;
	}

	public static int getGBKLength(String s, String encoding) throws UnsupportedEncodingException {
		int len = 0;
		if (s == null) {
			return len;
		}
		try {
			len = s.getBytes(encoding).length;
		} catch (UnsupportedEncodingException e) {
			log.error("字符转换异常 {}", s, e);
			throw e;
		}

		return len;
	}
}
