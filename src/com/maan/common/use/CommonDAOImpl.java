package com.maan.common.use;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Map;
import java.util.StringTokenizer;



import com.maan.common.*;
import com.maan.common.base.CommonBaseDAOImpl;


import com.maan.common.exception.CommonBaseException;

public class CommonDAOImpl extends CommonBaseDAOImpl implements CommonDAO {

	public int getMaxID(String tableName, String fieldName)
			throws CommonBaseException {
		String query;
		int id;
		query = "select nvl(max(" + fieldName + "),0)+1 from " + tableName + "";
		id = this.mytemplate.queryForInt(query);
		return id;
	}

	public int getCount(final String tableName, final String fieldName,
			final String condition) throws CommonBaseException {
		LogManager.push("CommonDAOImpl getFieldValue() method ");
		LogManager.logEnter();
		
		String duplicateQuery1 = "select count(*) from " + tableName
				+ " where " + condition;
		LogManager.info("duplicateQuery1-->" + duplicateQuery1);
		int x = this.mytemplate.queryForInt(duplicateQuery1);

		return x;
	}

	public String updateSingleField(final String tableName,
			final String fieldName, final String condition,
			final String condition1) throws CommonBaseException {
		LogManager.push("CommonDAOImpl updateSingleField() method ");
		LogManager.logEnter();

		String duplicateQuery1 = "update " + tableName + " set " + fieldName
				+ "=" + condition + " where " + condition1;
		LogManager.info("duplicateQuery1-->" + duplicateQuery1);
		int x = this.mytemplate.update(duplicateQuery1);
		if(x==0){
		return "update fails";
		}else{
		return "update success";	
		}
	}

	public String getFieldValue(final String tableName, final String fieldName,
			final String condition) throws CommonBaseException {
		String query;
		String result;
		Map map;
		query = "select  " + fieldName + " from " + tableName + " where "
				+ condition;
		map = this.mytemplate.queryForMap(query);
		result = (String) (map.get(fieldName) + "");
		return result;
	}

	public String getSysDate() throws CommonBaseException {
		String query;
		String result;
		Map map;
		query = "SELECT to_char(SYSDATE ,'dd-mm-yyyy')as START_DATE FROM DUAL";
		map = this.mytemplate.queryForMap(query);
		result = (String) (map.get("START_DATE") + "");
		return result;

	}

	public String readFiles(String path) throws CommonBaseException {

		String status = "";
		File file = new File(path);
		try {

			FileInputStream fis = null;
			BufferedInputStream bis = null;
			DataInputStream dis = null;

			fis = new FileInputStream(file);
			// Here BufferedInputStream is added for fast reading.
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			// dis.available() returns 0 if the file does not have more lines.
			while (dis.available() != 0) {

				// this statement reads the line from the file and print it to
				// the console.
				status += dis.readLine();
			}

			// dispose all the resources after using them.
			fis.close();
			bis.close();
			dis.close();

		} catch (FileNotFoundException e) {
			status = path;
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// LogManager.info("status is"+status);
		return status;

	}

	static String[] convertedArray;

	public String[] makeTwoDimArray(final String combined)
			throws CommonBaseException {

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

		return convertedArray;
	}
	
	
	
	public void validateDateInformations(final CommonDispatchForm cForm)
	{
		 String DATE_FORMAT = "dd-MM-yyyy";
		 String DATEFORMAT = "yyyy-MM-dd";
		 SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);	
		 SimpleDateFormat df = new SimpleDateFormat(DATEFORMAT);

		try{ 
			if(cForm.getTransDate().length()>10)
			cForm.setTransDate(sdf.format(df.parse(cForm.getTransDate())));
			if(cForm.getDateOfAdmission().length()>10)
			cForm.setDateOfAdmission(sdf.format(df.parse(cForm.getDateOfAdmission())));
			if(cForm.getDateOfDischarge().length()>10)
			cForm.setDateOfDischarge(sdf.format(df.parse(cForm.getDateOfDischarge())));
			if(cForm.getTransTime().length()>10)
			cForm.setTransTime(sdf.format(df.parse(cForm.getTransTime())));
			if(cForm.getInvestStartDate().length()>10)
			cForm.setInvestStartDate(sdf.format(df.parse(cForm.getInvestStartDate())));
			if(cForm.getInvestEndDate().length()>10)
			cForm.setInvestEndDate(sdf.format(df.parse(cForm.getInvestEndDate())));
			
		}catch(Exception e){LogManager.push("Exception In validateDateInformations: "+e);}
			
	}
	
}
