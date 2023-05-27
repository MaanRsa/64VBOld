package com.maan.admin.usermanipulation;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.jdbc.core.RowMapper;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.menu.MenuVB;
import com.maan.password.passwordEnc;

public class UserManipulationDAOImpl extends CommonBaseDAOImpl implements
		UserManipulationDAO {

	public boolean insertUser(final UserManipulationVB creationVB) throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl insertUser() method ");
		LogManager.logEnter();

		String query;
		final passwordEnc password = new passwordEnc();
		boolean result;
		query = "INSERT INTO LOGIN_MASTER (LOGIN_ID,PASSWORD,USERTYPE,USERNAME,STATUS,LOGIN_DETAIL_ID,INS_COMPANY_CODE) "
				+ "VALUES (?,?,?,?,?,?,?)";

		Object[] arg = new Object[7];

		arg[0] = creationVB.getLoginID();
		try{
		arg[1] =password.encrypt(creationVB.getPassword()).trim(); 
			} catch (Exception e) {
				LogManager
						.push("UserManipulationDAOImpl() method CANNOT CONVERT PASSWORD");
			}
		arg[2] = creationVB.getUserType();
		arg[3] = creationVB.getUserName();
		arg[4] = creationVB.getStatus();
		arg[5] = creationVB.getId()+"";
		arg[6] = creationVB.getInsCompanyId()==null?"14":creationVB.getInsCompanyId();
		
		LogManager.push("Query for user creation "+query);
		LogManager.push("Parameters "+arg[0]+"\t"+arg[1]+"\t"+arg[2]+"\t"+arg[3]+"\t"+arg[4]+"\t"+arg[5]+"\t"+arg[6]);
        if (this.mytemplate.update(query, arg) > 0) {
			result = true;
		} else {
			result = false;
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	
	public boolean insertApproverUser(final UserManipulationVB creationVB) throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl insertApproverUser() method ");
		LogManager.logEnter();
		String query, userCode="",userType="";
		boolean result;
		userType=creationVB.getUserType()==null?"":creationVB.getUserType();
		query = "INSERT INTO APPROVER_MASTER (APPROVER_ID,APPROVER_CODE,APPROVER_NAME,STATUS,ENTRY_DATE,END_DATE,LOGIN_ID) "
				+ "VALUES (?,?,?,?,SYSDATE,SYSDATE,?)";

		if(userType.equalsIgnoreCase("approver1"))
			userCode = "A1";
		else if(userType.equalsIgnoreCase("approver2"))
			userCode = "A2";
		Object[] arg = new Object[5];
		arg[0] = creationVB.getId();
		arg[1] = userCode;
		arg[2] = creationVB.getUserName();
		arg[3] = creationVB.getStatus();
		arg[4] = creationVB.getLoginID();

		if (this.mytemplate.update(query, arg) > 0) {
			result = true;
		} else {
			result = false;
		}
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public boolean updateUser(final UserManipulationVB creationVB)
			throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl updateUser() method ");
		LogManager.logEnter();

		String query;
		boolean result;
		query = "update LOGIN_MASTER set USERTYPE = ?,USERNAME = ?,STATUS = ?,INS_COMPANY_CODE=? where LOGIN_DETAIL_ID = ?";

		Object[] arg = new Object[5];
		arg[0] = creationVB.getUserType();
		arg[1] = creationVB.getUserName();
		arg[2] = creationVB.getStatus();
		arg[3] = creationVB.getInsCompanyId();
		arg[4] = creationVB.getId()+"";

		if (this.mytemplate.update(query, arg) > 0) {
			result = true;
		} else {
			result = false;
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	/*public boolean removeUser(final UserManipulationVB creationVB)
	throws CommonBaseException {

	LogManager.push("UserManipulationDAOImpl removeUser() method ");
	LogManager.logEnter();
	
	String query;
	boolean result;
	query = "delete from LOGIN_MASTER where login_id = ? ";
	Object[] arg = new Object[1];
	arg[0] = creationVB.getId()+"";
	
	if (this.mytemplate.update(query, arg) > 0) {
		result = true;
	} else {
		result = false;
	}
	
	LogManager.logExit();
	LogManager.popRemove(); // Should be the last statement
	return result;
	}*/

	public boolean allocateMenuToUser(final UserManipulationVB creationVB)
			throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl allocateMenuToUser() method ");
		LogManager.logEnter();

		String query, query2;
		boolean result;
		Object[] arg;
		int count;

		query2 = "select count(*) from LOGIN_DETAILS where LOGIN_ID=?";

		count = this.mytemplate.queryForInt(query2, new Object[] {creationVB.getLoginID() });

		LogManager.info("Count ::" + count);

		if (count == 0) {
			query = "INSERT INTO LOGIN_DETAILS (LOGIN_DETAIL_ID,LOGIN_ID,MENU_IDS,ACTIVE) VALUES "
					+ "(?,?,?,?)";
			arg = new Object[4];
			arg[0] = creationVB.getId()+"";
			arg[1] = creationVB.getLoginID();
			arg[2] = creationVB.getMenuIDStr();
			arg[3] = creationVB.getStatus();
		

		} else {
			query = "update LOGIN_DETAILS set MENU_IDS = ?,ACTIVE = ? where LOGIN_ID=?";
			arg = new Object[3];
			arg[0] = creationVB.getMenuIDStr();
			arg[1] = creationVB.getStatus();
			arg[2] = creationVB.getLoginID();
		}
		LogManager.info("query ::" + query);
		if (this.mytemplate.update(query, arg) > 0) {
			result = true;
		} else {
			result = false;
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
             
	public boolean allocateStateToUser(final UserManipulationVB creationVB)
			throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl allocateStateToUser method ");
		LogManager.logEnter();

		String query, query2;
		boolean result;
		Object[] arg;
		int count;

		query2 = "select count(*) from LOGIN_DETAILS where LOGIN_ID=?";

		count = this.mytemplate.queryForInt(query2, new Object[] { creationVB
				.getLoginID() });

		LogManager.info("Count ::" + count);

		if (count == 0) {
			query = "INSERT INTO LOGIN_DETAILS (LOGIN_DETAIL_ID,LOGIN_ID,STATE_CODES,ACTIVE) VALUES "
					+ "(?,?,?,?)";
			arg = new Object[4];
			arg[0] = creationVB.getId() + "";
			arg[1] = creationVB.getLoginID();
			arg[2] = creationVB.getStateCodes();
			arg[3] = creationVB.getStatus();

		} else {
			query = "update LOGIN_DETAILS set STATE_CODES = ?,ACTIVE = ? where LOGIN_ID=?";
			arg = new Object[3];
			arg[0] = creationVB.getStateCodes();
			arg[1] = creationVB.getStatus();
			arg[2] = creationVB.getLoginID();
		}
		LogManager.info("query ::" + query);
		if (this.mytemplate.update(query, arg) > 0) {
			result = true;
		} else {
			result = false;
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	public boolean allocateDistrictToUser(final UserManipulationVB creationVB)
			throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl allocateDistrictToUser method ");
		LogManager.logEnter();
	

		String query, query2;
		boolean result;
		Object[] arg;
		int count;

		query2 = "SELECT COUNT(*) FROM STATE_MASTER SM,LOGIN_DETAILS LD WHERE LD.STATE_CODES LIKE '%,'||SM.STATE_CODE||',%' AND LOGIN_ID=?";

		count = this.mytemplate.queryForInt(query2, new Object[] { creationVB
				.getLoginID() });

		LogManager.info("Count ::" + count);

		if (count == 0) {
			query = "INSERT INTO LOGIN_DETAILS (LOGIN_DETAIL_ID,LOGIN_ID,DISTRICT_CODES,ACTIVE) VALUES "
					+ "(?,?,?,?)";
			arg = new Object[4];
			arg[0] = creationVB.getId() + "";
			arg[1] = creationVB.getLoginID();
			arg[2] = creationVB.getDistrictCodes();
			arg[3] = creationVB.getStatus();

		} else {
			query = "update LOGIN_DETAILS set DISTRICT_CODES = ?,ACTIVE = ? where LOGIN_ID=?";
			arg = new Object[3];
			arg[0] = creationVB.getDistrictCodes();
			arg[1] = creationVB.getStatus();
			arg[2] = creationVB.getLoginID();
		}
		LogManager.info("query ::" + query);
		if (this.mytemplate.update(query, arg) > 0) {
			result = true;
		} else {
			result = false;
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}


	public List getLoginInfo(final int idVal) throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl getLoingInfo() method ");
		LogManager.logEnter();

		String query;
		List list;
		Object[] arg;

		query = "select LOGIN_ID,USERTYPE,USERNAME,STATUS,LOGIN_DETAIL_ID,INS_COMPANY_CODE "
				+ "from LOGIN_MASTER where LOGIN_DETAIL_ID = ?";

		arg = new Object[1];
		arg[0] = Integer.toString(idVal);

		list = this.mytemplate.query(query, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int idVal) throws SQLException {
				final UserManipulationVB bean = new UserManipulationVB();
				bean.setLoginID(rset.getString("LOGIN_ID"));
				bean.setUserType(rset.getString("USERTYPE"));
				bean.setUserName(rset.getString("USERNAME"));
				bean.setStatus(rset.getString("STATUS"));
				bean.setId(Integer.parseInt(rset.getString("LOGIN_DETAIL_ID")));
				bean.setInsCompanyId(rset.getString("INS_COMPANY_CODE"));
				return bean;
			}
		});

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return list;
	}

	public List getUserList(final String searchFor, final String searchOn,final String userType)
			throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl getUserList() method ");
		LogManager.logEnter();

		String query;
		List list;

		final StringBuffer search = new StringBuffer(32);

		if (searchFor != null && searchOn != null && !searchFor.equals("0")
				&& searchOn.length() != 0) {
			search.append(" where ").append( searchFor ).append(" like '%").append(searchOn).append("%'");
		}
		
		if(userType.equalsIgnoreCase("admin") )
		{
			if(search.length()>0)
			{
				search.append(" and usertype!='sadmin'");
			}else
			{
				search.append(" where usertype!='sadmin'");
			}
		}

		query = "select LOGIN_ID,USERTYPE,USERNAME,STATUS,LOGIN_DETAIL_ID "
				+ "from LOGIN_MASTER" + search;

		list = this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int idVal) throws SQLException {
				final UserManipulationVB bean = new UserManipulationVB();
				bean.setLoginID(rset.getString("LOGIN_ID"));
				bean.setUserType(rset.getString("USERTYPE"));
				bean.setUserName(rset.getString("USERNAME"));
				bean.setStatus(rset.getString("STATUS"));
				bean.setId(Integer.parseInt(rset.getString("LOGIN_DETAIL_ID")));
				return bean;
			}
		});

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return list;
	}

	public Map getLoginIDMap() throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl getLoginIDMap() method ");
		LogManager.logEnter();

		final Map map = new TreeMap();
		String query;

		query = "select LOGIN_DETAIL_ID,LOGIN_ID from LOGIN_MASTER";
	
		final List list = this.mytemplate.queryForList(query);

		for (int i = 0; i < list.size(); i++) {
			final Map tempMap = (Map) list.get(i);
			map.put(String.valueOf((BigDecimal) tempMap.get("LOGIN_DETAIL_ID")), tempMap
					.get("LOGIN_ID"));
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return map;
	}
	
	public String getLoginPassDate(final String loginid) throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl getLoginIDMap() method ");
		LogManager.logEnter();

		
		String query,value="";
		
		query = "select PASSDATE from LOGIN_MASTER WHERE LOGIN_ID='"+loginid+"'";
		final List list = this.mytemplate.queryForList(query);

		for (int i = 0; i < list.size(); i++) {
			final Map tempMap = (Map) list.get(i);
			value=tempMap.get("PASSDATE")==null?"":((String)tempMap.get("PASSDATE").toString()) ;
		}
           
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return value;
	}

	public Map getMenuMap(final String userType) throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl getMenuMap() method ");
		LogManager.logEnter();

		final Map map = new TreeMap();
		String query;

		query = "select MENU_ID,MENU_NAME from MENU_MASTER where ACTIVE='1' and type=?";
		final List list = this.mytemplate.queryForList(query,
				new Object[] { userType });

		for (int i = 0; i < list.size(); i++) {
			final Map tempMap = (Map) list.get(i);
			map.put(String.valueOf((BigDecimal) tempMap.get("MENU_ID")), tempMap
					.get("MENU_NAME"));
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return map;
	}

	public boolean isLoginOccur(final String loginID) throws CommonBaseException {
		int idVal;
		boolean result;

		idVal = this.mytemplate.queryForInt(
				"select count(*) from LOGIN_MASTER where LOGIN_ID =?",
				new Object[] { loginID });

		if (idVal == 0) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	public boolean isOldPwdWright(final String loginID, String pwd)
			throws CommonBaseException {
		int idVal;
		boolean result;
		LogManager.push("isOldPwdWright getLoginIDMap() method ");
		LogManager.logEnter();
		final passwordEnc password = new passwordEnc();
		
		try {
			pwd= password.encrypt(pwd).trim();
		} catch (Exception e) {
			LogManager
					.push("makeAuthendication() method CANNOT CONVERT PASSWORD");
		}
		idVal = this.mytemplate
				.queryForInt(
						"select count(*) from LOGIN_MASTER where LOGIN_ID =? and password = ?",
						new Object[] { loginID, pwd });

		if (idVal == 0) {
			result = true;
		} else {
			result = false;
		}
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public boolean changePwd(String loginID, String pwd,final String loginName,final String loginSts)
			throws CommonBaseException {
		int idVal;
		boolean result;String tempStr="";
		LogManager.push("isOldPwdWright getLoginIDMap() method "+loginName);
		LogManager.logEnter();
		final passwordEnc password = new passwordEnc();
		try {
			pwd= password.encrypt(pwd).trim();
		} catch (Exception e) {
			LogManager
					.push("makeAuthendication() method CANNOT CONVERT PASSWORD");
		}
		
		if(loginID==null || (loginID.length()==0))
		{
			final int lid=this.mytemplate.queryForInt("select LOGIN_DETAIL_ID from LOGIN_MASTER where login_id='"+loginName+"'");
			loginID=Integer.toString(lid);
		}
		
		if(loginSts!=null && (loginSts.length()>0))
		{
			tempStr=" ,passdate=sysdate ";
		}else
		{
			tempStr=" ,passdate=null ";
		}
		

		idVal = this.mytemplate
				.update(
						"update LOGIN_MASTER set LPASS5= LPASS4,LPASS4= LPASS3,LPASS3= LPASS2, LPASS2= LPASS1, LPASS1 = Password, "
								+ "password = ? "+tempStr+" where LOGIN_DETAIL_ID=?",
						new Object[] { pwd, loginID });

		if (idVal == 0) {
			result = false;
		} else {
			result = true;
		}
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public UserManipulationVB getSelectedMenu(final String loginID) throws CommonBaseException {

		final UserManipulationVB manipulationVB = new UserManipulationVB();

		LogManager.push("getSelectedMenu getLoginIDMap() method ");
		LogManager.logEnter();

		final List list = this.mytemplate
				.queryForList(
						"select menu_ids,ACTIVE from LOGIN_DETAILS where LOGIN_ID =?",
						new Object[] { loginID});
		if (!list.isEmpty()) {
			final Map tempMap = (Map) list.get(0);
			manipulationVB.setMenuIDStr((String) tempMap.get("MENU_IDS"));
			manipulationVB.setStatus((String) tempMap.get("ACTIVE"));
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return manipulationVB;
	}

	public List getStateList() throws CommonBaseException {

		String query;
		final List resultList = new ArrayList();
		query = "select STATE_CODE,STATE_NAME from STATE_MASTER where ACTIVE='Y' order by STATE_NAME";
		LogManager.info("query=>"+query);
		final List list = this.mytemplate.queryForList(query);
	
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				final Map tempMap = (Map) list.get(i);
				final UserManipulationVB manipulationVB = new UserManipulationVB();
				manipulationVB.setStateCode((String)tempMap.get("STATE_CODE"));
				manipulationVB.setStateName((String)tempMap.get("STATE_NAME"));
				resultList.add(manipulationVB);
			}
		}
		
		return resultList;
	}
	public List getDistrictList(final UserManipulationVB creationVB) throws CommonBaseException {

		String query;
		final List resultList = new ArrayList();
		query = "SELECT DISTRICT_CODE,DISTRICT_NAME FROM DISTRICT_MASTER WHERE ACTIVE='Y' AND STATE_CODE IN (SELECT STATE_CODE FROM STATE_MASTER SM,LOGIN_DETAILS LD WHERE LD.STATE_CODES LIKE '%,'||SM.STATE_CODE||',%' AND LOGIN_ID=?) ORDER BY DISTRICT_NAME";
		LogManager.info("query=>"+query);

				
		final List list = this.mytemplate.queryForList(query ,new Object[] { creationVB
				.getLoginID()});
	
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				final Map tempMap = (Map) list.get(i);
				final UserManipulationVB manipulationVB = new UserManipulationVB();
				manipulationVB.setDistrictCode((String)tempMap.get("DISTRICT_CODE"));
				manipulationVB.setDistrictName((String)tempMap.get("DISTRICT_NAME"));
				resultList.add(manipulationVB);
			}
		}
		
		return resultList;
	}
	public UserManipulationVB getSelectedState(final String loginID) throws CommonBaseException {

		final UserManipulationVB manipulationVB = new UserManipulationVB();

		LogManager.push("getSelectedState method ");
		LogManager.logEnter();

		final List list = this.mytemplate
				.queryForList(
						"select STATE_CODES,ACTIVE from LOGIN_DETAILS where LOGIN_ID =?",
						new Object[] { loginID});
	
		if (!list.isEmpty()) {
			final Map tempMap = (Map) list.get(0);
		    manipulationVB.setStateCodes((String)tempMap.get("STATE_CODES"));
			manipulationVB.setStatus((String) tempMap.get("ACTIVE"));
		}
	
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return manipulationVB;
	}
	public UserManipulationVB getSelectedDistrict(final String loginID) throws CommonBaseException {

		final UserManipulationVB manipulationVB = new UserManipulationVB();
		manipulationVB.setLoginID(loginID);

		LogManager.push("getSelectedState method ");
		LogManager.logEnter();
		

		final List list = this.mytemplate
				.queryForList(
						"select DISTRICT_CODES,ACTIVE from LOGIN_DETAILS where LOGIN_ID =?",
						new Object[] { loginID});
		
		if (!list.isEmpty()) {
			final Map tempMap = (Map) list.get(0);
		    manipulationVB.setDistrictCodes((String)tempMap.get("DISTRICT_CODES"));
			manipulationVB.setStatus((String) tempMap.get("ACTIVE"));
		}
	

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return manipulationVB;
	}
	public Map getInsuranceCompany(final UserManipulationVB creationVB) throws CommonBaseException {

		LogManager.push("UserManipulationDAOImpl getInsuranceCompany() method ");
		LogManager.logEnter();
		final Map result = new HashMap();
		String query;
		query = "select INS_COMPANY_CODE,INS_COMPANY_NAME from INSURANCE_COMPANY_MASTER ORDER BY INS_COMPANY_NAME";
		final Map mp = this.mytemplate.queryForMap(query);
		if(mp!=null && mp.size()>0){
			for(int i=0;i<mp.size();i++){
				result.put(mp.get("INS_COMPANY_CODE"),mp.get("INS_COMPANY_NAME"));
			}
		}
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
		
	}
	
		
	
}
