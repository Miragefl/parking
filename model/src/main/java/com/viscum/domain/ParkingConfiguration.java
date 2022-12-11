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
 * 停车场配置表
 *
 * @TableName parking_configuration
 */
@Getter
@Setter
@Builder
public class ParkingConfiguration implements Serializable {

	/**
	 * 主键
	 */
	@NotNull(message = "[主键]不能为空")
	@ApiModelProperty("主键")
	private Integer id;
	/**
	 * 停车场编号
	 */
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("停车场编号")
	@Length(max = 32, message = "编码长度不能超过32")
	private String parkingCode;
	/**
	 * 停车场区域编号
	 */
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("停车场区域编号")
	@Length(max = 32, message = "编码长度不能超过32")
	private String regionCode;
	/**
	 * 配置项key
	 */
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("配置项key")
	@Length(max = 32, message = "编码长度不能超过32")
	private String configKey;
	/**
	 * 配置项名称
	 */
	@Size(max = 64, message = "编码长度不能超过64")
	@ApiModelProperty("配置项名称")
	@Length(max = 64, message = "编码长度不能超过64")
	private String configName;
	/**
	 * 具体配置项
	 */
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("具体配置项")
	@Length(max = 32, message = "编码长度不能超过32")
	private String configValue;
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