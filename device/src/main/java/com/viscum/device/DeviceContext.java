package com.viscum.device;

import com.viscum.device.base.ParkDevice;

public interface DeviceContext {

	void initDevice(DeviceLoader loader);

	ParkDevice getDevice(String deviceNo);
}
