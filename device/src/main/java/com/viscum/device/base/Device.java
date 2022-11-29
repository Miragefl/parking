package com.viscum.device.base;

import com.viscum.common.enums.DeviceTypeEnum;
import com.viscum.common.enums.PassagewayTypeEnum;
import lombok.Data;

/**
 * 设备类
 * @author fenglei
 */
@Data
public class Device {
	private int id;
	/**
	 * 设备编号
	 */
	private String deviceNo;
	/**
	 * 停车场编号
	 */
	private String parkingCode;
	/**
	 * 停车场名称
	 */
	private String parkingName;
	/**
	 * 通道编号
	 */
	private String passagewayCode;
	/**
	 * 通道名称
	 */
	private String passagewayName;
	/**
	 * 通道类型
	 */
	private PassagewayTypeEnum passagewayType;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 设备类型
	 */
	private DeviceTypeEnum deviceType;
	/**
	 * 设备参数
	 */
	private String deviceParam;

	/**
	 * 设备品牌
	 */
	private String brand;

}
