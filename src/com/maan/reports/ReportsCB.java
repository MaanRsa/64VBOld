package com.maan.reports;

import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;
import com.maan.search.SearchDAO;
import com.maan.search.SearchDAOImpl;

public class ReportsCB {

	public void getReportsList(ReportsFormBean bean)throws CommonBaseException {
	
		ReportsDAO rdao=(ReportsDAOImpl)CommonDaoFactory.getDAO(ReportsDAO.class.getName());
		rdao.getReportsList(bean);
	}

}
