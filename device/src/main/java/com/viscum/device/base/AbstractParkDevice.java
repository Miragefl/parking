package com.viscum.device.base;

import com.viscum.model.IdentifyResult;

public abstract class AbstractParkDevice implements ParkDevice {
	private Device device;

	public AbstractParkDevice() {
	}

	public AbstractParkDevice(Device device) {
		this.device = device;
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

}
