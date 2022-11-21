package com.ydcloud.os.device.camera.hxzx;

import com.ydcloud.os.device.util.DLLPathUtil;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface JNADll extends Library {

	String strdir = DLLPathUtil.fullPath("hxzx","ice_ipcsdk");
    JNADll INSTANCE = (JNADll) Native.load(strdir, JNADll.class);

    //基于windows api
    public void ICE_IPCSDK_Init();
    public int ICE_IPCSDK_OpenPreview(String ip, byte tcp, byte stream,
                                      int hWnd, HXZXCameraWin.IPlateCallback callback, int param);
    public void ICE_IPCSDK_Close(int hSDK);
    public void ICE_IPCSDK_EnableUTF8(int hSDK, int enable);
    public int ICE_IPCSDK_TriggerExt(int hSDK);
    public int ICE_IPCSDK_OpenGate(int hSDK);
    public int ICE_IPCSDK_Fini();

    int ICE_IPCSDK_ControlAlarmOut(int hSDK, int u32Index);
    //基于linux api
    //连接camera
    int ICE_IPCSDK_Open(String ip, IntelliDataCallBack.IPlateCallback pfOnPlate, int index);
    //软触发通过ip
    int ICE_IPCSDK_TriggerByIp(String pcIP);
    //软触发通过handle
    int ICE_IPCSDK_Trigger(int hSDK, String number, String color, Pointer pdata, long size, Pointer imageSize);
//    int ICE_IPCSDK_OpenGate(int hSDK);


    int ICE_IPCSDK_TransSerialPort(int hSDK, byte[] nData, long nLen);
//    int ICE_IPCSDK_TransSerialPort(int hSDK,  nData, long nLen);
}
