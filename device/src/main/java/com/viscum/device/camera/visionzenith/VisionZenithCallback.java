package com.viscum.device.camera.visionzenith;

import ch.qos.logback.core.util.TimeUtil;
import com.sun.jna.Pointer;
import com.viscum.common.enums.CarPlateColorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class VisionZenithCallback implements JNADll.VZLPRC_TCP_PLATE_INFO_CALLBACK {

    Logger logger = LoggerFactory.getLogger(VisionZenithCallback.class);
    JNADll instance = JNADll.INSTANCE;

    public CarPlateColorEnum getColor(int type) {
        switch (type) {
            case 2:
                return CarPlateColorEnum.YELLOW;
            case 3:
                return CarPlateColorEnum.WHITE;
            case 4:
                return CarPlateColorEnum.BLACK;
            case 5:
                return CarPlateColorEnum.GREEN;
            default:
                return CarPlateColorEnum.BLUE;
        }
    }

    @Override
    public void invoke(int handle
            , Pointer pUserData
            , JNADll.TH_PlateResult_Pointer.ByReference pResult
            , int uNumPlates
            , int eResultType
            , JNADll.VZ_LPRC_IMAGE_INFO_Pointer.ByReference pImgFull
            , int nFullSize
            , JNADll.VZ_LPRC_IMAGE_INFO_Pointer.ByReference pImgPlateClip
            , int nClipSize) {
        String plateNo = "";
        try {
            byte[] plateNoBytes = new byte[trimIndex(pResult.license, new byte[]{(byte) 0xcc, (byte) 0x00})+1];
            System.arraycopy(pResult.license, 0, plateNoBytes, 0, plateNoBytes.length);
            plateNo = new String(plateNoBytes, "GB2312");
            logger.info("车牌plateNo:{}", plateNo);
            String plate = plateNo.replace(new String(new byte[]{(byte) 0xfe, 0x7f}), "").trim();
            plateNo = regular(plate,"[A-Z0-9]{1}");
            if ("".equals(plateNo)){
                plateNo = regular(plate,"");
            }
            logger.info("车牌plateNo:{}", plateNo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (StringUtils.isBlank(plateNo) || plateNo.contains("_无_")) {
            plateNo = CarPlateUtil.NO_PLATE;
        }
        logger.info("车牌bytes:{}", pResult.nColor);
        String color = getColor(pResult.nColor);
        logger.info("车牌bytes:{}", color);
        String bigImage = "";
        String smallImage = "";
        VisionZenithCameraManager device = VisionZenithCameraManager.handleDeviceMap.get(handle);
        int type = JNADll.VZ_LPRC_RESULT_TYPE.VZ_LPRC_RESULT_REALTIME.ordinal();
        if (device != null && type != eResultType) {
            LocalDateTime time = LocalDateTime.now();
            String imagePath = null;
            imagePath = device.getImageFullPath(plateNo, color, time);
            if (pImgFull != null && pImgFull.uWidth != 0 && pImgFull.uHeight != 0) {
                bigImage = imagePath + "-b.jpg";
                try {
                    Pointer pBigImagePath = StringUtil.convertToPointer(bigImage, "UTF-8");
                    int res = instance.VzLPRTcp_ImageSaveToJpeg(pImgFull, nFullSize, pBigImagePath, 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (plateNo != CarPlateUtil.NO_PLATE && pImgPlateClip != null && pImgPlateClip.uWidth != 0 && pImgPlateClip.uHeight != 0) {
                smallImage = imagePath + "-s.jpg";
                try {
                    Pointer pSmallImagePath = StringUtil.convertToPointer(smallImage, "UTF-8");
                    int res = instance.VzLPRTcp_ImageSaveToJpeg(pImgPlateClip, nClipSize, pSmallImagePath, 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            GateLog msg = new GateLog();
            msg.setParkingTime(TimeUtil.localDateTimeToStr(time,TimeUtil.FORMAT_DATETIME_FULL));
            msg.setPassagewayCode(device.getGateNo());
            msg.setCarPlateNo(plateNo);
            msg.setCarPlateColor(color);
            int carBodySize = CarPlateUtil.getCarBodySizeByColor(color);
            msg.setCarBodySize(carBodySize);
            msg.setCarPhoto(bigImage);
            msg.setPlatePhoto(smallImage);
            msg.setCreateTime(time);
            device.dispatcher(msg, null, null, plateNo, color, carBodySize, time);
        }
    }

    public int trimIndex(byte[] data, byte[] tmp) {
        for (int i = data.length - 1; i >= 0; i--) {
            if (data[i] != tmp[0] && data[i] != tmp[1]) {
                return i;
            }
        }
        return 0;
    }


    /**
     *
     *
     * @param data
     * @return
     */
    private static String regular(String data,String num){
        String plateNum = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Za-z0-9]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}";
        Pattern pa = Pattern.compile(plateNum+num);
        Matcher ma = pa.matcher(data);
        StringBuilder sb = new StringBuilder();
        while (ma.find()){
            sb.append(ma.group());
        }
        return sb.toString();

    }
}
