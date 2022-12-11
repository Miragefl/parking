package com.viscum.model;

import com.viscum.common.enums.CarPlateColorEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IdentifyResult {

	private String passagewayCode;
	private String carPhoto;
	private String platePhoto;
	private CarPlateColorEnum carPlateColor;
	private String carPlateNo;
	private LocalDateTime parkingTime;
}
