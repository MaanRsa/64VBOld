package com.maan.common.exception;

import java.sql.Timestamp;
import java.util.Date;

public class MotorBaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient Throwable throwableError;

	private transient String strMessage;

	private final transient String strErrorTime;

	/**
	 * Constructor to create the BaseException object with the spcified
	 * parameter.
	 * 
	 * @param error -
	 *            Throwable , Exception or any of its subclass
	 * @param userFriendlyMessage -
	 *            User Friendly message shown to the User
	 */

	public MotorBaseException(final Throwable error,final String strMessage) {
		super();
		this.throwableError = error;
		this.strMessage = strMessage;
		this.strErrorTime = new Timestamp(new Date().getTime()).toString();
	}

	/**
	 * Gets the error object associated
	 * 
	 * @return Throwable - Returns the error object associated
	 */
	public Throwable getError() {
		return throwableError;
	}

	/**
	 * Gets the user friendly message associated with the error
	 * 
	 * @return String - Returns the user friendly message associated with the
	 *         error.
	 */
	public String getErrorMessage() {
		return strMessage;
	}

	/**
	 * Gets the error id
	 * 
	 * @return String - Returns the error id
	 */
	public String getErrorTime() {
		return strErrorTime;
	}

	/**
	 * Sets the error associated
	 * 
	 * @param error -
	 *            Throwable representing the error object
	 */
	public void setError(final Throwable error) {
		this.throwableError = error;
	}

	/**
	 * Sets the error-message associated in the Message Constants file
	 * 
	 * @param error -
	 *            String object for setting the error message
	 */
	public void setErrorMessage(final String strMessage) {
		this.strMessage = strMessage;
	}

	public String toString() {
		final StringBuffer strBuffer = new StringBuffer(500);
		strBuffer.append(" \n Exception Occurred : " + strMessage + " -->> "
				+ strErrorTime);
		return strBuffer.toString();
	}
}
