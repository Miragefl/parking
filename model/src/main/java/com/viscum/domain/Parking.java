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
 * 停车场基本信息表
 *
 * @TableName parking
 */
@Getter
@Setter
@Builder
public class Parking implements Serializable {

	/**
	 * 主键
	 */
	@NotNull(message = "[主键]不能为空")
	@ApiModelProperty("主键")
	private Integer id;
	/**
	 * 停车场编号
	 */
	@NotBlank(message = "[停车场编号]不能为空")
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("停车场编号")
	@Length(max = 32, message = "编码长度不能超过32")
	private String parkingCode;
	/**
	 * 停车场名称
	 */
	@NotBlank(message = "[停车场名称]不能为空")
	@Size(max = 64, message = "编码长度不能超过64")
	@ApiModelProperty("停车场名称")
	@Length(max = 64, message = "编码长度不能超过64")
	private String parkingName;
	/**
	 * 停车场泊位数
	 */
	@NotNull(message = "[停车场泊位数]不能为空")
	@ApiModelProperty("停车场泊位数")
	private Integer parkingLotNum;
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
