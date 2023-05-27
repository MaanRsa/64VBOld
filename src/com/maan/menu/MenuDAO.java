package com.maan.menu;

import java.util.HashMap;
import java.util.List;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;
import com.maan.login.LoginForm;

public interface MenuDAO extends CommonBaseDAO {
	 
	List getMenu(final String loginId, final String userType) throws CommonBaseException;
	List getAdminMenu(final String loginId) throws CommonBaseException;
	HashMap getStateMaster() throws CommonBaseException;
	HashMap getStateMaster1(final String loginId) throws CommonBaseException;
	HashMap getDistrictMaster(final String stateId) throws CommonBaseException;
	HashMap getDistrictMaster1(final String stateId, final String loginId) throws CommonBaseException;
	String getStateName(final String stateId) throws CommonBaseException;
	String getDistrictName(final String districtId,final String stateId) throws CommonBaseException;
	}
