package com.viscum.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class DeviceInitCommandLineRunner implements CommandLineRunner {

	@Autowired
	private DeviceContext deviceContext;

	@Autowired
	private DeviceLoader deviceLoader;

	@Override
	public void run(String... args) throws Exception {
		CompletableFuture.runAsync(() -> deviceContext.initDevice(deviceLoader));
	}
}
