package com.viscum.device.camera.visionzenith;

import lombok.Data;

@Data
public class VisionZenithConfig {

	private String ip;
	private int port;
	private String name;
	private String password;
	private String rtspUrl;
	private String volume;
}
