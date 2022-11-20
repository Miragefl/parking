package com.viscum.device.camera.visionzenith;

import lombok.Data;

/**
 * 臻识相机配置
 */
@Data
public class VisionZenithConfig {

	private String ip;
	private int port;
	private String name;
	private String password;
	private String volume;
}
