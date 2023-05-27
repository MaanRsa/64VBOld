package com.maan.user.search;

import java.util.List;
import java.util.Map;

import com.maan.common.exception.CommonBaseException;

public interface SearchDAO {
	public List getSearchList(SearchFormBean sbean) throws CommonBaseException;
	public List getPolicySearchList(SearchFormBean sbean) throws CommonBaseException;
	//public List getBankSearchList(SearchFormBean sbean) throws CommonBaseException;
	//public List getReceiptSearchList(SearchFormBean sbean) throws CommonBaseException;
	public Map getBankList(SearchFormBean form) throws CommonBaseException;
	public void getReceiptDetail(ReceiptDetails receipt)throws CommonBaseException;
	public List doManualRealization(String searchOption, String searchValue);
	public void updateChecked(String receiptNo, String checkStatus)throws CommonBaseException;
	public List getManulRealizedList(String searchOption, String searchValue)throws CommonBaseException;
	public List getPendingList(SearchFormBean sbean)throws CommonBaseException;
	
}
