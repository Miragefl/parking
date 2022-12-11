package com.viscum.record.handler.basic;

import com.viscum.model.IdentifyResult;
import com.viscum.record.handler.AbstractIdentifyHandler;

public class UnlicensedCarHandler extends AbstractIdentifyHandler {
	@Override
	public boolean execute(IdentifyResult identifyResult) {
		return false;
	}
}
