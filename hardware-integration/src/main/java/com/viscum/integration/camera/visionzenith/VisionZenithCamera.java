package com.viscum.integration.camera.visionzenith;

import com.alibaba.fastjson2.JSON;
import com.sun.jna.Pointer;
import com.viscum.integration.base.*;
import com.viscum.model.Hardware;
import lombok.extern.slf4j.Slf4j;

/**
 * 臻识相机设备操作
 * @author viscum
 */
@Slf4j
public class VisionZenithCamera extends AbstractParkHardware implements CameraFunction, GateFunction {

	// private Device device;

	private VisionZenithCameraConfig config;

	private VisionZenithCameraCallback callback = new VisionZenithCameraCallback();

	private final JNADll instance = JNADll.INSTANCE;

	private final int handle;

	public VisionZenithCamera(Hardware hardware) {
		super(hardware);
		// this.device = device;
		config = JSON.parseObject(hardware.getHwParam(), VisionZenithCameraConfig.class);
		VisionZenithCameraManager.init();
		handle = instance.VzLPRTcp_Open(config.getIp(), config.getPort(), config.getName(), config.getPassword());
		callback.setDevice(this);
		int result = instance.VzLPRTcp_SetPlateInfoCallBack(handle, callback, Pointer.NULL, 1);
		log.info("设置相机回调结果 :{}", result);
		int s = JNADll.INSTANCE.VzLPRTcp_ForceTrigger(handle);
		log.info(">>>>>>>>>:{}", s);
	}


	@Override
	public int open() {
		int result = 0;
		try {
			int res = instance.VzLPRTcp_SetIOOutputAuto(handle, 0, 500);
			if (res == 0) {
				result = 1;
			}
		} catch (Exception e) {
			return -1;
		}
		return result;
	}

	@Override
	public int close() {
		throw new UnsupportedOperationException("臻识相机不支持");
	}

	@Override
	public int lock() {
		throw new UnsupportedOperationException("臻识相机不支持");
	}

	@Override
	public int unlock() {
		throw new UnsupportedOperationException("臻识相机不支持");
	}

	@Override
	public int softTrigger() {
		int result = 0;
		try {
			int res = instance.VzLPRTcp_ForceTrigger(handle);
			if (res == 0) {
				result = 1;
			}
		} catch (Exception e) {
			return -1;
		}
		return result;
	}
	//
	// @Override
	// public Device getDevice() {
	// 	return device;
	// }
	//
	// @Override
	// public void setDevice(Device device) {
	// 	this.device = device;
	// }

	public VisionZenithCameraConfig getConfig() {
		return config;
	}

	public void setConfig(VisionZenithCameraConfig config) {
		this.config = config;
	}
}
