package com.maan.reversal;

import java.util.List;

import com.maan.common.exception.CommonBaseException;

public interface ReversalDAO {
	public List getReceiptSearchList(ReversalFormBean sbean) throws CommonBaseException;
	public List getCitiSearchList(ReversalFormBean sbean) throws CommonBaseException;
	public List getHdfcSearchList(ReversalFormBean sbean) throws CommonBaseException;
	public List getHsbcSearchList(ReversalFormBean sbean) throws CommonBaseException;
	public List getReceiptReversalsList(ReversalFormBean sbean)throws CommonBaseException;
	public List getCitiReversalsList(ReversalFormBean sbean)throws CommonBaseException;
	public List getHdfcReversalsList(ReversalFormBean sbean)throws CommonBaseException;
	public List getHsbcReversalsList(ReversalFormBean sbean)throws CommonBaseException;
	public List getScbSearchList(ReversalFormBean sbean)throws CommonBaseException;
	public List getScbReversalsList(ReversalFormBean sbean) throws CommonBaseException;
	public List getAxisSearchList(ReversalFormBean sbean)throws CommonBaseException;
	public List getAxisReversalsList(ReversalFormBean sbean) throws CommonBaseException;
	public List getKotakSearchList(ReversalFormBean sbean) throws CommonBaseException;
	public List getKotakReversalsList(ReversalFormBean sbean) throws CommonBaseException;
}
