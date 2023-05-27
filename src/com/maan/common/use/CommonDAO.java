package com.maan.common.use;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface CommonDAO extends CommonBaseDAO {
	public int getMaxID(final String tableName, final String fieldName) throws CommonBaseException;

	public String getFieldValue(final String tableName, final String fieldName, final String condition) throws CommonBaseException;

	public int getCount(final String tableName, final String fieldName, final String condition) throws CommonBaseException ;
	
	public String getSysDate() throws CommonBaseException;
	
	public String readFiles(String path) throws CommonBaseException;
	
	public String[] makeTwoDimArray(final String combined)throws CommonBaseException;
	
	public String updateSingleField(final String tableName, final String fieldName, final String condition, final String condition1) throws CommonBaseException ;
	
	}
