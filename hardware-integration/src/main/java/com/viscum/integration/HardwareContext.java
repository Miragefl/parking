package com.viscum.integration;

import com.viscum.integration.base.ParkHardware;

public interface HardwareContext {

	void initDevice(HardwareLoader loader);

	ParkHardware getDevice(String deviceNo);
}
