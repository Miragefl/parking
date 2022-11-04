package com.viscum.device.util;

import com.sun.jna.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DllPathUtil {
	static Logger logger = LoggerFactory.getLogger(DllPathUtil.class);
	public static final String SEPARATOR = "/";
	public static final String PREFIX = "libs";
	public static final String ROOTPATH = System.getProperty("user.dir") + SEPARATOR + PREFIX + SEPARATOR;

	public static String fullPath(String vendor,String libName) {
		String path = (ROOTPATH + Platform.RESOURCE_PREFIX + SEPARATOR + vendor + SEPARATOR + System.mapLibraryName(libName)).replace("\\","/");
		//部分window机器对‘/’无法自动识别和转换故此处做强制转换
		logger.info("dllpath:{}",path);
		return path;
	}
}
