package com.viscum.integration.camera.intellidata;

import com.viscum.integration.base.Hardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson2.JSON;
import com.viscum.integration.base.AbstractParkHardware;
import com.viscum.integration.base.CameraFunction;
import com.viscum.integration.base.GateFunction;

/**
 * 华夏智信相机操作
 *
 * @author viscum
 */
public class IntelliDataCamera extends AbstractParkHardware
		implements CameraFunction, GateFunction {

	public static final Logger LOGGER = LoggerFactory.getLogger(IntelliDataCamera.class);

	private IntelliDataCameraConfig config;
	// private Device device;
	private final int mHandler;
	private IntelliDataCameraCallBack.IPlateCallback plateCallbackLinux = new IntelliDataCameraCallBack.PlateCallback();

	public IntelliDataCamera(Hardware hardware) {
		super(hardware);
		// this.device = device;
		config = JSON.parseObject(hardware.getDeviceParam(), IntelliDataCameraConfig.class);
		String ip = config.getIp();
		IntelliDataCameraManager.ipDeviceMap.put(ip, this);
		LOGGER.info("华夏相机设备Id {}", hardware.getDeviceNo());
		mHandler = JNADll.INSTANCE.ICE_IPCSDK_Open(ip, plateCallbackLinux, hardware.getId());
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

}
