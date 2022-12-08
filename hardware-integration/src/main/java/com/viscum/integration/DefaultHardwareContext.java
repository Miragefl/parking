package com.viscum.integration;

import com.viscum.model.Hardware;
import com.viscum.integration.base.ParkHardware;
import com.viscum.integration.base.HardwareTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class DefaultHardwareContext implements HardwareContext {

	public static Map<String, ParkHardware> deviceContextHolder = new ConcurrentHashMap<>();

	public static Map<String, List<Hardware>> deviceGateContextHolder = new ConcurrentHashMap<>();

	@Override
	public void initDevice(HardwareLoader loader) {
		List<Hardware> hardwareList = loader.load();
		hardwareList.forEach(this::create);
	}

	@Override
	public ParkHardware getDevice(String deviceNo) {
		return deviceContextHolder.get(deviceNo);
	}

	public void create(Hardware hardware) {
		try {
			HardwareTypeEnums hardwareTypeEnums = HardwareTypeEnums.valueOf(hardware.getHwType().name() + hardware.getBrand());
			ParkHardware parkHardware = (ParkHardware) hardwareTypeEnums.getClazz().getConstructors()[0].newInstance(hardware);
			deviceContextHolder.put(hardware.getHwCode(), parkHardware);
			deviceBindPassageway(hardware);
		} catch (Exception e) {
			log.error("初始化设备异常 {}", hardware, e);
		}
	}

	/**
	 * 通道、设备绑定
	 *
	 * @param hardware
	 */
	private void deviceBindPassageway(Hardware hardware) {
		if (StringUtils.isNotBlank(hardware.getGatewayCode())) {
			String passagewayCode = hardware.getGatewayCode();
			List<Hardware> list;
			if (deviceGateContextHolder.containsKey(passagewayCode)) {
				list = deviceGateContextHolder.get(passagewayCode);
			} else {
				list = new ArrayList<>();
			}
			list.add(hardware);
			deviceGateContextHolder.put(passagewayCode, list);
		}
	}
}
