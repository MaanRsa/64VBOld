package com.maan.common;

import java.util.HashMap;

import com.maan.common.exception.MotorBaseException;
import com.maan.common.exception.MotorExceptionConstants;

public class MotorDaoFactory {

	/**
	 * This recursive method is used to return the DAo object
	 * 
	 * @param daoName
	 * @return
	 */
	public static Object getDAO(final String daoName) throws MotorBaseException {
		final HashMap mapDAO = new HashMap();
		try {
			final String envName = daoName + "Impl";
//			if (mapDAO.containsKey(envName)) {
				//mapDAO.put(envName, loadDAOObjectFromClass(envName));
		//	}
			//else
			//{
				mapDAO.put(envName, loadDAOObjectFromClass(envName));
			//}
			
			return mapDAO.get(envName);
		} catch (Exception e) {
			throw new MotorBaseException(e, e.getMessage());
		}
	}

	/**
	 * Method is used for instance creation
	 * 
	 * @param className
	 * @return
	 */
	private static Object loadDAOObjectFromClass(final String className)
			throws MotorBaseException {
		// final String methodName = "loadDAOObjectFromClass";
		// log.entering(className, methodName);
		Object returnDao;
		try {
			final Class clazz = Class.forName(className);
			returnDao = clazz.newInstance();
		} catch (ClassNotFoundException ins) {
			// log.log(Level.SEVERE,"Class not found exception occured ",ins);
			// log.throwing(className, methodName,ins);
			throw new MotorBaseException(ins,
					MotorExceptionConstants.CLASS_NOT_FOUND);
		} catch (InstantiationException ins) {
			// log.log(Level.SEVERE,"InstantiationException occured ",ins);
			// log.throwing(className, methodName,ins);
			throw new MotorBaseException(ins,
					MotorExceptionConstants.INSTANTIATION);
		} catch (IllegalAccessException ins) {
			// log.log(Level.SEVERE,"IllegalAccessException occured ",ins);
			// log.throwing(className, methodName,ins);
			throw new MotorBaseException(ins,
					MotorExceptionConstants.ILLEGAL_ACCESS);
		}
		return returnDao;
	}
}
