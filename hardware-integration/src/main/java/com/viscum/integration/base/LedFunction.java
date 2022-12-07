package com.viscum.integration.base;

import java.util.List;

public interface LedFunction {

	int play(String playText);

	int show(List<LedText> list);
}
