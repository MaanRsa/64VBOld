package com.maan.common;

import java.util.Calendar;

import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PatternLayout;

public class LogManager {
	private static Logger logger = null;
	
	private static PatternLayout layout = new PatternLayout("%d{dd:MMM:yyyy-HH:mm:ss:SSS} %m %n");
	
	private static String path = System.getProperty("catalina.home");
	private static String copyTo = path+"\\logs\\64VB";
		
	private static void getAppLogger() {
		if (logger == null) {
			try{
				Calendar dt = Calendar.getInstance();
				copyTo = copyTo + dt.get(Calendar.DATE)+ "" + (dt.get(Calendar.MONTH)+1) + "" + dt.get(Calendar.YEAR) + "" + dt.get(Calendar.HOUR) + "" + dt.get(Calendar.MINUTE) + ".log";
				Process pr = Runtime.getRuntime().exec("cmd /c copy \""+path+"\\logs\\64VB.log\" \""+copyTo+"\"");
				pr.waitFor();
			}catch(Exception e){
				e.printStackTrace();
			}
			logger = Logger.getLogger(LogManager.class);
			RollingFileAppender appender = null;
			try{
				appender = new RollingFileAppender(layout,path+"/logs/64VB.log",false);
			}catch(Exception e){
				e.printStackTrace();
			}
			logger.addAppender(appender);
			logger.setLevel(Level.ALL);			
		}
	}

	/**
	 * Logs a message object with the DEBUG level.
	 * 
	 * @param strMessage
	 *            The message to log
	 */
	public static void debug(final String strMessage) {
		getAppLogger();
		logger.debug(strMessage);
		System.out.println(strMessage);
	}

	public static void logEnter() {
		debug("- Enter");
	}

	public static void logExit() {
		debug("- Exit");
	}

	/**
	 * Logs the stack trace of the Exception with the DEBUG level.
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void debug(final Exception exception) {
		getAppLogger();
		logger.debug("", exception);
		System.out.println(exception);
	}

	/**
	 * Logs the stack trace of the Exception with the DEBUG level.
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void debug(final Throwable throwable) {
		getAppLogger();
		logger.debug("", throwable);
		System.out.println(throwable);
	}

	/**
	 * Logs a message object with the INFO level.
	 * 
	 * @param strMessage
	 *            The message to log
	 */
	public static void info(final String strMessage) {
		getAppLogger();
		logger.info(strMessage);
		System.out.println(strMessage);
	}

	/**
	 * Logs the stack trace of the Exception with the INFO level. .
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void info(final Object strMessage) {
		getAppLogger();
		logger.info(strMessage);
		System.out.println(strMessage);
	}

	/**
	 * Logs the stack trace of the Exception with the INFO level. .
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */

	public static void info(final boolean booMessage) {
		 getAppLogger();
		 logger.info(booMessage);
		 System.out.println(booMessage);
	}

	/**
	 * Logs the stack trace of the Exception with the INFO level. .
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void info(final Exception exception) {
		getAppLogger();
		logger.info("", exception);
		System.out.println(exception);
	}

	/**
	 * Logs the stack trace of the Exception with the INFO level. .
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void info(final Throwable throwable) {
		getAppLogger();
		logger.info("", throwable);
		System.out.println(throwable);
	}

	/**
	 * Logs a message object with the WARN level.
	 * 
	 * @param strMessage
	 *            The message to log
	 */
	public static void warn(final String strMessage) {
		getAppLogger();
		logger.warn(strMessage);
		System.out.println("PUSH: " + strMessage);
	}

	/**
	 * Logs the stack trace of the Exception with the WARN level. .
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void warn(final Exception exception) {
		getAppLogger();
		logger.warn("", exception);
		System.out.println(exception);
	}

	/**
	 * Logs the stack trace of the Exception with the WARN level. .
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void warn(final Throwable throwable) {
		getAppLogger();
		logger.warn("", throwable);
		System.out.println(throwable);
	}

	/**
	 * Logs a message object with the ERROR level.
	 * 
	 * @param strMessage
	 *            The message to log
	 */
	public static void error(final String strMessage) {
		getAppLogger();
		logger.error(strMessage);
		System.out.println("PUSH: " + strMessage);
	}

	/**
	 * Logs the stack trace of the Exception with the ERROR level. .
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void error(final Exception exception) {
		getAppLogger();
		logger.error("", exception);
		System.out.println(exception);
	}

	/**
	 * Logs the stack trace of the Exception with the ERROR level. .
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void error(final Throwable throwable) {
		getAppLogger();
		logger.error("", throwable);
		System.out.println(throwable);
	}

	/**
	 * Logs a message object with the FATAL level.
	 * 
	 * @param strMessage
	 *            The message to log
	 */
	public static void fatal(final String strMessage) {
		getAppLogger();
		logger.fatal(strMessage);
		System.out.println(strMessage);
	}

	/**
	 * Logs the stack trace of the Exception with the FATAL level. .
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void fatal(final Exception exception) {
		getAppLogger();
		logger.fatal("", exception);
		System.out.println(exception);
	}

	/**
	 * Logs the stack trace of the Exception with the FATAL level. .
	 * 
	 * @param exception
	 *            The exception to log, including its stack trace.
	 * 
	 */
	public static void fatal(final Throwable throwable) {
		getAppLogger();
		logger.fatal("", throwable);
		System.out.println(throwable);
	}

	/**
	 * Push new diagnostic context information for the current thread
	 * 
	 * @param strMessage
	 *            The new diagnostic context information
	 * 
	 */
	public static void push(final String strMessage) {
		getAppLogger();
		logger.debug(strMessage);
		System.out.println("PUSH: " + strMessage);
	}

	/**
	 * Pop new diagnostic context information for the current thread
	 * 
	 */
	public static void pop() {
		getAppLogger();
		NDC.pop();
	}

	/**
	 * clears diagnostic context information memory for the current thread
	 * 
	 * @param strMessage
	 *            The new diagnostic context information
	 * 
	 */
	public static void remove() {
		getAppLogger();
		NDC.remove();
	}

	/**
	 * Pop new diagnostic context information for the current thread and clears
	 * the memory
	 * 
	 */
	public static void popRemove() {
		pop();
		remove();
	}
}
