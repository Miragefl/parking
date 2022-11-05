package com.viscum.device;

import lombok.Data;

/**
 * led展示文本
 * @author fenglei
 */
@Data
public class LedText {
	private int line;
	private String text;
	private int color;
	private String front;
	private int frontSize;
}
