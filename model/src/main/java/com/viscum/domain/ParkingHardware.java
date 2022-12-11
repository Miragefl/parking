package com.viscum.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 停车场硬件设备表
 *
 * @TableName parking_hardware
 */
@Getter
@Setter
@Builder
public class ParkingHardware implements Serializable {

	/**
	 * 主键
	 */
	@NotNull(message = "[主键]不能为空")
	@ApiModelProperty("主键")
	private Integer id;
	/**
	 * 硬件设备编号
	 */
	@NotBlank(message = "[硬件设备编号]不能为空")
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("硬件设备编号")
	@Length(max = 32, message = "编码长度不能超过32")
	private String hwCode;
	/**
	 * 硬件名称
	 */
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("硬件名称")
	@Length(max = 32, message = "编码长度不能超过32")
	private String hwName;
	/**
	 * 停车场编号
	 */
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("停车场编号")
	@Length(max = 32, message = "编码长度不能超过32")
	private String parkingCode;
	/**
	 * 通道编号
	 */
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("通道编号")
	@Length(max = 32, message = "编码长度不能超过32")
	private String gatewayCode;
	/**
	 * 通道类型 1：入口 2：出口
	 */
	@NotNull(message = "[通道类型 1：入口 2：出口]不能为空")
	@ApiModelProperty("通道类型 1：入口 2：出口")
	private Integer gatewayType;
	/**
	 * 设备类型 LED，CAMERA
	 */
	@NotNull(message = "[设备类型 LED，CAMERA]不能为空")
	@ApiModelProperty("设备类型 LED，CAMERA")
	private Integer hwType;
	/**
	 * 设备配置参数
	 */
	@NotNull(message = "[设备配置参数]不能为空")
	@ApiModelProperty("设备配置参数")
	private Object hwParam;
	/**
	 * 设备品牌
	 */
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("设备品牌")
	@Length(max = 32, message = "编码长度不能超过32")
	private String brand;
	/**
	 * 删除标识 0：未删除 1：已删除
	 */
	@NotNull(message = "[删除标识 0：未删除 1：已删除]不能为空")
	@ApiModelProperty("删除标识 0：未删除 1：已删除")
	private Integer isDel;
	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;
}