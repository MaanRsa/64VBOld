package com.maan.upload2;

import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface UploadDAO extends CommonBaseDAO {

	public String processCsv(final String csvLoc,final String insCompanyId,HttpServletRequest request,UploadForm form) throws CommonBaseException;
	public List getTransactionDetails(final String transId, final String bankId) throws CommonBaseException;
	public int getProcess(final String transId,final String bankId) throws CommonBaseException;
	public Map getBankList(final UploadForm form) throws CommonBaseException;
	}
