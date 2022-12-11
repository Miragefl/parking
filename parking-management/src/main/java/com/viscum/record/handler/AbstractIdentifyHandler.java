package com.viscum.record.handler;

import com.viscum.model.IdentifyResult;

/**
 * 识别结果处理器
 *
 * @author viscum
 */
public abstract class AbstractIdentifyHandler {

	public abstract boolean execute(IdentifyResult identifyResult);
}
