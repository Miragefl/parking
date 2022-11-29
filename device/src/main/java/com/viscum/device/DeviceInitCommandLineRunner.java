package com.viscum.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DeviceInitCommandLineRunner implements CommandLineRunner {

	@Autowired
	private DeviceContext deviceContext;

	@Autowired
	private DeviceLoader deviceLoader;

	@Override
	public void run(String... args) throws Exception {
		deviceContext.initDevice(deviceLoader);
	}
}
