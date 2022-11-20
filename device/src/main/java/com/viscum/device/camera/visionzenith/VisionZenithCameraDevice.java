package com.viscum.device.camera.visionzenith;

import com.alibaba.fastjson2.JSON;
import com.sun.jna.Pointer;
import com.viscum.device.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 臻识相机设备操作
 * @author viscum
 */
@Slf4j
public class VisionZenithCameraDevice extends AbstractParkDevice implements CameraFunction, GateFunction, ShowFunction, PlayFunction {

	private Device device;

	private VisionZenithConfig config;

	private VisionZenithCallback callback = new VisionZenithCallback();

	private JNADll instance = JNADll.INSTANCE;

	private int handle;

	public VisionZenithCameraDevice(Device device) {
		this.device = device;
		config = JSON.parseObject(device.getDeviceParam(), VisionZenithConfig.class);
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

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public VisionZenithConfig getConfig() {
		return config;
	}

	public void setConfig(VisionZenithConfig config) {
		this.config = config;
	}
}
