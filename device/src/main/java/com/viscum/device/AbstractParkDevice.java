package com.viscum.device;

import com.viscum.model.IdentifyResult;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractParkDevice {


	/**
	 * 转发相机识别结果
	 * @param msg 车辆识别结果
	 * @param carPhotoBase64 车辆识别图
	 * @param platePhotoBase64 车牌识别图
	 */
	public void dispatcher(IdentifyResult msg, String carPhotoBase64 , String platePhotoBase64) {

		if (StringUtils.isNotBlank(carPhotoBase64)) {
			// todo 保存车辆识别图
		}
		if (StringUtils.isNotBlank(platePhotoBase64)) {
			// todo 保存车牌识别图
		}

		// todo 通过mq发送 或http直接上传云端

	}
}
