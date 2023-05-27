package com.maan.password;

import com.maan.common.LogManager;

public class SystemUnavailableException extends Exception {
	public static String error = "";

	public SystemUnavailableException() {

	}

	public SystemUnavailableException(final String exception) {
		error = exception;
	}

	public static void main(final String[] args) {
		LogManager.debug("SystemUnavailableException - Enter");
		LogManager.debug("SystemUnavailableException - Enter"+error);
	}
}