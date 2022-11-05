package com.viscum.device.camera.visionzenith;

import lombok.extern.slf4j.Slf4j;

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
