
package com.maan.common.base;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.maan.common.LogManager;
import com.maan.common.ResourceLocator;
import com.maan.common.SQLExecution;
import com.maan.common.dbconnection.DBConnection;


public class CommonBaseDAOImpl implements CommonBaseDAO {
	protected JdbcTemplate mytemplate;

	public CommonBaseDAOImpl() {
		try {
			mytemplate = DBConnection.getInstance().gettemplate();
		} catch (SQLException e) {
			LogManager.error(e);
		}
	}

	public JdbcTemplate getMytemplate() {
		return mytemplate;
	}

	public void setMytemplate(final JdbcTemplate mytemplate) {
		this.mytemplate = mytemplate;
	}
	
	public String getQuery(String key){
		String query;
			query=ResourceLocator.getInstance().getDBBundle().getString(key);
		return query;
		
	}
	public SQLExecution getProcedureInstance()
	{
		return SQLExecution.getInstance();
		
	}
}