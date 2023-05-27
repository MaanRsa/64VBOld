package com.maan.common.dbconnection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.maan.common.LogManager;

public final class DBConnection {
	
	

	private static DBConnection mymanager = new DBConnection();

	private transient JdbcTemplate template;

	private transient DataSource dataSource;

	private DBConnection() {
		try {
			final InitialContext cxt = new InitialContext();
			if (cxt == null) {
				throw new Exception("Uh oh -- no context!");
			}
			
			dataSource = (DataSource) cxt.lookup("java:/comp/env/jdbc/64VB");
			LogManager.info("Testing the new Connection here ");
			if (dataSource == null) {
				throw new Exception("Data source not found!");
			}
			template = new JdbcTemplate(dataSource);
	 	} catch (NamingException e) {
			LogManager.debug(e);
		} catch (Exception e) {
			LogManager.debug(e);
		}
	}

	public static DBConnection getInstance() {
		return mymanager;
	}

	public Connection getDBConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public JdbcTemplate gettemplate() throws SQLException {
		return template;
	}
	
	public DataSource getDataSource()
    {
   	 
   	 return dataSource;
    }
}