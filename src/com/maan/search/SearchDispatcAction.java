package com.maan.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.maan.common.LogManager;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;
import com.maan.excelupload.UploadDAOImpl;
import com.maan.upload.UploadCB;
import com.maan.upload.UploadForm;
 
public class SearchDispatcAction extends DispatchAction {

	private String forward;
	SearchCB scb=new SearchCB();
	private String validateResult="";
	String reversalPage="";
	String bankPage="";
	String actualChequeNoPage="";
	String actualChequeAmountPage="";
	private ActionErrors errors = new ActionErrors();
	final com.maan.common.Validation validate = new com.maan.common.Validation();
	public ActionForward receiptDetails(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Receipt Detail Start");
		ActionForward forward = null;
		final SearchCB sCB = new SearchCB();
		String receipt=request.getParameter("receiptNo");
		LogManager.push("Receipt param:"+receipt);
		request.setAttribute("ReceiptValue", receipt);
		forward = mapping.findForward("receiptdetail"); 		
		return forward;
	}
	public ActionForward search(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Search Start");
		ActionForward forward = null;
		final SearchCB sCB = new SearchCB();
		SearchFormBean sForm=new SearchFormBean();
		sForm.setChequeNo("");
		sForm.setChequeAmount("");
		sForm.setSearchIn("Both");
		sForm.reset(mapping, request);
		final Map bankList = sCB.getBankList(sForm);
		
		sForm.setPartToShow("Search");
		LogManager.push(sForm.getPartToShow());
		sForm.reset(mapping, request);
		//LogManager.push("BankId: >>>>>>>>>"+sForm.getBankId());
		//LogManager.push("cheque: >>>>>>>>>"+sForm.getChequeNo());
		request.setAttribute("BankList", bankList);
		request.setAttribute("PartToShow","Search");
        forward = mapping.findForward("searchpage"); 		
		return forward;
	}
	public ActionForward goreceiptdetail(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("go receipt detail");
		ActionForward forward = null;
		final SearchCB sCB = new SearchCB();
		ReceiptDetails rec=( ReceiptDetails)form;
		String receiptval=(String)request.getAttribute("ReceiptValue");
		LogManager.push(receiptval);
		rec.setReceiptNo(receiptval);
		if(!("".equalsIgnoreCase(rec.getChequeNo()) && "Bank".equalsIgnoreCase(rec.getFrom()))){
			sCB.getReceiptDetail(rec);
			LogManager.push("TRansaction no:"+rec.getTransactionNo());
			LogManager.push("receipt no:"+rec.getReceiptNo());
			LogManager.push("cgeque no:"+rec.getChequeNo());
			forward = mapping.findForward("receiptdetail");
		}else{
			request.setAttribute("bid", request.getParameter("bid"));
			forward = mapping.findForward("cashTransaction");
		}
		return forward;
	}
	public ActionForward submitSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			String bankstatus=sbean.getSearchIn();
			sbean.setFromDate(request.getParameter("fromDate")==null?"":request.getParameter("fromDate"));
			LogManager.push("Bank status searched in:"+bankstatus);
			Map bankList=null;
			try {
				bankList = scb.getBankList(sbean);
			} catch (CommonBaseException e1) {
				
				e1.printStackTrace();
			}
			request.setAttribute("BankList", bankList);
			LogManager.push("Inside searchsubmit");
			List lists = new ArrayList();
			List listReversal = new ArrayList();
			//errors = validateForm(sbean);
			
	        validateResult=scb.validateSearchInfo(sbean,validateResult);
			
