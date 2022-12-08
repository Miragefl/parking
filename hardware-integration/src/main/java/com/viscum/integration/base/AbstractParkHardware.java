package com.viscum.integration.base;

import com.viscum.model.Hardware;
import com.viscum.model.IdentifyResult;

public abstract class AbstractParkHardware implements ParkHardware {
	private Hardware hardware;

	public AbstractParkHardware() {
	}

	public AbstractParkHardware(Hardware hardware) {
		this.hardware = hardware;
	}

	/**
	 * 转发相机识别结果
	 * @param msg 车辆识别结果
	 * @param carPhoto 车辆识别图
	 * @param platePhoto 车牌识别图
	 */
	public void dispatcher(IdentifyResult msg,  byte[] carPhoto , byte[] platePhoto) {
		if (null != carPhoto) {
			// todo 保存车辆识别图
		}
		if (null != platePhoto) {
			// todo 保存车牌识别图
		}

		// todo 通过mq发送 或http直接上传云端

	}

	public Hardware getDevice() {
		return hardware;
	}

	public void setDevice(Hardware hardware) {
		this.hardware = hardware;
	}
}
