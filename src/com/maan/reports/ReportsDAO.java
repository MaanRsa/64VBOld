package com.maan.reports;

import com.maan.common.exception.CommonBaseException;

public interface ReportsDAO {

	void getReportsList(ReportsFormBean bean)throws CommonBaseException;

}
