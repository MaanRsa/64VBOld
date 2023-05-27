package com.maan.common;

import java.util.HashMap;
import java.util.Map;

public class Validation {

	private static final String NEEDED = "needed";
	private static final String INVALID = "Invalid";
	
	public String validString(final String value, final boolean format) {
		String returnval = "";
		try {
			final String val = value.trim();
			//String validChar = null;
			if (val.length() > 0) {
				/*
				 * value=value.toLowerCase();
				 * validChar="+-abcdefghijklmnopqrstuvwxyz' "; if(format)
				 * validChar="1234567890"; for(int i=0;i<value.length();i++) {
				 * //char c=c.charAt(i); if(validChar.indexOf(value.charAt(i))==
				 * -1) returnval="Invalid"; }
				 */
				returnval="";
			} else
			{
				returnval = NEEDED;
			}
		} catch (Exception e) {
			returnval = NEEDED;
		}
		return returnval;
	}

	public String validLength(final String value, final int len) {
		String returnval = "";
		try {
			final String val = value.trim();
			if (val.length() >= len) {
				returnval = "";
			} else
			{
				returnval = NEEDED;
			}
		} catch (Exception e) {
			returnval = NEEDED;
		}
		return returnval;
	}

	public String emailValidate(final String mailId) {
		String returnval = "";
		try {
			final String mailid = mailId.trim();
			if (mailid.length() > 0) {
				final char charac[] = mailid.toCharArray();
				if(!Character.isLetter(charac[0])){
					returnval = INVALID;
				}else if (mailid.indexOf('@') == -1) {
					returnval = INVALID;
				} else if ((mailid.substring(0, mailid.indexOf('@'))).length() < 2
						|| (mailid.substring(mailid.indexOf('@') + 1)).length() < 7
						|| (mailid.substring(mailid.indexOf('@') + 1))
								.indexOf('.') == -1) {
					returnval = INVALID;
				}
			}
		} catch (Exception e) {
			returnval = NEEDED;
		}
		return returnval;
	}

	public String checkDate(final String strDate) {
		String returnVal = "";
		final java.text.SimpleDateFormat datfor = new java.text.SimpleDateFormat(
				"dd/MM/yyyy");
		datfor.setLenient(false);
		final java.text.ParsePosition pos = new java.text.ParsePosition(0);

		final java.util.Date date = datfor.parse(strDate, pos);

		if ((date == null) || (pos.getErrorIndex() != -1)) {

			if (date == null) {
				returnVal = INVALID;
			}
			if (pos.getErrorIndex() != -1) {
				returnVal = INVALID;
			}
			returnVal = INVALID;
		}
		return returnVal;
	}

	public String validInteger(final String value) {
		String returnval = "";
		try {
			Long.parseLong(value);
			//LogManager.info("--" + Long.parseLong(value));
		} catch (Exception e) {
			returnval = INVALID;
		}
		return returnval;
	}

	public String validateVarchar(final String value) {

		String result = "";
		final String Value = value.trim();
		for (int i = 0; i < Value.length(); ++i) {
			final char car = Value.charAt(i);
			if (!Character.isDigit(car) && !Character.isLetter(car))
			{
				result = INVALID;
			}
		}
		return result;
	}
	
	public String validateVarchar2(final String value) {

		String result = "";
		final String Value = value.trim();
		for (int i = 0; i < Value.length(); ++i) {
			final char car = Value.charAt(i);
			if (!Character.isDigit(car) && !Character.isLetter(car))
			{
				result = INVALID;
			}
		}
		return result;
	}
	public String validateStringValue(final String value) {

		String result = "";
		final String Value = value.trim();
		for (int i = 0; i < Value.length(); ++i) {
			final char car = Value.charAt(i);
			if (!Character.isLetter(car))
			{
				result = INVALID;
			}
		}
		return result;
	}

	public String validateDate(final int year, final int month, final int day) {
		String result = "";
		if (month == 0 || day == 0 || year == 0) {
			result = INVALID;
		} else if (month == 2 && year % 4 == 0) {
			if (day == 30 || day == 31) {
				result = INVALID;
			}
		} else if (month == 2 && year % 4 != 0) {
			if (day == 29 || day == 30 || day == 31) {
				result = INVALID;
			}
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day == 31) {
				result = INVALID;
			}
		}
		return result;
	}
	
	public String isNull(final String content){
		final StringBuffer contents = new StringBuffer(1000);
		if(content==null){
			contents.append("");
		}
		else{
			contents.append(content);
		}
		return contents.toString();
	}
	
	public String isNull(final String content,final String value){
		LogManager.push("content="+content+"value="+value);
		final StringBuffer contents = new StringBuffer();
		if(content==null||content.length()<=0){
			contents.append(value);
		}
		else{
			contents.append(content);
		}
		return contents.toString();
	}
	
	public String[] isNull(final String[] content){
		String[] contents;
		if(content==null||content.length<=0){
			contents =new String[0];
		}
		else
		{
			contents = content;
		}
		return contents;
	}
	
	public String isSelect(final String content,final String value){
		final StringBuffer contents = new StringBuffer();
		if(content==null || "Select".equalsIgnoreCase(content) ||content.length()<=0){
			contents.append(value);
		}
		else{
			contents.append(content);
		}
		return contents.toString();
	}
	
	public String isSelect(final String content){
		final StringBuffer contents = new StringBuffer();
		if(content==null || "Select".equalsIgnoreCase(content) ||content.length()<=0){
			contents.append("");
		}
		else{
			contents.append(content);
		}
		return contents.toString();
	}

	public String getStringMonth(final int month) {
		String iVal = "";
		if (month == 1)
		{
			iVal = "Jan";
		}
		else if (month == 2)
		{
			iVal = "Feb";
		}
		else if (month == 3)
		{
			iVal = "Mar";
		}
		else if (month == 4)
		{
			iVal = "Apr";
		}
		else if (month == 5)
		{
			iVal = "May";
		}
		else if (month == 6)
		{
			iVal = "Jun";
		}
		else if (month == 7)
		{
			iVal = "Jul";
		}
		else if (month == 8)
		{
			iVal = "Aug";
		}
		else if (month == 9)
		{
			iVal = "Sep";
		}
		else if (month == 10)
		{
			iVal = "Oct";
		}
		else if (month == 11)
		{
			iVal = "Nov";
		}
		else if (month == 12)
		{
			iVal = "Dec";
		}
		return iVal;
	}
	
	public int getIntegerMonth(final String month) {
		int iVal = 0;
		final Map hsIntMonth = new HashMap();
		hsIntMonth.put("Jan", Integer.valueOf(1));
		hsIntMonth.put("Feb", Integer.valueOf(2));
		hsIntMonth.put("Mar", Integer.valueOf(3));
		hsIntMonth.put("Apr", Integer.valueOf(4));
		hsIntMonth.put("May", Integer.valueOf(5));
		hsIntMonth.put("Jun", Integer.valueOf(6));
		hsIntMonth.put("Jul", Integer.valueOf(7));
		hsIntMonth.put("Aug", Integer.valueOf(8));
		hsIntMonth.put("Sep", Integer.valueOf(9));
		hsIntMonth.put("Oct", Integer.valueOf(10));
		hsIntMonth.put("Nov", Integer.valueOf(11));
		hsIntMonth.put("Dec", Integer.valueOf(12));
		try {
			if (hsIntMonth.get(month) != null) {
				iVal = Integer.parseInt(hsIntMonth.get(month).toString());
			}
		} catch (Exception e) {
			LogManager.debug(e);
		}
		return iVal;
	}

}
