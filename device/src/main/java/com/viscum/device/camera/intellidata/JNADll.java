package com.viscum.device.camera.intellidata;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.viscum.device.util.Helper;

public interface JNADll extends Library {

	String dllPath = Helper.fullPath("hxzx", "ice_ipcsdk");
	JNADll INSTANCE = Native.load(dllPath, JNADll.class);

	int ICE_IPCSDK_ControlAlarmOut(int hSDK, int u32Index);

	//基于linux api
	//连接camera
	int ICE_IPCSDK_Open(String ip, IntelliDataCameraCallBack.IPlateCallback pfOnPlate, int index);

	//软触发通过ip
	int ICE_IPCSDK_TriggerByIp(String pcIP);

}