package com.maan.admin.menu;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.jdbc.core.RowMapper;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.use.CommonCB;
import com.maan.menu.MenuVB;

public class AdminMenuDAOImpl extends CommonBaseDAOImpl implements AdminMenuDAO {

	public boolean insertMenu(final AdminMenuVB menuVB)
			throws CommonBaseException {

		LogManager.push("AdminMenuDAOImpl insertMenu() method ");
		LogManager.logEnter();

		String query;
		boolean result = false;
		final CommonCB commonCB = new CommonCB();
		query = "INSERT INTO MENU_MASTER (MENU_ID,MENU_NAME,MENU_URL,MASTER_MENU_ID,"
				+ "ORDER_NO,REMARKS,START_DATE,END_DATE,TYPE,ACTIVE) VALUES (?,?,?,?,?,?,?,?,?,?)";

		Object[] arg = new Object[10];

		arg[0] = menuVB.getId()+"";
		arg[1] = menuVB.getName().toUpperCase();
		arg[2] = menuVB.getUrl();
		arg[3] = menuVB.getSuperMenu();
		arg[4] = menuVB.getOrderby();
		arg[5] = menuVB.getRemark();
		arg[6] = commonCB.stringToDate1(menuVB.getStartDate());
		arg[7] = commonCB.stringToDate1(menuVB.getEndDate());
		arg[8] = menuVB.getType();
		arg[9] = menuVB.getStatus();

		try {

			if (this.mytemplate.update(query, arg) > 0) {
				result = true;
			} else {
				result = false;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public boolean updateMenu(final AdminMenuVB menuVB)
			throws CommonBaseException {

		LogManager.push("AdminMenuDAOImpl updateMenu() method ");
		LogManager.logEnter();

		String query;
		boolean result;
		final CommonCB commonCB = new CommonCB();
		query = "update MENU_MASTER set MENU_NAME = ?, MENU_URL = ?, MASTER_MENU_ID = ?,"
				+ "ORDER_NO = ?, REMARKS = ?, START_DATE = ?, END_DATE = ?, TYPE = ?,ACTIVE = ? where MENU_ID = ?";

		Object[] arg = new Object[10];
		arg[0] = menuVB.getName().toUpperCase();
		arg[1] = menuVB.getUrl();
		arg[2] = menuVB.getSuperMenu();
		arg[3] = menuVB.getOrderby();
		arg[4] = menuVB.getRemark();
		arg[5] = commonCB.stringToDate(menuVB.getStartDate());
		arg[6] = commonCB.stringToDate(menuVB.getEndDate());
		arg[7] = menuVB.getType();
		arg[8] = menuVB.getStatus();
		arg[9] = menuVB.getId()+"";

		if (this.mytemplate.update(query, arg) > 0) {
			result = true;
		} else {
			result = false;
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public List getMenuInfo(final int identity) throws CommonBaseException {

		LogManager.push("AdminMenuDAOImpl getMenuInfo() method ");
		LogManager.logEnter();

		String query;
		List list;
		Object[] arg;

		query = "select MENU_ID,MENU_NAME,MENU_URL,MASTER_MENU_ID,ORDER_NO,REMARKS,to_char(START_DATE, 'DD-MM-YYYY') as START_DATE"
				+ ",to_char(END_DATE, 'DD-MM-YYYY') as END_DATE,TYPE,ACTIVE from MENU_MASTER where menu_id = ?";

		arg = new Object[1];
		arg[0] = identity+"";

		list = this.mytemplate.query(query, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int rowid) throws SQLException {
				final AdminMenuVB bean = new AdminMenuVB();
				bean.setId(rset.getInt("MENU_ID"));
				bean.setName(rset.getString("MENU_NAME"));
				bean.setUrl(rset.getString("MENU_URL"));
				bean.setSuperMenu(rset.getString("MASTER_MENU_ID"));
				bean.setOrderby(rset.getString("ORDER_NO"));
				bean.setRemark(rset.getString("REMARKS"));
				bean.setStartDate(rset.getString("START_DATE"));
				bean.setEndDate(rset.getString("END_DATE"));
				bean.setType(rset.getString("TYPE"));
				bean.setStatus(rset.getString("ACTIVE"));
				return bean;
			}
		});

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return list;
	}

	public Map getMenuMap( String type, final boolean status)
			throws CommonBaseException {

		LogManager.push("AdminMenuDAOImpl getMenuMap() method ");
		LogManager.logEnter();

		final Map map = new TreeMap();
		String query;
		String isActive = "";

		if (status) {// true Active value only
			isActive = " and ACTIVE='1'";
		}
		if(type.equalsIgnoreCase("sadmin"))
		{
			type="admin";
		}
		query = "select MENU_ID,MENU_NAME from MENU_MASTER where type = ? and MASTER_MENU_ID='0'"
				+ isActive;
		final List list = this.mytemplate.queryForList(query, new Object[] { type });

		for (int i = 0; i < list.size(); i++) {
			final Map tempMap = (Map) list.get(i);
			map.put(String.valueOf((BigDecimal) tempMap.get("MENU_ID")) , tempMap
					.get("MENU_NAME"));
		}

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return map;
	}

	public List getAllMenu(String type, final String idval)
			throws CommonBaseException {

		String query;
		final List resultList = new ArrayList();
		query = "select MENU_ID,MENU_NAME,MENU_URL from MENU_MASTER where ACTIVE = '1' and "
				+ "type = ? and MASTER_MENU_ID = ? order by order_No";
		LogManager.info("query=>"+query);
		LogManager.info("Type=>"+type);
		if(type.equalsIgnoreCase("sadmin"))
		{
			type="admin";
		}
		
		final List list = this.mytemplate.queryForList(query,
				new Object[] { type, idval });
	
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				final Map tempMap = (Map) list.get(i);
				final MenuVB menuVB = new MenuVB();
				final String subID = String.valueOf((BigDecimal) tempMap.get("MENU_ID") );
				menuVB.setId(subID);
				menuVB.setContent((String) tempMap.get("MENU_NAME"));
				menuVB.setUrl((String) tempMap.get("MENU_URL"));
				menuVB.setList(getAllMenu(type, subID));
				resultList.add(menuVB);
			}
		}
		
		return resultList;
	}
}
