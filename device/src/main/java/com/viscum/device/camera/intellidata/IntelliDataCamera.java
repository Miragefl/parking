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

import java.util.List;

/**
 * 华夏智信相机操作
 *
 * @author viscum
 */
public class IntelliDataCamera extends AbstractParkDevice
		implements CameraFunction, GateFunction, ShowFunction, PlayFunction {

	public static final Logger LOGGER = LoggerFactory.getLogger(IntelliDataCamera.class);

	private IntelliDataCameraConfig config;
	private Device device;
	private int mHandler;
	private IntelliDataCameraCallBack.IPlateCallback plateCallbackLinux = new IntelliDataCameraCallBack.PlateCallback();

	public IntelliDataCamera(Device device) {
		this.device = device;
		config = JSON.parseObject(device.getDeviceParam(), IntelliDataCameraConfig.class);
		String ip = config.getIp();
		IntelliDataCameraManager.ipDeviceMap.put(ip, this);
		LOGGER.info("华夏相机设备Id {}", device.getDeviceNo());
		mHandler = JNADll.INSTANCE.ICE_IPCSDK_Open(ip, plateCallbackLinux, device.getId());
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
		try {
			int success = JNADll.INSTANCE.ICE_IPCSDK_ControlAlarmOut(mHandler, 0);
			if (1 == success) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int close() {
		try {
			int success = JNADll.INSTANCE.ICE_IPCSDK_ControlAlarmOut(mHandler, 1);
			if (1 == success) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return -1;
		}
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
		try {
			int success = JNADll.INSTANCE.ICE_IPCSDK_TriggerByIp(config.getIp());
			if (1 == success) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return -1;
		}
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
