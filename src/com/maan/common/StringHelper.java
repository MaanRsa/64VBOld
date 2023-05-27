package com.maan.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringHelper {

	public boolean checkDate(final String inDate, final String dateFormat) {
		final int dateFormatLength = dateFormat.length();
		boolean result;
		try {
			if (inDate.length() == dateFormatLength) {
				final SimpleDateFormat format = new SimpleDateFormat(dateFormat,Locale.ENGLISH);
				format.setLenient(false);
				format.parse(inDate);
				result = true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public Date covertDate(final String inDate, final String dateFormat) {
		Date date;
		final int dateFormatLength = dateFormat.length();
		try {
			if (inDate.length() == dateFormatLength) {
				final SimpleDateFormat format = new SimpleDateFormat(dateFormat,Locale.ENGLISH);
				format.setLenient(false);
				date = format.parse(inDate);
			} else {
				throw new Exception();
				// return date;
			}
		} catch (Exception e) {
			// return null;
			date = null;
		}
		return date;
	}

	public static String getSearchValue(final String type, final String value) {
		String result;

		if ("Start With".equals(type)) {// starting with search
			result = " like \'" + value + "%\'";
		} else if ("End With".equals(type)) { // Endeing With Search
			result = " like \'%" + value + "\'";
		} else if ("Contains".equals(type)) { // contains
			result = " like \'%" + value + "%\'";
		} else { // equal
			result = " = \'" + value + "\'";
		}

		return result;
	}

}
