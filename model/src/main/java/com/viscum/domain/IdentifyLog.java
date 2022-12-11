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
* 通道记录表
* @TableName identify_log
*/
@Getter
@Setter
@Builder
public class IdentifyLog implements Serializable {

    /**
     * 主键
     */
    @NotNull(message = "[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
     * 识别日志唯一编号
     */
    @NotBlank(message = "[识别日志唯一编号]不能为空")
    @Size(max = 32, message = "编码长度不能超过32")
    @ApiModelProperty("识别日志唯一编号")
    @Length(max = 32, message = "编码长度不能超过32")
    private String logSerialNo;
    /**
     * 停车流水编号
     */
    @NotBlank(message = "[停车流水编号]不能为空")
    @Size(max = 32, message = "编码长度不能超过32")
    @ApiModelProperty("停车流水编号")
    @Length(max = 32, message = "编码长度不能超过32")
    private String parkingSerialNo;
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
    @Size(max = 64, message = "编码长度不能超过64")
    @ApiModelProperty("停车场名称")
    @Length(max = 64, message = "编码长度不能超过64")
    private String parkingName;
    /**
     * 停车场区域编号
     */
    @Size(max = 32, message = "编码长度不能超过32")
    @ApiModelProperty("停车场区域编号")
    @Length(max = 32, message = "编码长度不能超过32")
    private String regionCode;
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
     * 车牌号
     */
    @NotBlank(message = "[车牌号]不能为空")
    @Size(max = 10, message = "编码长度不能超过10")
    @ApiModelProperty("车牌号")
    @Length(max = 10, message = "编码长度不能超过10")
    private String carPlateNo;
    /**
     * 车牌颜色
     */
    @NotBlank(message = "[车牌颜色]不能为空")
    @Size(max = 16, message = "编码长度不能超过16")
    @ApiModelProperty("车牌颜色")
    @Length(max = 16, message = "编码长度不能超过16")
    private String carPlateColor;
    /**
     * 停车时间
     */
    @NotNull(message = "[停车时间]不能为空")
    @ApiModelProperty("停车时间")
    private Date parkingTime;
    /**
     * 记录类型 1:识别记录 2：流水记录
     */
    @NotNull(message = "[记录类型 1:识别记录 2：流水记录]不能为空")
    @ApiModelProperty("记录类型 1:识别记录 2：流水记录")
    private Integer logType;
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