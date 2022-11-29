package com.viscum.device;

import com.viscum.device.base.Device;
import com.viscum.device.base.ParkDevice;
import com.viscum.device.base.ParkDeviceEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class DefaultDeviceContext implements DeviceContext {

	public static Map<String, ParkDevice> deviceContextHolder = new ConcurrentHashMap<>();

	public static Map<String, List<Device>> deviceGateContextHolder = new ConcurrentHashMap<>();

	@Override
	public void initDevice(DeviceLoader loader) {
		List<Device> deviceList = loader.load();
		deviceList.forEach(this::create);
	}

	@Override
	public ParkDevice getDevice(String deviceNo) {
		return deviceContextHolder.get(deviceNo);
	}

	public void create(Device device) {
		try {
			ParkDeviceEnums parkDeviceEnums = ParkDeviceEnums.valueOf(device.getDeviceType().name() + device.getBrand());
			ParkDevice parkDevice = (ParkDevice) parkDeviceEnums.getClazz().getConstructors()[0].newInstance(device);
			deviceContextHolder.put(device.getDeviceNo(), parkDevice);
			deviceBindPassageway(device);
		} catch (Exception e) {
			log.error("初始化设备异常 {}", device, e);
		}
	}

	/**
	 * 通道、设备绑定
	 *
	 * @param device
	 */
	private void deviceBindPassageway(Device device) {
		if (StringUtils.isNotBlank(device.getPassagewayCode())) {
			String passagewayCode = device.getPassagewayCode();
			List<Device> list;
			if (deviceGateContextHolder.containsKey(passagewayCode)) {
				list = deviceGateContextHolder.get(passagewayCode);
			} else {
				list = new ArrayList<>();
			}
			list.add(device);
			deviceGateContextHolder.put(passagewayCode, list);
		}
	}
}
