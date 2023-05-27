package com.maan.menu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.login.LoginForm;

public class MenuDAOImpl extends CommonBaseDAOImpl implements MenuDAO {

	public List getMenu(final String loginId, final String userType) throws CommonBaseException {

		LogManager.push("getMenu() method Implementation ");
		LogManager.logEnter();
		List list = getAllMenu(loginId, "0", userType);
				
		if (list == null) {
			list = new ArrayList();
		}
		final MenuVB menuVB = new MenuVB();
		menuVB.setContent("LOGOUT");
		menuVB.setUrl("login.do?method=logout");
		menuVB.setList(null);

		list.add(menuVB);

		LogManager.logExit();
		LogManager.popRemove();
		return list;
	}

	public List getAdminMenu(final String loginId) throws CommonBaseException {

		LogManager.push("getMenu() method Implementation ");
		LogManager.logEnter();

		List list = getAdminAllMenu(loginId, "0");

		if (list == null) {
			list = new ArrayList();
		}

		final MenuVB menuVB = new MenuVB();
		menuVB.setContent("LOGOUT");
		menuVB.setUrl("login.do?method=logout");
		menuVB.setList(null);

		list.add(menuVB);

		LogManager.logExit();
		LogManager.popRemove();
		return list;
	}

	public List getAllMenu(final String loginId, final String idVal,
			final String userType) throws CommonBaseException {

		String query;
		final List resultList = new ArrayList();
		query = "select a.MENU_ID,a.MENU_NAME,a.MENU_URL from MENU_MASTER a, LOGIN_DETAILS b "
				+ "where a.MASTER_MENU_ID = ? and b.MENU_IDS like ('%,'||a.MENU_ID||',%') and b.LOGIN_ID=? "
				+ "and b.active='1' and a.active='1' order by a.order_No";

		final List list = this.mytemplate.queryForList(query, new Object[] { idVal,
				loginId });
		
		if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
				final Map tempMap = (Map) list.get(i);
				final MenuVB menuVB = new MenuVB();
				final String subId = String.valueOf((BigDecimal) tempMap.get("MENU_ID"));
				menuVB.setContent((String) tempMap.get("MENU_NAME"));
				menuVB.setUrl((String) tempMap.get("MENU_URL"));
				LogManager.info(menuVB.getContent() + "::\t::" + subId);
				menuVB.setList(getAllMenu(loginId, subId, userType));
				resultList.add(menuVB);
			}}
			return resultList;
		
	}

	public List getAdminAllMenu(final String loginId, final String idVal)
			throws CommonBaseException {

		String query;
		final List resultList = new ArrayList();MenuVB menuVB;
		query = "select a.MENU_ID,a.MENU_NAME,a.MENU_URL from MENU_MASTER a, LOGIN_DETAILS b "
				+ "where a.MASTER_MENU_ID = ? and b.MENU_IDS like ('%,'||a.MENU_ID||',%') and b.LOGIN_ID=? "
				+ "and b.active='1' and a.active='1' order by a.order_No";

		final List list = this.mytemplate.queryForList(query, new Object[] { idVal,
				loginId });
		menuVB = new MenuVB();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				final Map tempMap = (Map) list.get(i);
				menuVB = new MenuVB();
				final String subId = String.valueOf((BigDecimal) tempMap.get("MENU_ID")) ;
				menuVB.setContent((String) tempMap.get("MENU_NAME"));
				menuVB.setUrl((String) tempMap.get("MENU_URL"));
				menuVB.setList(getAdminAllMenu(loginId, subId));
				resultList.add(menuVB);
			}
		
		}	return resultList;
	}
	
	public HashMap getStateMaster() throws CommonBaseException{
		 LogManager.push("GetStateMaster() method Starts");
		 LogManager.logEnter();
		 
		 String query = "select state_code,state_name from STATE_MASTER where active = 'Y' order by state_name";		 
	     List stateList = this.mytemplate.queryForList(query);
	     
	     HashMap stateMap = new LinkedHashMap();
	     if (!stateList.isEmpty()) {
			for (int i = 0; i < stateList.size(); i++) {
				final Map tempMap = (Map) stateList.get(i);		
				stateMap.put(tempMap.get("STATE_CODE"),tempMap.get("STATE_NAME"));		
			}
	     }
	     LogManager.logExit();
	     LogManager.popRemove();
	     return stateMap;
	 }
	 
	public HashMap getDistrictMaster(final String stateId) throws CommonBaseException{	
		 LogManager.push("GetDistrictMaster() method Starts");
		 LogManager.logEnter();		 
		 HashMap districtMap = new LinkedHashMap();		 
		 String query = "select DISTRICT_CODE,DISTRICT_NAME from DISTRICT_MASTER where state_code = '"+stateId+"' and active = 'Y' order by district_name";		 
		 LogManager.info("District Query -->"+query);
	     List districtList = this.mytemplate.queryForList(query);	     
	     if (!districtList.isEmpty()) {
			for (int i = 0; i < districtList.size(); i++) {
				final Map tempMap = (Map) districtList.get(i);					
				districtMap.put(tempMap.get("DISTRICT_CODE"),tempMap.get("DISTRICT_NAME"));		
			}
	     }
	     LogManager.logExit();
	     LogManager.popRemove();		 
	     return districtMap;
	 }
	 
	public HashMap getStateMaster1(final String loginId) throws CommonBaseException{
		 LogManager.push("getStateMaster1() method");
		 LogManager.logEnter();
		 
		 String query = "SELECT STATE_CODE,STATE_NAME FROM STATE_MASTER SM,LOGIN_DETAILS LD WHERE SM.ACTIVE = 'Y' AND LD.STATE_CODES LIKE '%,'||SM.STATE_CODE||',%' AND LOGIN_ID=? ORDER BY STATE_NAME";		 
	     List stateList = this.mytemplate.queryForList(query,new Object[]{loginId});
	     LogManager.info("Query=>"+query+" LoginID:=>"+loginId);
	     HashMap stateMap = new LinkedHashMap();
	     if (!stateList.isEmpty()) {
			for (int i = 0; i < stateList.size(); i++) {
				final Map tempMap = (Map) stateList.get(i);		
				stateMap.put(tempMap.get("STATE_CODE"),tempMap.get("STATE_NAME"));		
			}
	     }
	     LogManager.logExit();
	     LogManager.popRemove();
	     return stateMap;
	 }
	 
	 public HashMap getDistrictMaster1(final String stateId, final String loginId) throws CommonBaseException{	
		 LogManager.push("GetDistrictMaster() method Starts");
		 LogManager.logEnter();		 
		 HashMap districtMap = new LinkedHashMap();		 
		 String query = "SELECT DISTRICT_CODE,DISTRICT_NAME from DISTRICT_MASTER DM,LOGIN_DETAILS LD WHERE DM.STATE_CODE=? AND DM.ACTIVE = 'Y' AND LOGIN_ID=? AND LD.DISTRICT_CODES LIKE '%,'||DM.DISTRICT_CODE||',%' ORDER BY DISTRICT_NAME";		 
		 LogManager.info("District Query -->"+query);
	     List districtList = this.mytemplate.queryForList(query,new Object[]{stateId,loginId});	     
	     if (!districtList.isEmpty()) {
			for (int i = 0; i < districtList.size(); i++) {
				final Map tempMap = (Map) districtList.get(i);					
				districtMap.put(tempMap.get("DISTRICT_CODE"),tempMap.get("DISTRICT_NAME"));
			}
	     }
	     LogManager.logExit();
	     LogManager.popRemove();		 
	     return districtMap;
	 }
	 
	 public String getStateName(final String stateId ) throws CommonBaseException{
		 LogManager.push("GetStateName() method Starts");
		 LogManager.logEnter();		 
		 String name = "";		 
		 String query = "select state_name from state_master where state_code =  '"+stateId+"'";		 
		 List list = this.mytemplate.queryForList(query);		 
		 if (list.isEmpty()) {
			 name = "";
		 }else {
			Map tempMap = (Map) list.get(0);
			name = (String) tempMap.get("STATE_NAME");				
		 }		 
		 LogManager.logExit();
		 LogManager.popRemove();		 
		 return name;
	 }
	 
	 public String getDistrictName(final String districtId,final String stateId) throws CommonBaseException{
		 LogManager.push("GetDistrictName() method Starts");
		 LogManager.logEnter();		 
		 String name = "";		 
		 String query = "select district_name from district_master where district_code = '" + districtId + "' and state_code='" + stateId + "'";		 
		 List list = this.mytemplate.queryForList(query);		 
		 if (list.isEmpty()) {
			 name = "";
		 }else {
			Map tempMap = (Map) list.get(0);
			name = (String) tempMap.get("DISTRICT_NAME");				
		 }
		 LogManager.logExit();
		 LogManager.popRemove();
		 return name;
	 }
	 
	
}
