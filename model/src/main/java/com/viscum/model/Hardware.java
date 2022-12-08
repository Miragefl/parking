package com.viscum.model;

import com.viscum.common.enums.HwTypeEnum;
import com.viscum.common.enums.GatewayTypeEnum;
import lombok.Data;

/**
 * 设备类
 * @author fenglei
 */
@Data
public class Hardware {
	private int id;
	/**
	 * 设备编号
	 */
	private String hwCode;
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
	private String gatewayCode;
	/**
	 * 通道名称
	 */
	private String gatewayName;
	/**
	 * 通道类型
	 */
	private GatewayTypeEnum gatewayType;
	/**
	 * 设备名称
	 */
	private String hwName;
	/**
	 * 设备类型
	 */
	private HwTypeEnum hwType;
	/**
	 * 设备参数
	 */
	private String hwParam;

	/**
	 * 设备品牌
	 */
	private String brand;

}
