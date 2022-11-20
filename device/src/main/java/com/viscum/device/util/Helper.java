package com.viscum.device.util;

import com.sun.jna.Memory;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.viscum.common.CommonConstants;
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
	public static final String ROOT_PATH = "libs" + File.separator;

	public static final String PARK_CODE = SpringEnvironmentUtil.getProperty(CommonConstants.CUSTOM_CONFIG + "parkCode").toString();

	public static final String imagePath = SpringEnvironmentUtil.getPropertyOrDefault(CommonConstants.CUSTOM_CONFIG + "img.savePath", "~/parkSys/imageStore");

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
		LocalDateTime now = LocalDateTime.now();
		String separator = File.separator;
		String dir = imagePath + separator + now.getYear() + separator + now.getMonthValue() + separator + now.getDayOfMonth() + separator + parkCode;
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


	public static String fullPath(String vendor, String libName) {
		String path = (ROOT_PATH + Platform.RESOURCE_PREFIX + File.separator + vendor + File.separator + System.mapLibraryName(libName)).replace("\\", "/");
		log.info("dllpath:{}", path);
		return path;
	}
}