	          if("".equalsIgnoreCase(validateResult))
	            {
	            	try{
	            		LogManager.push("Search Option: "+sbean.getBankId()+" Search Value: "+sbean.getChequeAmount());
	            		lists=scb.getSearchList(sbean);
	            		listReversal = scb.getReceiptReversal(sbean);
	            		LogManager.push("LIST retrieved:"+lists);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	if(sbean.getRealised().equalsIgnoreCase("yes"))
	            			{
	            	request.setAttribute("PartToShow","SearchResult");
	            	request.setAttribute("PartToShow1",bankstatus);
	    	        
	            			}
	            	else
	            	{
	            		sbean.setPartToShow("NotRealizedList");
	            		request.setAttribute("PartToShow","NotRealizedList");
	            		request.setAttribute("PartToShow1",bankstatus);
		    	        
	            	}
	    	        
	            }
	            else
	            {
	            	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {validateResult}));
	            	request.setAttribute("PartToShow","Search");
	            	sbean.setPartToShow("Search");
	        		
	            }
	          
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			    if(errors.isEmpty()){
	            List bankReversalList=new ArrayList();
	            List nonMatchedReceipt=new ArrayList();
	            List nonMatchedBank=new ArrayList();
	            try {
	            	SearchFormBean countBean=(SearchFormBean)sbean.clone(); 	  	
	            		            		            	
	            	countBean.setSearch("first");
	            	countBean.setSearchIn("Bank");
                    countBean.setRealised("no");	            	               
					bankReversalList=scb.getReversalList(countBean);
					countBean.setSearchIn("Receipt");
					nonMatchedReceipt=scb.getNotRealizedList(countBean);
					countBean.setSearchIn("Bank");
					nonMatchedBank=scb.getNotRealizedList(countBean);
					System.out.println("Size of bank reversal list is "+bankReversalList.size());
					System.out.println("Size of non matched receipt list is "+nonMatchedReceipt.size());
					System.out.println("size of non matched bank list is "+nonMatchedBank.size());
				} catch (CommonBaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*List nonMatchedReceipt=new ArrayList();
	            try {
	            	SearchFormBean nonMatchedReceiptBean=(SearchFormBean)sbean.clone(); 	  	
	            		            		            	
	            	nonMatchedReceiptBean.setSearch("first");
	            	nonMatchedReceiptBean.setSearchIn("Receipt");
	            	nonMatchedReceiptBean.setRealised("no");	            	               
					bankReversalList=scb.getNotRealizedList(nonMatchedReceiptBean);
					
					System.out.println("Size of non matched receipt "+nonMatchedReceipt.size());
				} catch (CommonBaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
	            sbean.setBankReversalSize(bankReversalList.size());
	            sbean.setNonMathcedReceiptSize(nonMatchedReceipt.size());
	            sbean.setNonMatchedBankSize(nonMatchedBank.size());
	                
			validateResult="";
			//sbean.setSearchResult(lists);
			LogManager.push("REALISE:-->"+sbean.getRealised());
			request.setAttribute("searchResult", lists);
			request.setAttribute("reversalReceipt", listReversal);
			    }
			    validateResult="";
	return mapping.findForward(forward);
	}
	public ActionForward realisedTransaction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LogManager.push("Inside realised transaction");
		SearchFormBean sbean;
		System.out.print("search parameter"+request.getParameter("search"));
		String bankStatus=request.getParameter("searchIn");
		String bankId=request.getParameter("bankId");
		String transNo=request.getParameter("transactionNo");
		String realised=request.getParameter("realised");
		sbean = new SearchFormBean();
		sbean.setTransactionNo(transNo);
		sbean.setBankId(bankId);
		sbean.setSearchIn(bankStatus);
		sbean.setRealised(realised);
		sbean.setChequeNo("");
		sbean.setActualChequeAmount("");
		sbean.setChequeAmount("");
		sbean.setSearchFor("similar");
		LogManager.push("Bank name :"+ sbean.getBankName());
		
		LogManager.push("Transaction no from sbean"+sbean.getTransactionNo());
		
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
						
			Map bankList=null;
			try {
				bankList = scb.getBankList(sbean);
			} catch (CommonBaseException e1) {
				
				e1.printStackTrace();
			}
			request.setAttribute("BankList", bankList);
			LogManager.push("Bank status :"+ bankStatus);
			//LogManager.push("Transaction No :"+ transNo);
			
			
			
			List lists = new ArrayList();
			      	try{
	            		//LogManager.push("Search Option: "+sbean.getBankId()+" Search Value: "+sbean.getChequeAmount());
	            		lists=scb.getSearchList(sbean);
	            		LogManager.push("LIST retrieved:"+lists);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - realisedTransaction(): "+e);}
	           	if(sbean.getRealised().equalsIgnoreCase("yes"))
	            			{
	           		sbean.setPartToShow("RealisedTransactionResult");
	           		request.setAttribute("PartToShow1",bankStatus);
	            	request.setAttribute("PartToShow","RealisedTransactionResult");
	            			}
	            	else
	            	{
	            		sbean.setPartToShow("NotRealisedTransactionResult");
		        		request.setAttribute("PartToShow","NotRealizedTransactionList");
	            		request.setAttribute("PartToShow1",bankStatus);
		    	        
	            	}
	    	   
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            LogManager.push("Bank name :"+ sbean.getBankName());
				request.setAttribute("Bank", sbean.getBankName());
	   		request.setAttribute("searchResult", lists);
	   		request.setAttribute("transactionNo", transNo);
	   		
	return mapping.findForward(forward);
	}
/*	public ActionForward submitChequeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CommonBaseException  {
		LogManager.push("SearchDispatchaction submitChequeDetails() Method - Enter");			
		ActionForward forward = null;			
		final HttpSession session = request.getSession(true);
		final List list;		
		SearchFormBean sForm = (SearchFormBean)form;
		String amount[]=request.getParameterValues("actualChequeAmount");
		String numbers[]=request.getParameterValues("actualChequeNo");
		String bank[]=request.getParameterValues("bankNo1");
		String reverse[]=request.getParameterValues("reversal");
		String bankData=sForm.getBankData();
		for(int i=0;i<amount.length-1;i++)
		{
			if(amount[i].length()>0||numbers[i].length()>0)
			{
				actualChequeAmountPage=actualChequeAmountPage+","+amount[i];
				actualChequeNoPage=actualChequeNoPage+","+numbers[i];
				bankPage=bankPage+","+bank[i];
			}
		}
		
		for(int i=0;i<reverse.length-1;i++)
		{
			if(reverse[i].equalsIgnoreCase("R"))
			{
		    reversalPage=reversalPage+","+reverse[i];
		    bankPage=bankPage+","+bank[i];
			}
		}
				
		final String[] actualChequeAmount=actualChequeAmountPage.split(",");
		
		String[] bankNo=bankPage.split(",");
		final String[] bankNo1 = bankPage.split(",");
		final String[] actualChequeNo = actualChequeNoPage.split(",");
		final String[] reversal = reversalPage.split(",");
		LogManager.push("length:"+actualChequeAmount.length);
		String search=request.getParameter("search");
		LogManager.push("search:"+search);
		if(search.equalsIgnoreCase("second"))
		 {
		for(int j=0;j<actualChequeAmount.length-1;j++)
		LogManager.push(actualChequeAmount[j]+"<--ChequeAmount"+"BankNo-->"+bankNo[j]);
		
		for(int j=0;j<reversal.length;j++)
			LogManager.push(reversal[j]+"<--reversal"+"BankNo-->"+bankNo1[j]);
			
		 }
		errors.clear();
		
		SearchCB sCB=new SearchCB();
		sForm.setRealised("no");
		try{
			list=sCB.getNotRealizedList(sForm);
			sForm.setNonmatchedlist(list);
			
			if(actualChequeAmount==null){
				//LogManager.push("not exists cheque amt");
				sForm.setAmount(new HashMap());
				
			}
			if(actualChequeNo==null){
				//LogManager.push("not exists cheque amt");
				sForm.setChequeNos(new HashMap());
				
			}
			if(reversal==null){
				//LogManager.push("not exists cheque amt");
				sForm.setReversalMap(new HashMap());
				
			}
			HashMap amounts = new HashMap();
			HashMap cheques = new HashMap();
			HashMap reverseMap = new HashMap();
			LogManager.push("Search inside submit cheque:::"+search);
			if(search.equalsIgnoreCase("second"))
			 {
				LogManager.push("Inside second");
			if(actualChequeAmount!=null && actualChequeAmount.length-1>0){				
				for(int i=0;i<actualChequeAmount.length;i++){
					if(amounts.containsKey(bankNo[i])){
						amounts.remove(bankNo1[i]);						
						amounts.put(bankNo1[i], actualChequeAmount[i]);
					}else{
						amounts.put(bankNo1[i], actualChequeAmount[i]);						
					}
					LogManager.push("hi"+i+bankNo1[i]);
				}
			}
			
			if(actualChequeNo!=null && actualChequeNo.length-1>0){				
				for(int i=0;i<actualChequeAmount.length;i++){
					if(cheques.containsKey(bankNo1[i])){
						cheques.remove(bankNo1[i]);						
						cheques.put(bankNo1[i], actualChequeNo[i]);
					}else{
						cheques.put(bankNo1[i], actualChequeNo[i]);						
					}
					LogManager.push("hi"+i+bankNo1[i]);
				}
			}
			if(reversal.length>0){				
				for(int i=0;i<reversal.length;i++){
					if(reverseMap.containsKey(bankNo1[i])){
						reverseMap.remove(bankNo1[i]);						
						reverseMap.put(bankNo1[i], reversal[i]);
					}else{
						reverseMap.put(bankNo1[i], reversal[i]);						
					}
					LogManager.push("reversal"+i+bankNo1[i]);
				}
			}
			//for(int j=0;j<actualChequeAmount.length;j++)
			//	LogManager.push("J-->"+amounts.get(bankNo[j]));
						
			sForm.setAmount(amounts);
			sForm.setChequeNos(cheques);
			sForm.setReversalMap(reverseMap);
			for(int i=0;i<list.size();i++){
				final SearchVB svb = (SearchVB)list.get(i);
				//LogManager.push("BankNOs.."+svb.getBankNo2());
				//if((amounts.get(bankNo[i])).toString()!=null)
				//LogManager.push("entered"+(amounts.get(bankNo[i])).toString());
				
				if(amounts.containsKey(svb.getBankNo1())){
					LogManager.push("entered"+(amounts.get(svb.getBankNo1())).toString());
					svb.setActualChequeAmount((amounts.get(svb.getBankNo1())).toString());
					
				}
				if(cheques.containsKey(svb.getBankNo1())){
					LogManager.push("entered"+(cheques.get(svb.getBankNo1())).toString());
					svb.setActualChequeNo((cheques.get(svb.getBankNo1())).toString());
					
				}
				if(reverseMap.containsKey(svb.getBankNo1())){
					LogManager.push("entered"+(reverseMap.get(svb.getBankNo1())).toString());
					svb.setReversal((reverseMap.get(svb.getBankNo1())).toString());
					
				}
				list.set(i, svb);
				//LogManager.push("svb"+svb.getActualChequeAmount());
						
			}
			
			sCB.updateReverse(sForm);
			sCB.updateChequeDetails(list,sForm);
			//sCB.updateSelectedData(bankNo,actualChequeNo,actualChequeAmount,bankData);
			sCB.updateReverse(sForm);
					
		}	
			
		}
			
		catch(Exception e){
			LogManager.fatal(e);
			e.printStackTrace();
			throw new CommonBaseException(e,CommonExceptionConstants.OTHER_ERROR);
		}finally{
			this.actualChequeNoPage="";
			this.actualChequeAmountPage="";
			this.bankPage="";
			this.reversalPage="";
			LogManager.info("SearchDispatchaction submitChequeDetails() Method - Exit"); // Should be the last statement
		}
		if(search.equalsIgnoreCase("first"))
		{
		sForm.setPartToShow("NotRealizedList");
    	request.setAttribute("PartToShow","NotRealizedList");
		request.setAttribute("searchResult",list);
		request.setAttribute("PartToShow1",sForm.getSearchIn());
	       
		forward = mapping.findForward("searchpage");
		}
		else
		{
			LogManager.push("submit cheque details:");
			String bankstatus=sForm.getSearchIn();
			Map bankList=null;
			try {
				bankList = scb.getBankList(sForm);
			} catch (CommonBaseException e1) {
				
				e1.printStackTrace();
			}
			request.setAttribute("BankList", bankList);
			LogManager.push("Inside searchsubmit");
			List lists = new ArrayList();
			List lists2 = new ArrayList();
			List lists3 = new ArrayList();
			//errors = validateForm(sbean);
			   
        	try{
        		LogManager.push("Search Option: "+sForm.getBankId()+" Search Value: "+sForm.getChequeAmount());
        		scb.processActuals(sForm);
        		lists=scb.getMatchedList(sForm,session.getId());
        		//lists2=scb.getReversalUpdatedList(sForm,session.getId());
        		lists3=scb.getNotMatchedList(sForm,session.getId());
        		LogManager.push("LIST retrieved:"+lists);
        		sCB.updateReverse(sForm);
        	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}        	
        	if(lists.size()>0)
        	{
	        	sForm.setPartToShow("MatchedResult");
	        	request.setAttribute("PartToShow","MatchedResult");
        	}
        	else
        	{
        		if(lists3.size()>0)
        		{
	        		sForm.setPartToShow("NonMatchedResult");
	        		request.setAttribute("PartToShow","NonMatchedResult");
        		}
        		else
        		{
        			sForm.setPartToShow("ReversalUpdatedResult");
            		request.setAttribute("PartToShow","ReversalUpdatedResult");            			
        		}
        	}        	            	
            saveMessages(request,errors);
            saveErrors(request,errors);
            //sbean.setFromJsp("");
			validateResult="";
			request.setAttribute("searchResult", lists);
		    request.setAttribute("searchResult2", lists2);
		    request.setAttribute("nonmatchedResult", lists3);
		    request.setAttribute("nonmatchedlist", sForm.getNonmatchedlist());
		   // HttpSession session1 = request.getSession(true);
		    session.removeAttribute("nonmatchedlist");
		    //scb.deleteTemp();
			session.setAttribute("nonmatchedlist", sForm.getNonmatchedlist());
			forward = mapping.findForward("searchpage");
		}
		return forward;
	}*/
	
	public ActionForward submitReversalDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CommonBaseException  {
		LogManager.push("SearchDispatchaction submitReversalDetails() Method - Enter");			
		ActionForward forward = null;			
		final HttpSession session = request.getSession(false);
		List list;		
		SearchFormBean sForm = (SearchFormBean)form;
		//final String[] actualChequeAmount = request.getParameterValues("actualChequeAmount");
		final String[] bankNo = request.getParameterValues("bankNo2");
		final String[] bankNo1 = request.getParameterValues("bankNo1");
		//final String[] actualChequeNo = request.getParameterValues("actualChequeNo");
		final String[] reversal = request.getParameterValues("reversal");
		//LogManager.push("length:"+actualChequeAmount.length);
		String search=request.getParameter("search");
		LogManager.push("search:"+search);
		if(search.equalsIgnoreCase("second"))
		 {
			for(int j=0;j<reversal.length;j++)
			LogManager.push(reversal[j]+"<--reversal"+"BankNo-->"+bankNo1[j]);
		 }
		errors.clear();
		SearchCB sCB=new SearchCB();
		sForm.setRealised("no");
		try{
			list=sCB.getReversalList(sForm);
			//LogManager.push("Submit Retrived list:"+list);
			if(reversal==null){
				//LogManager.push("not exists cheque amt");
				sForm.setReversalMap(new HashMap());
			}
			HashMap amounts = new HashMap();
			HashMap cheques = new HashMap();
			HashMap reverseMap = new HashMap();
			LogManager.push("Search inside submit cheque:::"+search);
			if(search.equalsIgnoreCase("second"))
			 {
			if(reversal.length>0){				
				for(int i=0;i<reversal.length;i++){
					if(reverseMap.containsKey(bankNo1[i])){
						reverseMap.remove(bankNo1[i]);						
						reverseMap.put(bankNo1[i], reversal[i]);
					}else{
						reverseMap.put(bankNo1[i], reversal[i]);						
					}
					LogManager.push("reversal"+i+bankNo1[i]);
				}
			}
			//for(int j=0;j<actualChequeAmount.length;j++)
			//	LogManager.push("J-->"+amounts.get(bankNo[j]));
			sForm.setReversalMap(reverseMap);
			for(int i=0;i<list.size();i++){
				final SearchVB svb = (SearchVB)list.get(i);
				//LogManager.push("BankNOs.."+svb.getBankNo2());
				//if((amounts.get(bankNo[i])).toString()!=null)
				//LogManager.push("entered"+(amounts.get(bankNo[i])).toString());
				if(reverseMap.containsKey(svb.getBankNo1())){
					LogManager.push("entered"+(reverseMap.get(svb.getBankNo1())).toString());
					svb.setReversal((reverseMap.get(svb.getBankNo1())).toString());
				}
				list.set(i, svb);
				//LogManager.push("svb"+svb.getActualChequeAmount());
			}
			sCB.updateReverseReversal(sForm);
			sCB.updateReversalDetails(list,sForm);
			sCB.updateReverseReversal(sForm);
		}	
		}
		catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e,CommonExceptionConstants.OTHER_ERROR);
		}finally{
			LogManager.info("SearchDispatchaction submitReversalDetails() Method - Exit"); // Should be the last statement
		}
		if(search.equalsIgnoreCase("first"))
		{
		sForm.setPartToShow("ReversalList");
    	request.setAttribute("PartToShow","ReversalSuccess");
		request.setAttribute("searchResult",list);
		request.setAttribute("PartToShow1",sForm.getSearchIn());
	       
		forward = mapping.findForward("searchpage");
		}
		else
		{
			LogManager.push("submit cheque details:");
			String bankstatus=sForm.getSearchIn();
			Map bankList=null;
			try {
				bankList = scb.getBankList(sForm);
			} catch (CommonBaseException e1) {
				
				e1.printStackTrace();
			}
			request.setAttribute("BankList", bankList);
			LogManager.push("Inside searchsubmit");
			//List lists = new ArrayList();
			//List lists2 = new ArrayList();
			//errors = validateForm(sbean);
				      
        	/*try{
        		//LogManager.push("Search Option: "+sForm.getBankId()+" Search Value: "+sForm.getChequeAmount());
        		//lists=scb.getMatchedList(sForm);
        		//lists2=scb.getNotMatchedList(sForm);
        		//LogManager.push("LIST retrieved:"+lists);
        		//sCB.updateReverse(sForm);
        	}catch(Exception e){LogManager.push("Exception In submitReversalDetails - searchInit(): "+e);}
        	*/
        
    		
            saveMessages(request,errors);
            saveErrors(request,errors);
            //sbean.setFromJsp("");
		
			
            //list=sCB.getReversalList(sForm);
			
		}
		request.setAttribute("searchResult", list);
		request.setAttribute("PartToShow","ReversalSuccess");
		forward = mapping.findForward("searchpage");
		return forward;
	}
	/*
	public ActionForward transactionSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CommonBaseException  {
		LogManager.info("SearchDispatchaction notrealised() Method - Enter");			
		ActionForward forward = null;			
		final HttpSession session = request.getSession(false);
		final List list;		
		SearchFormBean sbean = new SearchFormBean();
		final String[] actualChequeAmount = request.getParameterValues("actualChequeAmount");
		final String[] bankNo = request.getParameterValues("bankNo2");
		final String[] bankNo1 = request.getParameterValues("bankNo1");
		final String[] actualChequeNo = request.getParameterValues("actualChequeNo");
		String bankStatus=request.getParameter("searchIn");
		String bankId=request.getParameter("bankId");
		String transNo=request.getParameter("transactionNo");
		String realised=request.getParameter("realised");
		LogManager.push("Parameter bankId"+bankId);
		
		sbean.setTransactionNo(transNo);
		sbean.setBankId(bankId);
		sbean.setSearchIn(bankStatus);
		sbean.setRealised(realised);
		sbean.setChequeNo("");
		sbean.setChequeAmount("");
		sbean.setActualChequeAmount("");
		sbean.setSearchFor("similar");
		LogManager.push("Transaction no from sbean"+sbean.getTransactionNo());
		LogManager.push("TBank id "+sbean.getBankId());
		
		String search=request.getParameter("search");
		if(search==null)
			search="first";
		LogManager.push("search:"+search);
		if(search.equalsIgnoreCase("second"))	
		 {
		for(int j=0;j<actualChequeAmount.length;j++)
		LogManager.push(actualChequeAmount[j]+"<--ChequeAmount"+"BankNo-->"+bankNo[j]);
		 }
		errors.clear();
		
		SearchCB sCB=new SearchCB();
		sbean.setRealised("no");
		try{
			list=sCB.getSearchList(sbean);
			//LogManager.push("Submit Retrived list:"+list);
			if(actualChequeAmount==null){
				//LogManager.push("not exists cheque amt");
				sbean.setAmount(new HashMap());
				
			}
			if(actualChequeNo==null){
				//LogManager.push("not exists cheque amt");
				sbean.setChequeNos(new HashMap());
				
			}
			HashMap amounts = new HashMap();
			HashMap cheques = new HashMap();
			
			if(search.equalsIgnoreCase("second"))
			 {
				
			if(actualChequeAmount!=null && actualChequeAmount.length>0){				
				for(int i=0;i<actualChequeAmount.length;i++){
					if(amounts.containsKey(bankNo[i])){
						amounts.remove(bankNo[i]);						
						amounts.put(bankNo[i], actualChequeAmount[i]);
					}else{
						amounts.put(bankNo[i], actualChequeAmount[i]);						
					}
					LogManager.push("hi"+i+bankNo[i]);
				}
			}
			
			if(actualChequeNo!=null && actualChequeNo.length>0){				
				for(int i=0;i<actualChequeAmount.length;i++){
					if(cheques.containsKey(bankNo1[i])){
						cheques.remove(bankNo1[i]);						
						cheques.put(bankNo1[i], actualChequeNo[i]);
					}else{
						cheques.put(bankNo1[i], actualChequeNo[i]);						
					}
					LogManager.push("hi"+i+bankNo1[i]);
				}
			}
			//for(int j=0;j<actualChequeAmount.length;j++)
			//	LogManager.push("J-->"+amounts.get(bankNo[j]));
						
			sbean.setAmount(amounts);
			sbean.setChequeNos(cheques);
			for(int i=0;i<list.size();i++){
				final SearchVB svb = (SearchVB)list.get(i);
				//LogManager.push("BankNOs.."+svb.getBankNo2());
				//if((amounts.get(bankNo[i])).toString()!=null)
				//LogManager.push("entered"+(amounts.get(bankNo[i])).toString());
				
				if(amounts.containsKey(svb.getBankNo2())){
					LogManager.push("entered no"+(amounts.get(svb.getBankNo2())).toString());
					svb.setActualChequeAmount((amounts.get(svb.getBankNo2())).toString());
					
				}
				if(cheques.containsKey(svb.getBankNo1())){
					LogManager.push("entered amt"+(cheques.get(svb.getBankNo1())).toString());
					svb.setActualChequeNo((cheques.get(svb.getBankNo1())).toString());
					
				}
				list.set(i, svb);
				//LogManager.push("svb"+svb.getActualChequeAmount());
						
			}
			sCB.updateChequeDetails(list,sbean);
			LogManager.push("TBank id 2 "+sbean.getBankId());
			
		}	
		}
						
		catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e,CommonExceptionConstants.OTHER_ERROR);
		}finally{
			LogManager.info("SearchDispatchaction notrealised() Method - Exit"); // Should be the last statement
		}
		if(search.equalsIgnoreCase("first"))
		{
		sbean.setPartToShow("NotRealisedTransactionResult");
        	
		request.setAttribute("PartToShow","NotRealizedTransactionList");
		request.setAttribute("searchResult",list);
		request.setAttribute("PartToShow1",bankStatus);
		request.setAttribute("transactionNo",transNo);
		
		forward = mapping.findForward("searchpage");
		}
		else
		{
			String bankstatus=sbean.getSearchIn();
			Map bankList=null;
			try {
				bankList = scb.getBankList(sbean);
			} catch (CommonBaseException e1) {
				
				e1.printStackTrace();
			}
			request.setAttribute("BankList", bankList);
			LogManager.push("Inside not realised transaction submit");
			List lists = new ArrayList();
			List lists2 = new ArrayList();
			//errors = validateForm(sbean);
				      
        	try{
        		LogManager.push("Search Option: "+sbean.getBankId()+" Search Value: "+sbean.getChequeAmount());
        		lists=scb.getMatchedList(sbean);
        		lists2=scb.getNotMatchedList(sbean);
        		LogManager.push("LIST retrieved:"+lists);
        	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
        	
        	sbean.setPartToShow("MatchedResult");
        	request.setAttribute("PartToShow","MatchedResult");
        	            	
            saveMessages(request,errors);
            saveErrors(request,errors);
            //sbean.setFromJsp("");
		    validateResult="";
			request.setAttribute("searchResult", lists);
			request.setAttribute("searchResult2", lists2);
			forward = mapping.findForward("searchpage");
			
		}
		request.setAttribute("transactionNo",transNo);
		
		return forward;
	}*/
	public ActionForward notRealizedList(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws CommonBaseException  {
		LogManager.info("SearchDispatchAction notRealizedList() Method - Enter");			
		LogManager.push("SearchDispatchAction notRealizedList()  Method - Enter");
		ActionForward forward = null;			
		final List list;		
		HttpSession session=request.getSession();
				
		SearchFormBean sForm = (SearchFormBean)form;
		String bankData=sForm.getBankData()==null?"":sForm.getBankData();
		LogManager.push("sForm.getSearchIn()==>"+sForm.getSearchIn());
		SearchCB sCB=new SearchCB();
    	String[] bankNo1;
		if(bankData.equalsIgnoreCase("reversal"))
		{
		    bankNo1=request.getParameterValues("bankNo2");
		    
		}
		else
		{
			bankNo1=request.getParameterValues("bankNo1");
		}
		final String[] actualChequeNo = request.getParameterValues("actualChequeNo");
		final String[] actualChequeAmount = request.getParameterValues("actualChequeAmount");
		final String[] reversal = request.getParameterValues("reversal");
		/*for(int i=0;i<actualChequeNo.length-1;i++)
		{
			if(actualChequeNo[i].length()>0)
		
		}
		for(int i=0;i<actualChequeAmount.length-1;i++)
		{
			if(actualChequeAmount[i].length()>0)
			
		}*/
		for(int i=0;i<actualChequeAmount.length-1;i++)
		{
			if(actualChequeAmount[i].length()>0||actualChequeNo[i].length()>0)
			{
				actualChequeAmountPage=actualChequeAmountPage+","+actualChequeAmount[i];
				actualChequeNoPage=actualChequeNoPage+","+actualChequeNo[i];
				bankPage=bankPage+","+bankNo1[i];
			}
		}
		
		for(int i=0;i<reversal.length-1;i++)
		{
			if(reversal[i].equalsIgnoreCase("R"))
			{
		    reversalPage=reversalPage+","+reversal[i];
		    bankPage=bankPage+","+bankNo1[i];
			}
		}
		/*for(int i=0;i<bankNo1.length-1;i++)
		{
			
		}*/
		
		LogManager.push("reversal page "+reversalPage);
		LogManager.push("bank page "+bankPage);
		//final String[] bankNo2 = request.getParameterValues("bankNo2");
		//request.getParameter("");
		String reqmethod=request.getParameter("method");
		String reqPage=request.getParameter("d-446779-p");
		LogManager.push("request.method"+request.getParameter("method"));
		
		LogManager.push("request.getParameter(d-446779-p)"+request.getParameter("d-446779-p"));
		errors.clear();
		//sForm.setInsCompanyId(loginForm.getInsCompanyId());
		errors = validateForm(sForm,actualChequeNo,actualChequeAmount);
		for(int i=0;i<actualChequeAmount.length;i++){
		LogManager.push("actualChequeAmount:-->"+i+":"+actualChequeAmount[i]);
		LogManager.push("actualChequeNo:-->"+i+":"+actualChequeNo[i]);
		}
		if(reversal!=null){
		for(int j=0;j<reversal.length;j++){
			LogManager.push(reversal[j]+"<--reversal"+"BankNo-->"+bankNo1[j]);
		}
		}
		try{
			if(request.getParameter("search").equalsIgnoreCase("first")){
			sCB.updateFields(sForm);
			}
			list=sCB.getNotRealizedList(sForm);
			ArrayList newList=new ArrayList(list);
			
			if(bankData.equalsIgnoreCase("reversal"))
			{
				if(newList.size()>0)
				{
					for(int i=0;i<newList.size();i++)
					{
					SearchVB svb=(SearchVB)newList.get(i);
					//LogManager.push("cheqne NO: "+svb.getChequeNo()+"\t Actual cheque amount: "+svb.getActualChequeAmount());
					String actualcheque=svb.getActualChequeAmount()==null?"":svb.getActualChequeAmount();
					String actualChqNo=svb.getActualChequeNo()==null?"":svb.getActualChequeNo();
					if(actualcheque.length()>0||actualChqNo.length()>0)
					{
						list.remove(svb);
						
					}
					
					}
				}
			}
			
			
			if(actualChequeAmount==null){
				sForm.setAmount(new HashMap());
				//sForm.setTotAmount("0");
			}
			if(actualChequeNo==null){
				sForm.setChequeNos(new HashMap());
				//sForm.setTotAmount("0");
			}
			if(errors.isEmpty()){
									
			HashMap amounts = sForm.getAmount();
			
			if(actualChequeAmount!=null && actualChequeAmount.length>0){				
				for(int i=0;i<actualChequeAmount.length;i++){
					if(amounts.containsKey(bankNo1[i])){
						amounts.remove(bankNo1[i]);						
						amounts.put(bankNo1[i], actualChequeAmount[i]);
					}else{
						amounts.put(bankNo1[i], actualChequeAmount[i]);						
					}
				}
			}
			sForm.setAmount(amounts);
			
			HashMap reverseMap = sForm.getReversalMap();
			if(reversal!=null ){				
				for(int i=0;i<reversal.length;i++){
					if(reverseMap.containsKey(bankNo1[i])){
						//reverseMap.remove(bankNo1[i]);						
						reverseMap.put(bankNo1[i], reversal[i]);
					}else{
						reverseMap.put(bankNo1[i], reversal[i]);						
					}
				}
			}
			sForm.setReversalMap(reverseMap);
			HashMap chequeNos = sForm.getChequeNos();
			if(actualChequeNo!=null && actualChequeNo.length>0){				
				for(int i=0;i<actualChequeNo.length;i++){
					if(chequeNos.containsKey(bankNo1[i])){
						chequeNos.remove(bankNo1[i]);						
						chequeNos.put(bankNo1[i], actualChequeNo[i]);
					}else{
						chequeNos.put(bankNo1[i], actualChequeNo[i]);						
					}
				}
			}
			sForm.setChequeNos(chequeNos);
			
			if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				final SearchVB svb = (SearchVB)list.get(i);
				if(amounts.containsKey(svb.getBankNo1())){
					svb.setActualChequeAmount((amounts.get(svb.getBankNo1())).toString());
					list.set(i, svb);
				}
				if(chequeNos.containsKey(svb.getBankNo1())){
					svb.setActualChequeNo((chequeNos.get(svb.getBankNo1())).toString());
					list.set(i, svb);
				}
				if(reverseMap.containsKey(svb.getBankNo1())){
					svb.setReversal((reverseMap.get(svb.getBankNo1())).toString());
					list.set(i, svb);
				}
				if(svb.getActualChequeAmount()!=null && (svb.getActualChequeAmount().trim()).length()>0 
						&& "Invalid".equalsIgnoreCase(validate.validInteger(svb.getActualChequeAmount()))){
					//errors.add("searchFormBean", new ActionMessage("search.update.chequeno.invalid", new Object[] {i+1}));
					errors.add("searchFormBean", new ActionError("search.update.chequeno.invalid"));			
					
				}
				if(svb.getActualChequeNo()!=null && (svb.getActualChequeNo().trim()).length()>0 
						&& "Invalid".equalsIgnoreCase(validate.validInteger(svb.getActualChequeNo()))){
					errors.add("searchFormBean", new ActionError("search.update.chequeamt.invalid"));			
					//errors.add("searchFormBean", new ActionMessage("search.update.chequeamt.invalid", new Object[] {i+1}));
					}
			}
			
			
			request.setAttribute("PartToShow1",sForm.getSearchIn());
			request.setAttribute("PartToShowBank", sForm.getSearchIn()+bankData);
			
			}
			else
			{
			request.setAttribute("PartToShow1","Receipt");
			}
			//sCB.updateChequeDetails(list,sForm);
			
			sCB.updateReverse2(sForm);
			request.setAttribute("searchResult", list);
			request.setAttribute("PartToShow","NotRealizedList");
			sForm.setPartToShow("NotRealizedList");
			request.setAttribute("Valid","yes");
			forward = mapping.findForward("searchpage");	
			
			}
			else
			{
				request.setAttribute("searchResult", list);
				request.setAttribute("PartToShow","NotRealizedList");
				request.setAttribute("PartToShow1",sForm.getSearchIn());
				request.setAttribute("Valid","no");
				saveErrors(request,errors);
				saveMessages(request, errors);
				forward = mapping.findForward("searchpage");	
				
			}
		}catch(Exception e){
			LogManager.fatal(e);
			e.printStackTrace();
			throw new CommonBaseException(e,CommonExceptionConstants.OTHER_ERROR);
		}finally{
			LogManager.info("SearchDispatchAction notRealizedList() Method - Exit"); // Should be the last statement
		}
		
	
		return forward;
	}
	public ActionForward reversalList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CommonBaseException  {
		LogManager.info("SearchDispatchAction reversalList() Method - Enter");			
		LogManager.push("SearchDispatchAction reversalList()  Method - Enter");
		ActionForward forward = null;			
		final List list;		
		SearchFormBean sForm = (SearchFormBean)form;
		
		LogManager.push("sForm.getSearchIn()==>"+sForm.getSearchIn());
		SearchCB sCB=new SearchCB();
		final String[] bankNo1 = request.getParameterValues("bankNo1");
		
		final String[] reversal = request.getParameterValues("reversal");
		//request.getParameter("");
		String reqmethod=request.getParameter("method");
		String reqPage=request.getParameter("d-446779-p");
		LogManager.push("request.method"+request.getParameter("method"));
		
		LogManager.push("request.getParameter(d-446779-p)"+request.getParameter("d-446779-p"));
		errors.clear();
		//sForm.setInsCompanyId(loginForm.getInsCompanyId());
		
		if(reversal!=null){
		for(int j=0;j<reversal.length;j++){
			LogManager.push(reversal[j]+"<--reversal"+"BankNo-->"+bankNo1[j]);
		}
		}
		try{
			if(request.getParameter("search").equalsIgnoreCase("first")){
			sCB.updateReversalFields(sForm);
			}
			list=sCB.getReversalList(sForm);
			
			if(errors.isEmpty()){
			HashMap reverseMap = sForm.getReversalMap();
			if(reversal!=null ){				
				for(int i=0;i<reversal.length;i++){
					if(reverseMap.containsKey(bankNo1[i])){
						reverseMap.remove(bankNo1[i]);						
						reverseMap.put(bankNo1[i], reversal[i]);
					}else{
						reverseMap.put(bankNo1[i], reversal[i]);						
					}
				}
			}
			sForm.setReversalMap(reverseMap);
			for(int i=0;i<list.size();i++){
				final SearchVB svb = (SearchVB)list.get(i);
				if(reverseMap.containsKey(svb.getBankNo1())){
					svb.setReversal((reverseMap.get(svb.getBankNo1())).toString());
					list.set(i, svb);
				}
			}
			
			sCB.updateReversalDetails(list,sForm);
			sCB.updateReverse2(sForm);
			request.setAttribute("searchResult", list);
			request.setAttribute("PartToShow","ReversalList");
			if(list.size()>0){
			request.setAttribute("PartToShow1",sForm.getSearchIn());
			}
			else
			{
			request.setAttribute("PartToShow1","Receipt");
				
			}
			sForm.setPartToShow("ReversalList");
			request.setAttribute("Valid","yes");
			forward = mapping.findForward("searchpage");	
			
			}
			else
			{
				request.setAttribute("searchResult", list);
				request.setAttribute("PartToShow","ReversalList");
				request.setAttribute("PartToShow1",sForm.getSearchIn());
				request.setAttribute("Valid","no");
				saveErrors(request,errors);
				saveMessages(request, errors);
				forward = mapping.findForward("searchpage");	
				
			}
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e,CommonExceptionConstants.OTHER_ERROR);
		}finally{
			LogManager.info("SearchDispatchAction reversalList() Method - Exit"); // Should be the last statement
		}
		
	
		return forward;
	}
	public ActionForward notRealizedTransactionList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CommonBaseException  {
		LogManager.info("SearchDispatchAction notRealizedTransactionList() Method - Enter");			
		LogManager.push("SearchDispatchAction notRealizedTransactionList()  Method - Enter");
		ActionForward forward = null;			
		final List list;		
		SearchFormBean sForm = (SearchFormBean)form;
		String bankStatus=request.getParameter("searchIn");
		String bankId=request.getParameter("bankId");
		String transNo=request.getParameter("transactionNo");
		String realised=request.getParameter("realised");
		sForm.setTransactionNo(transNo);
		sForm.setBankId(bankId);
		sForm.setSearchIn(bankStatus);
		sForm.setRealised(realised);
		sForm.setChequeNo("");
		sForm.setChequeAmount("");
		
		sForm.setSearchFor("similar");
		LogManager.push("Transaction no from sForm"+sForm.getTransactionNo());
		LogManager.push("TBank id "+sForm.getBankId());
		
		SearchCB sCB=new SearchCB();
		final String[] actualChequeAmount = request.getParameterValues("actualChequeAmount");
		final String[] bankNo1 = request.getParameterValues("bankNo1");
		final String[] actualChequeNo = request.getParameterValues("actualChequeNo");
		final String[] bankNo2 = request.getParameterValues("bankNo2");
		
		errors.clear();
		//sForm.setInsCompanyId(loginForm.getInsCompanyId());
		
		if(actualChequeAmount!=null)
		{
			
			for(int i=0;i<actualChequeAmount.length;i++){
			
			LogManager.push("actualChequeAmount:-->"+i+":"+actualChequeAmount[i]);
		LogManager.push("actualChequeNo:-->"+i+":"+actualChequeNo[i]);
		}
			}
		try{
			list=sCB.getNotRealizedList(sForm);
			if(actualChequeAmount==null){
				sForm.setAmount(new HashMap());
				//sForm.setTotAmount("0");
			}
			if(actualChequeNo==null){
				sForm.setChequeNos(new HashMap());
				//sForm.setTotAmount("0");
			}
			if(actualChequeAmount!=null)
			{
			HashMap amounts = sForm.getAmount();
			if(actualChequeAmount!=null && actualChequeAmount.length>0){				
				for(int i=0;i<actualChequeAmount.length;i++){
					if(amounts.containsKey(bankNo1[i])){
						amounts.remove(bankNo1[i]);						
						amounts.put(bankNo1[i], actualChequeAmount[i]);
					}else{
						amounts.put(bankNo1[i], actualChequeAmount[i]);						
					}
				}
			}
			sForm.setAmount(amounts);
			HashMap chequeNos = sForm.getChequeNos();
			if(actualChequeNo!=null && actualChequeNo.length>0){				
				for(int i=0;i<actualChequeNo.length;i++){
					if(chequeNos.containsKey(bankNo2[i])){
						chequeNos.remove(bankNo2[i]);						
						chequeNos.put(bankNo2[i], actualChequeNo[i]);
					}else{
						chequeNos.put(bankNo2[i], actualChequeNo[i]);						
					}
				}
			}
			sForm.setChequeNos(chequeNos);
			
			
			for(int i=0;i<list.size();i++){
				final SearchVB svb = (SearchVB)list.get(i);
				if(amounts.containsKey(svb.getBankNo1())){
					svb.setActualChequeAmount((amounts.get(svb.getBankNo1())).toString());
					list.set(i, svb);
				}
				if(chequeNos.containsKey(svb.getBankNo2())){
					svb.setActualChequeNo((chequeNos.get(svb.getBankNo2())).toString());
					list.set(i, svb);
				}
				
			}
			}
			//if(sForm.getSearchIn().equals("Bank"))
			//sCB.updateChequeDetails(list,sForm);
			request.setAttribute("searchResult", list);
			LogManager.push("LIST retrieved:"+list.size());
        	
			request.setAttribute("PartToShow","NotRealizedTransactionList");
			request.setAttribute("PartToShow1",sForm.getSearchIn());
			sForm.setPartToShow("NotRealizedList");
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e,CommonExceptionConstants.OTHER_ERROR);
		}finally{
			LogManager.info("SearchDispatchAction notRealizedList() Method - Exit"); // Should be the last statement
		}
		forward = mapping.findForward("searchpage");			
		return forward;
	}
	public ActionForward getDuplicates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			LogManager.push("Inside getDuplicates");
			List list = new ArrayList();
			String bankid=request.getParameter("bankId");
			String transid=request.getParameter("transactionNo");
						
	        try{
	             		LogManager.push("Search bank id: "+bankid+" Search trans id: "+transid);
	            		if(!bankid.equalsIgnoreCase(""))
	            		{
	            			LogManager.push("Bank Search fro");
	             		list=scb.getBankDuplicates(transid,bankid);
	             		String bankname="HDFC BANK";
	             		if(bankid.equalsIgnoreCase("CIT"))
	             		{
	             			bankname="CITI BANK";
	             		}
	             		if(bankid.equalsIgnoreCase("SCB"))
	             		{
	             			bankname="SCB BANK";
	             		}
	             		if(bankid.equalsIgnoreCase("AXB"))
	             		{
	             			bankname="AXIS BANK";
	             		}
	             		if(bankid.equalsIgnoreCase("HSB"))
	             		{
	             			bankname="HSBC BANK";
	             		}
	             		if(bankid.equalsIgnoreCase("KOT"))
	             		{
	             			bankname="KOTAK BANK";
	             		}
	             		request.setAttribute("bankName", bankname);
	             		request.setAttribute("PartToShow","BankDuplicatesResult");
		            	
	            		}
	            		else{
	             			list=scb.getReceiptDuplicates(transid);
	            			request.setAttribute("PartToShow","ReceiptDuplicatesResult");
	    	          	}
	            	   LogManager.push("Duplicates LIST retrieved size:"+list);
	            		}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	        	validateResult="";
				request.setAttribute("searchResult", list);
	        	return mapping.findForward(forward);
	}
	public ActionForward getInvalids(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			LogManager.push("Inside getInvalids");
			List list = new ArrayList();
			String bankid=request.getParameter("bankId"), bankname = "";
			String transid=request.getParameter("transactionNo");
	        try{
         		LogManager.push("Search bank id: "+bankid+" Search trans id: "+transid);
        		if(!bankid.equalsIgnoreCase(""))
        		{
        			LogManager.push("Bank Search fro");
         		list=scb.getBankInvalids(transid,bankid);
         		if(bankid.equalsIgnoreCase("CIT"))
         		{
         			bankname="CITI BANK";
         			request.setAttribute("PartToShow","CitiBankInvalidsResult");
         		} else if(bankid.equalsIgnoreCase("SCB"))
         		{
         			bankname="SCB BANK";
         			request.setAttribute("PartToShow","SCBBankInvalidsResult");
         		} else if(bankid.equalsIgnoreCase("AXB"))
         		{
         			bankname="AXIS BANK";
         			request.setAttribute("PartToShow","AXBBankInvalidsResult");
         		} else if(bankid.equalsIgnoreCase("HDB"))
         		{
         			bankname="HDFC BANK";
         			request.setAttribute("PartToShow","HDFCBankInvalidsResult");
         		}else if(bankid.equalsIgnoreCase("KOT"))
         		{
         			bankname="KOTAK BANK";
         			request.setAttribute("PartToShow","KOTBankInvalidsResult");
         		}
         		
         		else if(bankid.equalsIgnoreCase("HSB"))
         		{
         			bankname="HSBC BANK";
         			request.setAttribute("PartToShow","HSBCBankInvalidsResult");
         		}
         		request.setAttribute("bankName", bankname);
        		}
        		else{
         			list=scb.getReceiptInvalids(transid);
         			//request.setAttribute("searchResult",list);
        			request.setAttribute("PartToShow","ReceiptInvalidsResult");
	          	}
        	   LogManager.push("Invalids LIST retrieved size:"+list);
        	}catch(Exception e){
        		LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);
        	}
	            	
            saveMessages(request,errors);
            saveErrors(request,errors);
        	validateResult="";
			request.setAttribute("searchResult", list);
        	return mapping.findForward(forward);
	}
	public ActionForward getReversals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			LogManager.push("Inside getReversals");
			List list = new ArrayList();
			String bankid=request.getParameter("bankId");
			String transid=request.getParameter("transactionNo");
						
	        try{
	             		LogManager.push("Search bank id: "+bankid+" Search trans id: "+transid);
	            		if(!bankid.equalsIgnoreCase(""))
	            		{
	            			LogManager.push("Bank Search fro");
	             		list=scb.getBankReversals(transid,bankid);
	             		String bankname="HDFC BANK";
	             		request.setAttribute("PartToShow","HDFCBankReversalsResult");
		            	
	             		if(bankid.equalsIgnoreCase("SCB"))
	             		{
	             			bankname="SCB BANK";
	             			request.setAttribute("PartToShow","SCBBankReversalsResult");
	             		}
	             		if(bankid.equalsIgnoreCase("CIT"))
	             		{
	             			bankname="CITI BANK";
	             			request.setAttribute("PartToShow","CitiBankReversalsResult");
			            	
	             		}
	             		if(bankid.equalsIgnoreCase("AXB"))
	             		{
	             			bankname="AXIS BANK";
	             			request.setAttribute("PartToShow","AXBBankReversalsResult");
			            	
	             		}
	             		if(bankid.equalsIgnoreCase("KOT"))
	             		{
	             			bankname="KOTAK BANK";
	             			request.setAttribute("PartToShow","KOTBankReversalsResult");
			            	
	             		}
	             		request.setAttribute("bankName", bankname);
	             		
	            		}
	            		else{
	             			list=scb.getReceiptReversals(transid);
	            			request.setAttribute("PartToShow","ReceiptReversalsResult");
	    	          	}
	            	   LogManager.push("getReversals LIST retrieved size:"+list);
	            		}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - getReversals(): "+e);}
	            	
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	        	validateResult="";
				request.setAttribute("searchResult", list);
	        	return mapping.findForward(forward);
	}
	public ActionForward getNocheques(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			LogManager.push("Inside getNocheques");
			List list = new ArrayList();
			String bankid=request.getParameter("bankId");
			String transid=request.getParameter("transactionNo");
						
	        try{
	             		LogManager.push("Search bank id: "+bankid+" Search trans id: "+transid);
	            		if(!bankid.equalsIgnoreCase(""))
	            		{
	            			LogManager.push("Bank Search fro");
	             		list=scb.getBankNocheqeus(transid,bankid);
	             		String bankname="HDFC BANK";
	             		request.setAttribute("PartToShow","HDFCNochequesResult");
		            	
	             		if(bankid.equalsIgnoreCase("CIT"))
	             		{
	             			bankname="CITI BANK";
	             			request.setAttribute("PartToShow","CitiNochequesResult");
			            	
	             		}
	             		if(bankid.equalsIgnoreCase("SCB"))
	             		{
	             			bankname="SCB BANK";
	             			request.setAttribute("PartToShow","SCBNochequesResult");
			            	
	             		}

	             		if(bankid.equalsIgnoreCase("AXB"))
	             		{
	             			bankname="AXIS BANK";
	             			request.setAttribute("PartToShow","AXBNochequesResult");
			            	
	             		}
	             		if(bankid.equalsIgnoreCase("KOT"))
	             		{
	             			bankname="KOTAK BANK";
	             			request.setAttribute("PartToShow","KOTNochequesResult");
			            	
	             		}
	             		request.setAttribute("bankName", bankname);
	             		
	            		}
	            		else{
	             			list=scb.getBankNocheqeus(transid,bankid);
	            			request.setAttribute("PartToShow","ReceiptNochequesResult");
	    	          	}
	            	   LogManager.push("Invalids LIST retrieved size:"+list);
	            		}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	        	validateResult="";
				request.setAttribute("searchResult", list);
	        	return mapping.findForward(forward);
	}
	public ActionForward getReceiptPayments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			LogManager.push("Inside getReceiptPayments");
			List list = new ArrayList();
			String bankid=request.getParameter("bankId");
			String transid=request.getParameter("transactionNo");
						
	        try{
         		LogManager.push("Search bank id: "+bankid+" Search trans id: "+transid);
        		list=scb.getReceiptPayments(transid);
    			request.setAttribute("PartToShow","ReceiptPaymentsResult");
           		LogManager.push("Payments LIST retrieved size:"+list);
                }
	            catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	        	validateResult="";
				request.setAttribute("searchResult", list);
	        	return mapping.findForward(forward);
	}
	public ActionForward getUpdatedReversals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			LogManager.push("Inside getUpdatedReversals");
			List list = new ArrayList();
			String bankid=request.getParameter("bankId");
			String transid=request.getParameter("transactionNo");
			//sbean.setNonmatchedlist((List)request.getAttribute("nonmatchedlist"));
			HttpSession session = request.getSession(true);
			//sbean.setNonmatchedlist( (List)session.getAttribute("nonmatchedlist"));
			String bankNos[]=(String[])session.getAttribute("exportReversalBankNos");
			LogManager.push("request list::"+request.getAttribute("nonmatchedlist"));		
	        		
	        try{
         		LogManager.push("Search bank id: "+bankid+" Search trans id: "+transid);
        		list=scb.getReversalUpdatedList(bankNos,sbean, session.getId());
    			request.setAttribute("PartToShow","ReversalUpdatedResult");
           		LogManager.push("ReversalUpdatedResult LIST retrieved size:"+list);
                }
	            catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	        	validateResult="";
				request.setAttribute("searchResult2", list);
				//request.setAttribute("nonmatchedlist", sbean.getNonmatchedlist());
	        	return mapping.findForward(forward);
	}
	public ActionForward getActualMatched(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			LogManager.push("Inside getActualMatched");
			List list = new ArrayList();
			String bankid=request.getParameter("bankId");
			String transid=request.getParameter("transactionNo");
			//sbean.setNonmatchedlist((List)request.getAttribute("nonmatchedlist"));
			HttpSession session = request.getSession(true);
			String bankNos[]=(String[])session.getAttribute("exportBankNos");
			
			LogManager.push("request list::"+request.getAttribute("nonmatchedlist"));		
	        	
	        try{
         		LogManager.push("Search bank id: "+bankid+" Search trans id: "+transid);
         		list=scb.getActualMatchedList(bankNos,sbean,session.getId());
        		request.setAttribute("PartToShow","MatchedResult");
           		LogManager.push("MatchedResult LIST retrieved size:"+list);
                }
	            catch(Exception e){LogManager.push("Exception In SearchDispatcAction - getActualMatched(): "+e);}
	            	
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	        	validateResult="";
				request.setAttribute("searchResult", list);
				//request.setAttribute("nonmatchedlist", sbean.getNonmatchedlist());
	        	return mapping.findForward(forward);
	}
	public ActionForward getActualNonMatched(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			LogManager.push("Inside getActualNonMatched");
			List list = new ArrayList();
			String bankid=request.getParameter("bankId");
			String transid=request.getParameter("transactionNo");
			LogManager.push("Nonmatched listben:"+sbean.getNonmatchedlist());
			HttpSession session = request.getSession(true);
			sbean.setNonmatchedlist( (List)session.getAttribute("nonmatchedlist"));
			String bankNo[]=(String[])session.getAttribute("exportBankNos");
					
			LogManager.push("request list::"+request.getAttribute("nonmatchedlist"));		
	        try{
         		LogManager.push("Search bank id: "+bankid+" Search trans id: "+transid);
         		
        		list=scb.getNotMatchedActualList(bankNo,sbean,session.getId());
         		
    			request.setAttribute("PartToShow","NonMatchedResult");
           		LogManager.push("NonMatchedResult LIST retrieved size:"+list);
                }
	            catch(Exception e){LogManager.push("Exception In SearchDispatcAction - getActualNonMatched(): "+e);}
	            	
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	        	validateResult="";
				request.setAttribute("nonmatchedResult", list);
				//request.setAttribute("nonmatchedlist", sbean.getNonmatchedlist());
	        	return mapping.findForward(forward);
	}
	private ActionErrors validateForm(SearchFormBean sbean,final String[] chequeNo,final String[] chequeAmt ) throws CommonBaseException{
		final UploadCB uploadCB = new UploadCB();
		ActionErrors errors = new ActionErrors();
		
		String result;
		for(int i=0;i<chequeNo.length;i++)
		{
			if(isString(chequeNo[i]) && chequeNo[i]!=null && !chequeNo[i].equalsIgnoreCase("") )
			{
					//result="Enter Cheque no. in numbers only";
					errors.add("searchFormBean", new ActionError("search.update.chequeno.invalid"));			
					
			}
			 if(isString(chequeAmt[i]) && chequeAmt[i]!=null && !chequeAmt[i].equalsIgnoreCase(""))
			{
					//result="Enter Cheque amount in numbers only";
					errors.add("searchFormBean", new ActionError("search.update.chequeamt.invalid"));			
					
			}
		
		}
		
	
		return errors;
	}
	public boolean isString(String arg)
	{
		boolean result=true;
		String pattern ="[0-9]+";
		Pattern p=Pattern.compile(pattern);
		Matcher m=p.matcher(arg);
		if(m.matches())
		result=false;
			
		return result;
	}
	public ActionForward cashDetails(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Cash Detail Start");
		LogManager.push("Cash details");
		ActionForward forward= null;
		forward=mapping.findForward("cashdetail"); 		
		return forward;
	}
	public ActionForward submitBankReversalDetails(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		
		LogManager.push("inside bank reversal daetails");
		ActionForward forward = null;			
		final HttpSession session = request.getSession(true);
		final List list;		
		SearchFormBean sForm = (SearchFormBean)form;
		String bank[]=request.getParameterValues("bankNo1");
		String reverse[]=request.getParameterValues("reversal");
		String bankData=sForm.getBankData();
		SearchCB scb=new SearchCB();
		for(int i=0;i<reverse.length-1;i++)
		{
			if(reverse[i].equalsIgnoreCase("R"))
			{
		    reversalPage=reversalPage+","+reverse[i];
		    bankPage=bankPage+","+bank[i];
			}
		}
			
		String[] bankNo=bankPage.split(",");
		final String[] bankNo1 = bankPage.split(",");
		final String[] reversal = reversalPage.split(",");
		
		String search=request.getParameter("search");
		LogManager.push("search:"+sForm.getSearchIn());
		LogManager.push("bank id "+sForm.getBankId());
		
	    try{
		scb.updateBankReversalData(bankPage,sForm);
	    List reversalList=scb.getReversalUpdatedList(bankNo,sForm,session.getId());
	    request.setAttribute("searchResult2", reversalList);
		sForm.setPartToShow("ReversalUpdatedResult");
	    }catch(Exception e){e.printStackTrace();}
		finally{
		bankPage="";
		reversalPage="";
		}
		
		session.setAttribute("exportReversalBankNos", bankNo);
		request.setAttribute("PartToShow","ReversalUpdatedResult");
		forward = mapping.findForward("searchpage");
		return forward;
	}
	public ActionForward submitBankActualDetails(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		
		ActionForward forward = null;
		List nonMatchedList=null;
		List matchedList=null;
		final HttpSession session = request.getSession(true);
		final List list;		
		SearchFormBean sForm = (SearchFormBean)form;
		String amount[]=request.getParameterValues("actualChequeAmount");
		String numbers[]=request.getParameterValues("actualChequeNo");
		String bank[]=request.getParameterValues("bankNo1");
		String reverse[]=request.getParameterValues("reversal");
		String bankData=sForm.getBankData();
		for(int i=0;i<amount.length-1;i++)
		{
			if(amount[i].length()>0||numbers[i].length()>0)
			{
				actualChequeAmountPage=actualChequeAmountPage+","+amount[i];
				actualChequeNoPage=actualChequeNoPage+","+numbers[i];
				bankPage=bankPage+","+bank[i];
			}
		}
						
		final String[] actualChequeAmount=actualChequeAmountPage.split(",");
		String[] bankNo=bankPage.split(",");
		final String[] actualChequeNo = actualChequeNoPage.split(",");
		SearchCB scb=new SearchCB();
		
			
		try{
		scb.updateReverse(sForm);
		scb.updateBankActualDetails(actualChequeNo,actualChequeAmount,bankNo,sForm);
		scb.updateReverse(sForm);
		scb.processActuals(sForm);
		
		nonMatchedList=scb.getNotMatchedActualList(bankNo,sForm,session.getId());
		matchedList=scb.getActualMatchedList(bankNo, sForm, session.getId());
		if(matchedList.size()>0)
		{
			sForm.setPartToShow("MatchedResult");
        	request.setAttribute("PartToShow","MatchedResult");
		}
		if(nonMatchedList.size()>0)
		 {
			sForm.setPartToShow("NonMatchedResult");
    		request.setAttribute("PartToShow","NonMatchedResult");	
		 }
		}
		catch(Exception e){e.printStackTrace();}
		finally {
		actualChequeAmountPage="";
		actualChequeNoPage="";
		bankPage="";
		}
		session.setAttribute("exportBankNos", bankNo);
		request.setAttribute("searchResult", matchedList);
	    request.setAttribute("nonmatchedResult", nonMatchedList);
	   
		forward = mapping.findForward("searchpage");
		return forward;
	}
	
}