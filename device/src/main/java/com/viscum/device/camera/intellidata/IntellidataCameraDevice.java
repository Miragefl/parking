package com.viscum.device.camera.intellidata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson2.JSON;
import com.viscum.device.AbstractParkDevice;
import com.viscum.device.CameraFunction;
import com.viscum.device.Device;
import com.viscum.device.GateFunction;
import com.viscum.device.LedText;
import com.viscum.device.PlayFunction;
import com.viscum.device.ShowFunction;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class IntellidataCameraDevice extends AbstractParkDevice
		implements CameraFunction, GateFunction, ShowFunction, PlayFunction {

	public static final Logger LOGGER = LoggerFactory.getLogger(IntellidataCameraDevice.class);

	private IntelliDataCameraConfig config;
	private Device device;
	private int mHandler;
	private IntelliDataCallBack.IPlateCallback plateCallbackLinux = new IntelliDataCallBack.PlateCallback();

	public IntellidataCameraDevice(Device device) {
		this.device = device;
		config = JSON.parseObject(device.getDeviceParam(), IntelliDataCameraConfig.class);
		String ip = config.getIp();
		IntelliDataCameraManager.init(device.getDeviceNo(), this);
		IntelliDataCameraManager.ipDeviceMap.put(ip, this);
		LOGGER.info("华夏相机设备Id {}", device.getId());
		mHandler = JNADll.INSTANCE.ICE_IPCSDK_Open(ip, plateCallbackLinux, Integer.parseInt(data.getId()));
	}

	@Override
	public int play(String playText) {
		throw new UnsupportedOperationException("华夏致信相机不支持");
	}

	@Override
	public int show(List<LedText> list) {
		throw new UnsupportedOperationException("华夏致信相机不支持");
	}

	@Override
	public int open() {
		int handler = mHandler;
		try {

			int success = JNADll.INSTANCE.ICE_IPCSDK_ControlAlarmOut(handler, 0);
			if (1 == success) {
				handler = 1;
			} else {
				handler = 0;
			}
		} catch (Exception e) {
			return -1;
		}
		return handler;
	}

	@Override
	public int close() {
		int handler = mHandler;
		try {

			int success = JNADll.INSTANCE.ICE_IPCSDK_ControlAlarmOut(handler, 1);
			if (1 == success) {
				handler = 1;
			} else {
				handler = 0;
			}
		} catch (Exception e) {
			return -1;
		}
		return handler;
	}

	@Override
	public int lock() {
		throw new UnsupportedOperationException("华夏致信相机不支持");
	}

	@Override
	public int unlock() {
		throw new UnsupportedOperationException("华夏致信相机不支持");
	}

	@Override
	public int softTrigger() {
		int handler = mHandler;
		try {
			int success = JNADll.INSTANCE.ICE_IPCSDK_TriggerByIp(config.getIp());
			if (1 == success) {
				handler = 1;
			} else {
				handler = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return handler;
	}

	public IntelliDataCameraConfig getConfig() {
		return config;
	}

	public void setConfig(IntelliDataCameraConfig config) {
		this.config = config;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
