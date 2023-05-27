package com.maan.upload3;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface UploadDAO extends CommonBaseDAO {

	public String processCsv(final String csvLoc,final String insCompanyId,HttpServletRequest request) throws CommonBaseException;
	public List getTransactionDetails(final String transId, int i) throws CommonBaseException;
	public void updateReceiptsDetail(final String transId,String realizeStatus) throws CommonBaseException;
	public List getReceiptsDetail(final String transId) throws CommonBaseException;
	public List getReceiptsDetailNotMatched(final String transId) throws CommonBaseException;
	public List getDbColumnsForDownload(final String insCompanyId,final String masterTable) throws CommonBaseException;
	public void updatePolicyDetails(String transId) throws CommonBaseException;
	public List getPolicies(String transId, String status) throws CommonBaseException;
	
}
