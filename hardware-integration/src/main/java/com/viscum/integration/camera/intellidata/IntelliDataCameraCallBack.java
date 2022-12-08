package com.viscum.integration.camera.intellidata;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.viscum.common.CommonConstants;
import com.viscum.common.enums.CarPlateColorEnum;
import com.viscum.model.IdentifyResult;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 华夏智信相机回调
 *
 * @author viscum
 */
@Slf4j
public class IntelliDataCameraCallBack {

	public static final List<String> NO_CAR_PLATE_LIST = Arrays.asList("无牌车", "");

	public interface IPlateCallback extends Callback {
		public void ICE_IPCSDK_Plate(int param, String strIP, Pointer strNumber, Pointer strColor,
		                             Pointer pPicData, int nLen, Pointer pCloseupData, int nCloseupLen,
		                             int nPlatePosLeft, int nPlatePosTop, int nPlatePosRight, int nPlatePosBottom,
		                             float fPlateConfidence, int nVehicleColor, int nPlateType, int nVehicleDir,
		                             int nAlarmType, int nSpeed, int nCapTime, int nVehicleType, int nResultHigh,
		                             int nResultLow);
	}

	private static CarPlateColorEnum toColor(String color) {
		log.info("收到相机颜色：{}", color);
		switch (color) {
			case "黄色":
			case "黄绿色":
				return CarPlateColorEnum.YELLOW;
			case "绿色":
				return CarPlateColorEnum.GREEN;
			case "白色":
				return CarPlateColorEnum.WHITE;
			case "黑色":
				return CarPlateColorEnum.BLACK;
			default:
				return CarPlateColorEnum.BLUE;
		}
	}

	public static class PlateCallback implements IPlateCallback {
		@Override
		public void ICE_IPCSDK_Plate(int param, String strIP, Pointer strNumber, Pointer strColor,
		                             Pointer pPicData, int nLen, Pointer pCloseupData, int nCloseupLen,
		                             int nPlatePosLeft, int nPlatePosTop, int nPlatePosRight, int nPlatePosBottom,
		                             float fPlateConfidence, int nVehicleColor, int nPlateType, int nVehicleDir,
		                             int nAlarmType, int nSpeed, int nCapTime, int nVehicleType, int nResultHigh,
		                             int nResultLow) {

			IntelliDataCamera device = IntelliDataCameraManager.ipDeviceMap.get(strIP);
			if (null == device) {
				return;
			}
			log.info("华夏智信fPlateConfidence: {}", fPlateConfidence);
			String _carPlateNo = strNumber.getString(0, "gbk");
			String carPlateNo = _carPlateNo;
			if (NO_CAR_PLATE_LIST.stream().anyMatch(e -> StringUtils.equals(e, _carPlateNo))) {
				carPlateNo = CommonConstants.NULL_CAR_PLATE_NO;
			}
			int confidence = Math.round(fPlateConfidence);
			CarPlateColorEnum carPlateColor = toColor(strColor.getString(0, "gbk"));
			byte[] carPhoto = null;
			if (nLen > 0) {
				carPhoto = new byte[nLen];
				pPicData.read(0, carPhoto, 0, nLen);
			}
			byte[] platePhoto = null;
			if (nCloseupLen > 0) {
				platePhoto = new byte[nCloseupLen];
				pCloseupData.read(0, platePhoto, 0, nCloseupLen);
			}
			IdentifyResult msg = new IdentifyResult();
			msg.setParkingTime(LocalDateTime.now());
			msg.setPassagewayCode(device.getDevice().getGatewayCode());
			msg.setCarPlateNo(carPlateNo);
			msg.setCarPlateColor(carPlateColor);
			device.dispatcher(msg, carPhoto, platePhoto);
		}

	}

}
