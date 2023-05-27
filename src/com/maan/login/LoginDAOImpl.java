package com.maan.login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.Runner;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.password.passwordEnc;

public class LoginDAOImpl extends CommonBaseDAOImpl implements LoginDAO {

	public List makeAuthendication(final LoginVB loginVB)
			throws CommonBaseException {

		LogManager.push("makeAuthendication() method");
		LogManager.logEnter();
		Object[] arg;
		final passwordEnc password = new passwordEnc();
		arg = new Object[2];
		arg[0] = loginVB.getEnteredLoginId();
		try {
			arg[1] = password.encrypt(loginVB.getEnteredPassword()).trim();
		} catch (Exception e) {
			LogManager
					.push("makeAuthendication() method CANNOT CONVERT PASSWORD");
		}
		LogManager.push("makeAuthendication() method PASSWORD IS---" + arg[1]);
		final String query = getQuery(DBConstants.LOGIN_AUTH);

		final List list = this.mytemplate.query(query, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				LoginVB bean = new LoginVB();
				bean.setUserType(rset.getString("USERTYPE"));
				bean.setUserId(rset.getString("LOGIN_ID"));
				bean.setInsCompanyId(rset.getString("INS_COMPANY_CODE"));
				bean.setStartDate(rset.getString("STARTDATE"));
				bean.setEndDate(rset.getString("ENDDATE"));
				return bean;
			}
		});
		LogManager.push("makeAuthendication() method list " + list.size());
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return list;
	}

	public boolean insertSessionInfo(final LoginVB loginVB,
			final String sessionId) throws CommonBaseException {

		Object[] arg;
		// String result;
		List list;
		arg = new Object[1];
		Map values;
		boolean loginstatus = false, insertStatus = true;
		arg[0] = loginVB.getEnteredLoginId();
		LogManager.push("insertSessionInfo() method");
		LogManager.logEnter();

		final String selquery = getQuery(DBConstants.SESSION_CHECK);

		list = this.mytemplate.queryForList(selquery, arg);
		LogManager.push("makeAuthendication() method query " + selquery
				+ "arg[0]" + arg[0] + "list.size()" + list.size());
		if (list.isEmpty()) {
			loginstatus = true;
		} else
			{
			values = (Map) list.get(0);
			
			LogManager
					.push("makeAuthendication() method query ---"
							+ values.get("START_TIME") + "--"
							+ values.get("END_TIME") + "--"
							+ values.get("MINUTES") + "--"
							+ values.get("HOURS"));
			if (values.get("END_TIME") == null) {
				if ((Integer.parseInt((String) values.get("MINUTES")) >= 30)
						|| (Integer.parseInt((String) values.get("HOURS")) > 0)) {
					loginstatus = true;
				}
			} else
				{ loginstatus = true;}
		}

		if (loginstatus) {
			final String query = getQuery(DBConstants.INSERT_SESSION_INFO);
			arg = new Object[2];
			arg[0] = sessionId;
			arg[1] = loginVB.getEnteredLoginId();
			this.mytemplate.update(query, arg);
		} else {
			insertStatus = false;
		}
		LogManager.push("makeAuthendication() method exit ");
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement

		return insertStatus;

	}

	public String updateSessionInfo(final LoginVB loginVB,
			final String sessionId) throws CommonBaseException {

		Object[] arg;
		arg = new Object[2];
		LogManager.push("updateSessionInfo() method");
		LogManager.logEnter();
		final String query = getQuery(DBConstants.UPDATE_SESSION_INFO);
		arg[0] = loginVB.getUserId();
		arg[1] = sessionId;
		
		LogManager.push("login:"+loginVB.getUserId());
		LogManager.push("sid"+sessionId);
		if (arg[0] != null) {
			this.mytemplate.update(query, arg);
		}
		String qrydel="DELETE FROM TEMP_NONMATCHED WHERE SESSION_ID='"+sessionId+"'";
		Runner.updation(qrydel);
		LogManager.push("updateSessionInfo() method exit ");
		LogManager.push("updateSessionInfo() method exit ");
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return "success";

	}
	
	public String getInvestigationUserCode(final LoginForm loginForm) throws CommonBaseException
	{
		String result="";
		Map map;
		final String query=getQuery(DBConstants.INVESTIGATE_USER_CODE);
		map=this.mytemplate.queryForMap(query,new Object[] {loginForm.getUserId()});
		result=(String)map.get("INVEST_OFFICER_CODE");
		
		return result;
	}
	
}
