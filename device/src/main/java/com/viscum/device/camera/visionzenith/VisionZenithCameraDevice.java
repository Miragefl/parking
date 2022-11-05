package com.viscum.device.camera.visionzenith;

import com.alibaba.fastjson2.JSON;
import com.sun.jna.Pointer;
import com.viscum.device.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class VisionZenithCameraDevice implements CameraFunction, GateFunction, ShowFunction, PlayFunction {

	private Device device;

	private VisionZenithConfig config;

	private VisionZenithCallback callback;

	private JNADll instance = JNADll.INSTANCE;

	private int handle;

	public VisionZenithCameraDevice(Device device) {
		this.device = device;
		config = JSON.parseObject(device.getDeviceParam(), VisionZenithConfig.class);
		VisionZenithCameraManager.init();
		handle = instance.VzLPRTcp_Open(config.getIp(), config.getPort(), config.getName(), config.getPassword());
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
	public int play(String playText) {
		throw new UnsupportedOperationException("臻识相机不支持");
	}

	@Override
	public int show(List<LedText> list) {
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
}
