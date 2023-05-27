package com.maan.upload;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface UploadDAO extends CommonBaseDAO {

	public String processCsv(final String csvLoc,final String insCompanyId,HttpServletRequest request) throws CommonBaseException;
	public List getTransactionDetails(final String transId, final String insCompanyId) throws CommonBaseException;
	public List getDbColumnsForDownload(final String insCompanyId,final String masterTable) throws CommonBaseException;
}
