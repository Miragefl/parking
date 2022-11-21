package com.viscum.device.camera.visionzenith;

import lombok.Data;

/**
 * 臻识相机配置
 *
 * @author viscum
 */
@Data
public class VisionZenithCameraConfig {

	private String ip;
	private int port;
	private String name;
	private String password;
	private String volume;
}
