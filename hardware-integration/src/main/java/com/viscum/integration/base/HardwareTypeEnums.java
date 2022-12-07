package com.viscum.integration.base;

import com.viscum.integration.camera.intellidata.IntelliDataCamera;
import com.viscum.integration.camera.visionzenith.VisionZenithCamera;
import com.viscum.integration.led.minwang.MinWangLed;
import com.viscum.integration.led.ouguan.OuGuanLed;

public enum HardwareTypeEnums {

	/**
	 * 华夏智信相机
	 */
	CAMERA_INTELLI_DATA(IntelliDataCamera.class),
	/**
	 * 臻识相机
	 */
	CAMERA_VISION_ZENITH(VisionZenithCamera.class),
	/**
	 * 民望LED
	 */
	LED_MINWANG(MinWangLed.class),
	/**
	 * 欧冠LED
	 */
	LED_OUGUAN(OuGuanLed.class);

	HardwareTypeEnums(Class<? extends ParkHardware> clazz) {
		this.clazz = clazz;
	}

	private Class<? extends ParkHardware> clazz;

	public Class<? extends ParkHardware> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends ParkHardware> clazz) {
		this.clazz = clazz;
	}
}
