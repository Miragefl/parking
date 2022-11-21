package com.viscum.device.camera.intellidata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * package: com.ydcloud.os.dev.camera.hxzx
 * author : CaineZhu
 * mail   : CaineZhu@me.com
 * date   : 2018/2/5 9:19
 * desc   : MWCameraManager
 */
public class IntelliDataCameraManager {
    static Logger logger = LoggerFactory.getLogger(IntelliDataCameraManager.class);

    private static volatile boolean hasInit = false;
    public static Map<String, IntellidataCameraDevice> ipDeviceMap = new ConcurrentHashMap<>();

    public static void init(String did, IntellidataCameraDevice device) {
//        didDeviceMap.put(did, device);
        if (!hasInit) {
            if(PlatformUtil.isWindow) {
                JNADll.INSTANCE.ICE_IPCSDK_Init();
            }
            hasInit = true;
            logger.info("华夏智信摄像头初始化");
        }
    }
}
