package com.maan.common.use;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;

public class CommonCB extends CommonBaseCB {
	
	
	
	public int getMaxID(final String tableName, final String fieldName)
			throws CommonBaseException {

		int result;
		final CommonDAO commonDAO;

		LogManager.push("getMaxID() method ");
		LogManager.logEnter();

		commonDAO = (CommonDAOImpl) CommonDaoFactory.getDAO(CommonDAO.class.getName());
		result = commonDAO.getMaxID(tableName, fieldName);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	
	public String updateSingleField(final String tableName, final String fieldName, final String condition,
			final String condition1) throws CommonBaseException {
	
		final CommonDAO commonDAO;

		LogManager.push("getMaxID() method ");
		LogManager.logEnter();

		commonDAO = (CommonDAOImpl) CommonDaoFactory.getDAO(CommonDAO.class.getName());
		String result = commonDAO.updateSingleField(tableName, fieldName,condition,condition1);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;	
	}
	
	public String getFieldValue(final String tableName, final String fieldName,
			final String condition) throws CommonBaseException {

		String result;
		final CommonDAO commonDAO;

		LogManager.push("getFieldValue() method ");
		LogManager.logEnter();

		commonDAO = (CommonDAOImpl) CommonDaoFactory.getDAO(CommonDAO.class.getName());
		result = commonDAO.getFieldValue(tableName, fieldName, condition);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public Date stringToDate(final String dateStr, final String formate) throws CommonBaseException {

		Date result;

		LogManager.push("stringToDate() method ");
		LogManager.logEnter();

		DateFormat formatter;
		try {
			formatter = new SimpleDateFormat(formate);
			result = (Date) formatter.parse(dateStr);
		} catch (Exception e) {
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public Date stringToDate(final String dateStr) throws CommonBaseException {

		Date result;

		LogManager.push("stringToDate() method ");
		LogManager.logEnter();

		DateFormat formatter;
		if (dateStr.length() == 0) {
			result = new Date();
		} else {
			try {
				formatter = new SimpleDateFormat("dd-mm-yyyy");
				result = (Date) formatter.parse(dateStr);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CommonBaseException(e,CommonExceptionConstants.OTHER_ERROR);
			}
		}
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	public Date stringToDate1(final String dateStr) throws CommonBaseException {

		Date result;

		LogManager.push("stringToDate() method ");
		LogManager.logEnter();

		DateFormat formatter;
		if (dateStr.length() == 0) {
			result = new Date();
		} else {
			try {
				formatter = new SimpleDateFormat("dd/mm/yyyy");
				result = (Date) formatter.parse(dateStr);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CommonBaseException(e,CommonExceptionConstants.OTHER_ERROR);
			}
		}
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	public String stringArrayToString(final String[] value,
			final String delimiter) throws CommonBaseException {
		String result;
		LogManager.push("stringArrayToString() method ");
		LogManager.logEnter();
		
		if (value != null) {
			result = value[0];
			for (int i = 1; i < value.length; i++) {
				result += delimiter + value[i];
				
			}
		} else {
			result = "";
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public String[] stringToStringArray(final String value,
			final String delimiter) throws CommonBaseException {
		String[] result;
		LogManager.push("stringToStringArray() method ");
		LogManager.logEnter();
		if (value != null) {
			result = value.split(delimiter);
		} else {
			result = new String[0];
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public String getSysDateInCommonCB() throws CommonBaseException {

		String result;
		final CommonDAO commonDAO;

		LogManager.push("getSysDateInCommonCB() method ");
		LogManager.logEnter();

		commonDAO = (CommonDAOImpl) CommonDaoFactory.getDAO(CommonDAO.class
				.getName());
		result = commonDAO.getSysDate();

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public String readFilesInCommonCB(String path) throws CommonBaseException {

		String result;
		final CommonDAO commonDAO;

		LogManager.push("readFilesInCommonCB() method ");
		LogManager.logEnter();

		commonDAO = (CommonDAOImpl) CommonDaoFactory.getDAO(CommonDAO.class
				.getName());
		result = commonDAO.readFiles(path);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public String[] makeTwoDimArrayInCB(final String combined)
			throws CommonBaseException {

		String[] result;
		final CommonDAO commonDAO;

		LogManager.push("readFilesInCommonCB() method ");
		LogManager.logEnter();

		commonDAO = (CommonDAOImpl) CommonDaoFactory.getDAO(CommonDAO.class
				.getName());
		result = commonDAO.makeTwoDimArray(combined);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	static String[] convertedArray;

	public static String[] makeTwoDimArray(final String combined) {
		LogManager.push("CommonCB makeTwoDimArray() method ");
		LogManager.logEnter();
		final StringTokenizer hasStr = new StringTokenizer(combined, "#");
		try {
			int count = 0;
			// int jcount = 0;
			while (hasStr.hasMoreTokens()) {
				final String first = hasStr.nextToken();
				final StringTokenizer tildStr = new StringTokenizer(first, ",");
				convertedArray = new String[tildStr.countTokens()];
				while (tildStr.hasMoreTokens()) {
					final String second = tildStr.nextToken();
					convertedArray[count] = second;
					count = count + 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogManager.logExit();
		LogManager.popRemove();
		return convertedArray;
	}

	public int getCount(final String tableName, final String fieldName,
			final String condition)throws CommonBaseException {
	
		LogManager.push("CommonCB getSearchField() method ");
		LogManager.logEnter();
		final CommonDAO commonDAO;

		commonDAO = (CommonDAOImpl) CommonDaoFactory.getDAO(CommonDAO.class
				.getName());
		
		int result=commonDAO.getCount(tableName, fieldName, condition);
		
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
		
	}
	
	public static String[] makeTwoDimArrayOne(final String combined) {
		LogManager.push("CommonCB makeTwoDimArrayOne() method ");
		LogManager.logEnter();
		final StringTokenizer hasStr = new StringTokenizer(combined, "#");
		try {
			int count = 0;
			// int jcount = 0;
			while (hasStr.hasMoreTokens()) {
				final String first = hasStr.nextToken();
				final StringTokenizer tildStr = new StringTokenizer(first, "*");
				convertedArray = new String[tildStr.countTokens()];
				while (tildStr.hasMoreTokens()) {
					final String second = tildStr.nextToken();
					convertedArray[count] = second;
					count = count + 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogManager.logExit();
		LogManager.popRemove();
		return convertedArray;
	}
	
}
