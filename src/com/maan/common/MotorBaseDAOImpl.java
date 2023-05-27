/*
 * Created on Jan 16, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.maan.common;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.maan.common.dbconnection.DBConnection;

/**
 * Abstract class from which every entity/usecase-specific DAO should extend.
 * Offers a variety of database related functions like getting a connection,
 * query execution, query execution embedded with paging logic, updation etc.
 */

public class MotorBaseDAOImpl implements MotorBaseDAO {
	protected JdbcTemplate mytemplate;

	public MotorBaseDAOImpl() {
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
}