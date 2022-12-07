package com.viscum.integration.camera.intellidata;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 华夏智信相机管理
 *
 * @author viscum
 */
public class IntelliDataCameraManager {
	public static Map<String, IntelliDataCamera> ipDeviceMap = new ConcurrentHashMap<>();

}
