package com.maan.admin.master.error;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.jdbc.core.RowMapper;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;

public class ErrorDAOImpl extends CommonBaseDAOImpl implements ErrorDAO
{
	private final static String ERID="ERROR_ID";
	private final static String ECODE="ERROR_CODE";
	private final static String EDESC= "ERROR_DESC";
	private final static String ACTIVE="ACTIVE";
	public String insertErrorDetails(final ErrorVB errorVB) throws CommonBaseException
	{
		LogManager.push("ErrorDAOImpl insertErrorDetails() method Starts");
		LogManager.logEnter();
		String query;
		String result="";
		
		query = "insert into ERROR_MASTER(ERROR_ID,ERROR_CODE,ERROR_DESC,ACTIVE) values (?,?,?,?)";
        
		Object[] args = new Object[4];
		args[0] = errorVB.getErrorId();		
		args[1] = errorVB.getErrorCode();
		args[2] = errorVB.getErrorDesc();		
		args[3] = errorVB.getActive();
		
		final int insertedRows = this.mytemplate.update(query, args);
		
		if(insertedRows >= 1){
			result = "Inserted Successfully";
		}
		else if(insertedRows == 0){
			result = "";
		}
		
		LogManager.logExit();
		LogManager.push("ErrorDAOImpl insertErrorDetails() method  Ends");
		LogManager.popRemove(); 
		return result;
		
	}
	public String updateErrorDetails(final ErrorVB errorVB) throws CommonBaseException
	{
		LogManager.push("ErrorDAOImpl updateErrorDetails() method Starts");
		LogManager.logEnter();
		String query;
		String result="";
		
		query = "update ERROR_MASTER set ERROR_CODE = ? , ERROR_DESC = ?, ACTIVE = ? where ERROR_ID = ?";
        
		Object[] args = new Object[4];
		args[0] = errorVB.getErrorCode();		
		args[1] = errorVB.getErrorDesc(); 
		args[2] = errorVB.getActive();
		args[3] = errorVB.getErrorId();
		
		final int insertedRows = this.mytemplate.update(query, args);
		
		if(insertedRows >= 1){
			result = "Updated Successfully";
		}
		else if(insertedRows == 0){
			result = "";
		}
		
		LogManager.logExit();
		LogManager.push("ErrorDAOImpl updateErrorDetails() method  Ends");
		LogManager.popRemove(); 
		return result;
		
	}
	
	public List getErrorList() throws CommonBaseException
	{
		LogManager.push("ErrorDAOImpl getErrorList() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
		final String query="select ERROR_ID,ERROR_CODE,ERROR_DESC,ACTIVE from ERROR_MASTER";
		
		list = (ArrayList) this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet resultSet, final int identity) throws SQLException {
				final ErrorVB errorVB = new ErrorVB();
				errorVB.setErrorId(resultSet.getString(ERID));
				errorVB.setErrorCode(resultSet.getString(ECODE));
				errorVB.setErrorDesc(resultSet.getString(EDESC));
				errorVB.setActive(resultSet.getString(ACTIVE));
				return errorVB;
			}
		});
		
		LogManager.logExit();
		LogManager.push("ErrorDAOImpl getErrorList() method  Ends");
		LogManager.popRemove(); 
		return list;
	}
	
	public List getEditErrorDetails (final String updateId) throws CommonBaseException
	{
		LogManager.push("ErrorDAOImpl getEditErrorDetails() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
		final String query="select ERROR_ID,ERROR_CODE,ERROR_DESC,ACTIVE from ERROR_MASTER where ERROR_ID = "+updateId ;
		
		list = (ArrayList) this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet resultSet,final int identity) throws SQLException {
				final ErrorVB errorVB = new ErrorVB();
				errorVB.setErrorId(resultSet.getString(ERID));
				errorVB.setErrorCode(resultSet.getString(ECODE));
				errorVB.setErrorDesc(resultSet.getString(EDESC));
				errorVB.setActive(resultSet.getString(ACTIVE));
				return errorVB;
			}
		});
		
		LogManager.logExit();
		LogManager.push("ErrorDAOImpl getEditErrorDetails() method  Ends");
		LogManager.popRemove(); 
		return list;
	}
	
	public List getSearchByErrorCode(final String searchValue) throws CommonBaseException
	{
		LogManager.push("ErrorDAOImpl getSearchByErrorCode() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
		
		final String query="select ERROR_ID,ERROR_CODE,ERROR_DESC,ACTIVE from ERROR_MASTER where lower(ERROR_CODE) like ('%"+searchValue.toLowerCase(Locale.ENGLISH)+"%')";
		
		list = (ArrayList) this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet resultSet,final int identity) throws SQLException {
				final ErrorVB errorVB = new ErrorVB();
				errorVB.setErrorId(resultSet.getString(ERID));
				errorVB.setErrorCode(resultSet.getString(ECODE));
				errorVB.setErrorDesc(resultSet.getString(EDESC));
				errorVB.setActive(resultSet.getString(ACTIVE));
				return errorVB;
			}
		});
		
		LogManager.logExit();
		LogManager.push("ErrorDAOImpl getSearchByErrorCode() method  Ends");
		LogManager.popRemove(); 
		return list;
	}
	
	public List getSearchByErrorDesc(final String searchValue) throws CommonBaseException
	{
		LogManager.push("ErrorDAOImpl getSearchByErrorDesc() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
		
		final String query="select ERROR_ID,ERROR_CODE,ERROR_DESC,ACTIVE from ERROR_MASTER where lower(ERROR_DESC) like ('%"+searchValue.toLowerCase(Locale.ENGLISH)+"%')";
		
		list = (ArrayList) this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet resultSet,final int identity) throws SQLException {
				final ErrorVB errorVB = new ErrorVB();
				errorVB.setErrorId(resultSet.getString(ERID));
				errorVB.setErrorCode(resultSet.getString(ECODE));
				errorVB.setErrorDesc(resultSet.getString(EDESC));
				errorVB.setActive(resultSet.getString(ACTIVE));
				return errorVB;
			}
		});
		
		LogManager.logExit();
		LogManager.push("ErrorDAOImpl getSearchByErrorDesc() method  Ends");
		LogManager.popRemove(); 
		return list;
	}
}