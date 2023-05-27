/*
 * Created on Jan 16, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.maan.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import com.maan.common.exception.MotorBaseException;

/**
 * @author Vinoth.K
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MotorBaseCB {

	public Map getDate(final boolean isMajor, final int noOfYear)
			throws MotorBaseException {

		String empty;
		empty = "";
		final String[] monthStr = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		final TreeMap map = new TreeMap();
		final ArrayList date = new ArrayList();
		final TreeMap month = new TreeMap();
		final ArrayList year = new ArrayList();
		int startYear;

		LogManager.push("GetDate() method ");
		LogManager.debug("- Enter");

		final GregorianCalendar calendar = new GregorianCalendar();

		//date.add("DD");
		for (int i = 1; i < 32; i++) {
			if (i < 10) {
				date.add("0" + i);
			} else {
				date.add(i + empty);
			}
		}
		map.put("date", date);

		//month.put("MMM", "MMM");
		for (int i = 0; i < monthStr.length; i++) {
			if (i < 9) {
				month.put("0" + (i + 1), monthStr[i]);
			} else {
				month.put((i + 1) +empty, monthStr[i]);
			}
			
		}
		map.put("month", month);

		//year.add("YYYY");
		if (isMajor) {
			startYear = calendar.get(Calendar.YEAR) - 18;
		} else {
			startYear = calendar.get(Calendar.YEAR);
		}

		for (int i = startYear; i > startYear - noOfYear; i--) {
			year.add(Integer.valueOf(i));
		}
		map.put("year", year);

		LogManager.debug("- Exit");
		LogManager.popRemove(); // Should be the last statement

		return map;
	}
}
