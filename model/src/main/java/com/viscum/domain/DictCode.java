package com.viscum.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * 系统字典值
 *
 * @TableName dict_code
 */
@Getter
@Setter
@Builder
public class DictCode implements Serializable {

	/**
	 * 字典KEY
	 */
	@NotBlank(message = "[字典KEY]不能为空")
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("字典KEY")
	@Length(max = 32, message = "编码长度不能超过32")
	private String codeKey;
	/**
	 * 字典值名称
	 */
	@NotBlank(message = "[字典值名称]不能为空")
	@Size(max = 32, message = "编码长度不能超过32")
	@ApiModelProperty("字典值名称")
	@Length(max = 32, message = "编码长度不能超过32")
	private String codeName;
	/**
	 * 字典值
	 */
	@NotBlank(message = "[字典值]不能为空")
	@Size(max = 64, message = "编码长度不能超过64")
	@ApiModelProperty("字典值")
	@Length(max = 64, message = "编码长度不能超过64")
	private String codeValue;
	/**
	 * 有效标识 0: 有效 1：无效
	 */
	@NotNull(message = "[有效标识 0: 有效 1：无效]不能为空")
	@ApiModelProperty("有效标识 0: 有效 1：无效")
	private Integer isValid;
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
