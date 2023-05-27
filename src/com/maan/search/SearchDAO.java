package com.maan.search;

import java.util.List;
import java.util.Map;

import com.maan.common.exception.CommonBaseException;

public interface SearchDAO {
	
	public List getSearchList(SearchFormBean sbean) throws CommonBaseException;
	public List getMatchedList(SearchFormBean sbean, String sid) throws CommonBaseException;
	public Map  getBankList(SearchFormBean form) throws CommonBaseException;
	public List getNotRealisedList(SearchFormBean form) throws CommonBaseException;
	public void updateChequeDetails(List list, SearchFormBean form)throws CommonBaseException;
	public List getRealisedTransactionList(SearchFormBean form)throws CommonBaseException;
	public List getNotMatchedList(SearchFormBean sbean, String sid)throws CommonBaseException;
	public void getReceiptDetail(ReceiptDetails receipt)throws CommonBaseException;
	public List getNotRealizedList(SearchFormBean sbean)throws CommonBaseException;
	public List getBankDuplicates(String transid, String bankid)throws CommonBaseException;
	public List getReceiptDuplicates(String transid)throws CommonBaseException;
	public List getReceiptInvalids(String transid)throws CommonBaseException;
	public List getBankInvalids(String transid, String bankid)throws CommonBaseException;
	public List getReceiptPayments(String transid)throws CommonBaseException;
	public void updateReverse(SearchFormBean sbean)throws CommonBaseException;
	public void updatefields(SearchFormBean sbean)throws CommonBaseException;
	public void updateReverse2(SearchFormBean sbean)throws CommonBaseException;
	public List getReversalList(SearchFormBean form)throws CommonBaseException;
	public void updateReversalFields(SearchFormBean form)throws CommonBaseException;
	public void updateReversalDetails(List list, SearchFormBean form)throws CommonBaseException;
	public void updateReverseReversal(SearchFormBean form)throws CommonBaseException;
	public List getBankNocheqeus(String transid, String bankid)throws CommonBaseException;
	public List getReversalUpdatedList(String[] bankNo1,SearchFormBean form, String sid)throws CommonBaseException;
	public void processActuals(SearchFormBean form)throws CommonBaseException;
	public void deleteTemp(String sid)throws CommonBaseException;
	public List getBankReversals(String transid, String bankid)throws CommonBaseException;
	public List getReceiptReversals(String transid)throws CommonBaseException;
	public List getReceiptReversal(SearchFormBean sbean)throws CommonBaseException;
	public void updateSelectedData(String[] bankNo,String[] actualChequeNo,String[] actualChqueAmount,String bankData)throws CommonBaseException;
	public void updateBankReversalData(String bankNos,SearchFormBean sbean)throws CommonBaseException;
	public void updateBankActualData(String[] actualChequeNos,String[] actualchequeAmounts,String[] bankNos,SearchFormBean sForm)throws CommonBaseException;
	public List getNotMatchedActualList(String[] bankNo,SearchFormBean sbean,String sid)throws CommonBaseException;
	public List getActualMatchedList(String[] bankNos, SearchFormBean sbean,
			String id)throws CommonBaseException;
}
