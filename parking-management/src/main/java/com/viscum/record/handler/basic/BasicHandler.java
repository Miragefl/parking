package com.viscum.record.handler.basic;

import com.viscum.model.IdentifyResult;
import com.viscum.record.handler.AbstractIdentifyHandler;

import java.time.LocalDateTime;

/**
 *
 */
public class BasicHandler extends AbstractIdentifyHandler {

	@Override
	public boolean execute(IdentifyResult identifyResult) {
		return false;
	}

	void getParkingInfo(String gatewayCode) {

	}

	void getCarType(String carPlateNo, String carPlateColor, String parkingCode, LocalDateTime parkingTime) {

	}

	void saveRecord() {

	}
}
