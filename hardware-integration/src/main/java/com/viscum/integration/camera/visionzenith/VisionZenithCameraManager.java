package com.viscum.integration.camera.visionzenith;

import lombok.extern.slf4j.Slf4j;

/**
 * 臻识相机设备管理类
 */
@Slf4j
public class VisionZenithCameraManager {

	private static boolean hasInit = false;

	public static void init() {
		if (hasInit) {
			return;
		}
		JNADll.INSTANCE.VzLPRTcp_Setup();
		hasInit = true;
		log.info("臻识相机sdk初始化成功");
	}
}
