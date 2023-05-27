package com.maan.common;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

public class MotorValidation {

	public ActionErrors notNull(final String arg, final ActionErrors errors,
			final String errorName) {
		if (arg == null || arg.length() < 1) {
			final String errName = errorName.substring(errorName.lastIndexOf('.'),
					errorName.length());
			errors.add(errName, new ActionError(errorName));
		}
		return errors;
	}

	public ActionErrors notEqual(final String arg, final String value,
			final ActionErrors errors, final String errorName) {
		if (!value.equals(arg)) {
			final String errName = errorName.substring(errorName.lastIndexOf('.'),
					errorName.length());
			errors.add(errName, new ActionError(errorName));
		}
		return errors;

	}
}
