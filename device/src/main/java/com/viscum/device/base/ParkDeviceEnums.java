package com.viscum.device.base;

import com.viscum.device.camera.intellidata.IntelliDataCamera;
import com.viscum.device.camera.visionzenith.VisionZenithCamera;
import com.viscum.device.led.minwang.MinWangLed;
import com.viscum.device.led.ouguan.OuGuanLed;

public enum ParkDeviceEnums {

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

	ParkDeviceEnums(Class<? extends ParkDevice> clazz) {
		this.clazz = clazz;
	}

	private Class<? extends ParkDevice> clazz;

	public Class<? extends ParkDevice> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends ParkDevice> clazz) {
		this.clazz = clazz;
	}
}
