package com.viscum.integration.base;

import lombok.Data;

/**
 * led展示文本
 *
 * @author viscum
 */
@Data
public class LedText {
	private int line;
	private String text;
	private int color;
	private int front;
	private int frontSize;
}
