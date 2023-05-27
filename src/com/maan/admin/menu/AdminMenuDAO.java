package com.maan.admin.menu;

import java.util.List;
import java.util.Map;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface AdminMenuDAO extends CommonBaseDAO {
	 boolean insertMenu(final AdminMenuVB menuVB)
			throws CommonBaseException;

	 boolean updateMenu(final AdminMenuVB menuVB)
			throws CommonBaseException;

	 List getMenuInfo(final int idval) throws CommonBaseException;

	 Map getMenuMap(final String type, final boolean status)
			throws CommonBaseException;

	 List getAllMenu(final String type, final String idval)
			throws CommonBaseException;
}
