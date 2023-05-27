package com.maan.common.base;

import java.util.HashMap;

import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;

public class CommonDaoFactory {

	public static Object getDAO(final String daoName)
			throws CommonBaseException {
		final HashMap mapDAO = new HashMap();
		try {
			final String envName = daoName + "Impl";

			mapDAO.put(envName, loadDAOObjectFromClass(envName));

			return mapDAO.get(envName);
		} catch (Exception e) {
			throw new CommonBaseException(e, e.getMessage());
		}
	}

	private static Object loadDAOObjectFromClass(final String className)
			throws CommonBaseException {

		Object returnDao;
		try {
			final Class clazz = Class.forName(className);
			returnDao = clazz.newInstance();
		} catch (ClassNotFoundException ins) {

			throw new CommonBaseException(ins,
					CommonExceptionConstants.CLASS_NOT_FOUND);
		} catch (InstantiationException ins) {

			throw new CommonBaseException(ins,
					CommonExceptionConstants.INSTANTIATION);
		} catch (IllegalAccessException ins) {

			throw new CommonBaseException(ins,
					CommonExceptionConstants.ILLEGAL_ACCESS);
		}
		return returnDao;
	}
}
