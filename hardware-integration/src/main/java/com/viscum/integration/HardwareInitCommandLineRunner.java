package com.viscum.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class HardwareInitCommandLineRunner implements CommandLineRunner {

	@Autowired
	private HardwareContext hardwareContext;

	@Autowired
	private HardwareLoader hardwareLoader;

	@Override
	public void run(String... args) throws Exception {
		CompletableFuture.runAsync(() -> hardwareContext.initDevice(hardwareLoader));
	}
}
