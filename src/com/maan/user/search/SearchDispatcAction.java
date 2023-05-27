package com.maan.user.search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document; 
import com.lowagie.text.Paragraph; 
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter; 
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;

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
import com.maan.search.SearchVB;
import com.maan.transaction.TransactionCB;
import com.maan.transaction.TransactionForm;

import java.awt.Color;

public class SearchDispatcAction extends DispatchAction {

	private String forward;
	SearchCB scb=new SearchCB();
	private String validateResult="";
	String receiptNos="";
	private ActionErrors errors = new ActionErrors();
	final com.maan.common.Validation validate = new com.maan.common.Validation();
	public ActionForward receiptDetails(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Receipt Detail Start");
		ActionForward forward = null;
		final SearchCB sCB = new SearchCB();
		String receipt=request.getParameter("receiptNo");
		LogManager.push("Receipt param:"+receipt);
		request.setAttribute("ReceiptValue", receipt);
		forward = mapping.findForward("receiptdetail2"); 		
		return forward;
	}
	public ActionForward search(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Search Start");
		ActionForward forward = null;
		final SearchCB sCB = new SearchCB();
		SearchFormBean sForm=new SearchFormBean();
		sForm.setPartToShow("Search");
		LogManager.push(sForm.getPartToShow());
		sForm.reset(mapping, request);
		request.setAttribute("PartToShow","Search");
		forward = mapping.findForward("searchpage"); 		
		return forward;
	}
	public ActionForward policysearch(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("policysearch Start::");
		ActionForward forward = null;
		final SearchCB sCB = new SearchCB();
		SearchFormBean sForm=new SearchFormBean();
		sForm.setPartToShow("Search");
		LogManager.push(sForm.getPartToShow());
		sForm.reset(mapping, request);
		request.setAttribute("PartToShow","Search");
        forward = mapping.findForward("policysearchpage"); 		
		return forward;
	}
	public ActionForward goreceiptdetail(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("go receipt detail");
		ActionForward forward = null;
		final SearchCB sCB = new SearchCB();
		
		ReceiptDetails rec=( ReceiptDetails)form;
		String receiptval=(String)request.getAttribute("ReceiptValue");
		LogManager.push("Receiptval"+receiptval);
		rec.setReceiptNo(receiptval);
		sCB.getReceiptDetail(rec);
		LogManager.push("receipt no:"+rec.getReceiptNo());
		
		if(rec.getChequeno()==null)
		{
			rec.setChequeno("");
		}LogManager.push("cheque no:"+rec.getChequeno());
		LogManager.push("Payment type:"+rec.getPaymenttype());
	    response.setContentType("application/pdf");        
		  Document document = new Document();    
		  try{        
			  response.setContentType("application/pdf");  
			  PdfWriter.getInstance(document, response.getOutputStream()); 
			  document.open();   
		  //added for pdf 
				String loc =getServlet().getServletContext().getRealPath("/images/logo.gif").replace('/','\\');
				LogManager.push("path===>" + loc);
				Image image = Image.getInstance(loc);
			if(!(rec.getChequeno().equalsIgnoreCase("") || rec.getChequeno().equalsIgnoreCase("-"))){
			  Table tt=new Table(4,10);
				tt.setWidth(100);
				tt.setBorderColor(Color.WHITE);
				
				Cell ct1 = new Cell(new Paragraph("\n\n",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				ct1.setHorizontalAlignment(Element.ALIGN_CENTER);
				ct1.setVerticalAlignment(10);
				ct1.setBorderColor(Color.WHITE);
				ct1.setColspan(4);
				tt.addCell(ct1);		 
				
				Cell ccc = new Cell(image);
			 	ccc.setHorizontalAlignment(Element.ALIGN_LEFT);
				ccc.setBorderColor(Color.white);
				ccc.setHeader(true);
				tt.addCell(ccc);
				
				Cell cc1 = new Cell(new Paragraph("",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				cc1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cc1.setBorderColor(Color.WHITE);
				 
				tt.addCell(cc1);
				Cell cc2 = new Cell(new Paragraph("",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				cc2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cc2.setBorderColor(Color.WHITE);
				 
				tt.addCell(cc2);
				Cell cc3 = new Cell(new Paragraph("",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				cc3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cc3.setBorderColor(Color.WHITE);
				 
				tt.addCell(cc3);
				
				document.add(tt);
			  Table t1 = new Table(4, 13);
			t1.setWidth(100);
			t1.setBorderColor(Color.WHITE);
			Cell tt1 = new Cell(new Paragraph("",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			tt1.setHorizontalAlignment(Element.ALIGN_CENTER);
			tt1.setBorderColor(Color.WHITE);
			tt1.setColspan(4);
			t1.addCell(tt1);
			
			
			Cell tc1 = new Cell(new Paragraph("RECEIPT DETAILS",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			tc1.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc1.setBorderColor(Color.WHITE);
			tc1.setColspan(4);
			t1.addCell(tc1);
			
			Cell tc2 = new Cell(new Paragraph("Agent Code : ", FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc2.setBorderColor(Color.WHITE);
			t1.addCell(tc2);
			Cell tc3 = new Cell(new Paragraph(rec.getReceiptagcode(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc3.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc3.setBorderColor(Color.WHITE);
			t1.addCell(tc3);
			Cell tc4 = new Cell(new Paragraph("Agent Name  : ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
							Font.NORMAL, new Color(0, 0, 0))));
			tc4.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc4.setBorderColor(Color.WHITE);
			t1.addCell(tc4);
			Cell tc5 = new Cell(new Paragraph(rec.getReceiptagname(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc5.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc5.setBorderColor(Color.WHITE);
			t1.addCell(tc5);
			
			Cell tc6 = new Cell(new Paragraph("Receipt Branch : ", FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc6.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc6.setBorderColor(Color.WHITE);
			t1.addCell(tc6);
			Cell tc7 = new Cell(new Paragraph(rec.getReceiptbranchcode(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc7.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc7.setBorderColor(Color.WHITE);
			t1.addCell(tc7);
			Cell tc8 = new Cell(new Paragraph("Receipt Date  : ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
							Font.NORMAL, new Color(0, 0, 0))));
			tc8.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc8.setBorderColor(Color.WHITE);
			t1.addCell(tc8);
			Cell tc9 = new Cell(new Paragraph(rec.getReceiptdate(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc9.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc9.setBorderColor(Color.WHITE);
			t1.addCell(tc9);
			
			Cell tc10 = new Cell(new Paragraph("Receipt No : ", FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc10.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc10.setBorderColor(Color.WHITE);
			t1.addCell(tc10);
			Cell tc11 = new Cell(new Paragraph(rec.getReceiptno(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc11.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc11.setBorderColor(Color.WHITE);
			t1.addCell(tc11);
			Cell tc12 = new Cell(new Paragraph("Payment Type : ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
							Font.NORMAL, new Color(0, 0, 0))));
			tc12.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc12.setBorderColor(Color.WHITE);
			t1.addCell(tc12);
			Cell tc13 = new Cell(new Paragraph(rec.getPaymenttype(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc13.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc13.setBorderColor(Color.WHITE);
			t1.addCell(tc13);
			Cell tt2 = new Cell(new Paragraph("",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			tt2.setHorizontalAlignment(Element.ALIGN_CENTER);
			tt2.setColspan(4);
			t1.addCell(tt2);
			

	    	if(rec.getPaymenttype().equalsIgnoreCase("Credit")){
	    		Cell tc14 = new Cell(new Paragraph("CREDIT CARD DETAILS",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				tc14.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc14.setBorderColor(Color.WHITE);
				tc14.setColspan(4);
				t1.addCell(tc14);
				
				Cell tc15 = new Cell(new Paragraph("Credit Card Type : ", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc15.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc15.setBorderColor(Color.WHITE);
				t1.addCell(tc15);
				Cell tc16 = new Cell(new Paragraph(rec.getCreditcardtype(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc16.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc16.setBorderColor(Color.WHITE);
				t1.addCell(tc16);
				Cell tc17 = new Cell(new Paragraph("Credit Card Bank : ",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
								Font.NORMAL, new Color(0, 0, 0))));
				tc17.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc17.setBorderColor(Color.WHITE);
				t1.addCell(tc17);
				Cell tc18 = new Cell(new Paragraph(rec.getCreditcardbank(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc18.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc18.setBorderColor(Color.WHITE);
				t1.addCell(tc18);
				
				Cell tc19 = new Cell(new Paragraph("Transaction Reference : ", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc19.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc19.setBorderColor(Color.WHITE);
				t1.addCell(tc19);
				Cell tc20 = new Cell(new Paragraph(rec.getTransactionreference(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc20.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc20.setBorderColor(Color.WHITE);
				t1.addCell(tc20);
	    	}
	    	
	    	
	    	if(rec.getPaymenttype().equalsIgnoreCase("Cheque"))
	    	{

				Cell tc14 = new Cell(new Paragraph("CHEQUE DETAILS",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				tc14.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc14.setBorderColor(Color.WHITE);
				tc14.setColspan(4);
				t1.addCell(tc14);
				
				Cell tc15 = new Cell(new Paragraph("Cheque No : ", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc15.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc15.setBorderColor(Color.WHITE);
				t1.addCell(tc15);
				Cell tc16 = new Cell(new Paragraph(rec.getChequeno(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc16.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc16.setBorderColor(Color.WHITE);
				t1.addCell(tc16);
				Cell tc17 = new Cell(new Paragraph("Cheque Date : ",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
								Font.NORMAL, new Color(0, 0, 0))));
				tc17.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc17.setBorderColor(Color.WHITE);
				t1.addCell(tc17);
				Cell tc18 = new Cell(new Paragraph(rec.getChequedate(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc18.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc18.setBorderColor(Color.WHITE);
				t1.addCell(tc18);
				
				Cell tc19 = new Cell(new Paragraph("Cheque Amount : ", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc19.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc19.setBorderColor(Color.WHITE);
				t1.addCell(tc19);
				Cell tc20 = new Cell(new Paragraph(rec.getAmount(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc20.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc20.setBorderColor(Color.WHITE);
				t1.addCell(tc20);
				Cell tc21 = new Cell(new Paragraph("Bank Name And Location : ",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
								Font.NORMAL, new Color(0, 0, 0))));
				tc21.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc21.setBorderColor(Color.WHITE);
				t1.addCell(tc21);
				Cell tc22 = new Cell(new Paragraph(rec.getBanknameandloc(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc22.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc22.setBorderColor(Color.WHITE);
				t1.addCell(tc22);
				
				Cell tc23 = new Cell(new Paragraph("Particulars : ", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc23.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc23.setBorderColor(Color.WHITE);
				t1.addCell(tc23);
				Cell tc24 = new Cell(new Paragraph(rec.getParticulars(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc24.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc24.setBorderColor(Color.WHITE);
				t1.addCell(tc24);
				
				Cell tc25 = new Cell(new Paragraph("", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc25.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc25.setBorderColor(Color.WHITE);
				t1.addCell(tc25);
				
	    	}
	    	Cell tc26 = new Cell(new Paragraph("", FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc26.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc26.setBorderColor(Color.WHITE);
			t1.addCell(tc26);
			Cell tt3 = new Cell(new Paragraph("",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			tt3.setHorizontalAlignment(Element.ALIGN_CENTER);
			tt3.setColspan(4);
			t1.addCell(tt3);
			
			Cell tc27 = new Cell(new Paragraph("STATUS",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			tc27.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc27.setBorderColor(Color.WHITE);
			tc27.setColspan(4);
			t1.addCell(tc27);
			
		/* if(rec.getChequestatus().equalsIgnoreCase("reversed") ){
	 		Cell tc28 = new Cell(new Paragraph("REVERSED", FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD,
							new Color(0, 0, 0))));
			tc28.setHorizontalAlignment(Element.ALIGN_CENTER);
			tc28.setBorderColor(Color.WHITE);
			tc28.setColspan(4);
			t1.addCell(tc28);	
		 }*/ if(rec.getChequestatus().equalsIgnoreCase("")){
		 		Cell tc28 = new Cell(new Paragraph("STATUS NOT KNOWN", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD,
								new Color(0, 0, 0))));
				tc28.setHorizontalAlignment(Element.ALIGN_CENTER);
				tc28.setBorderColor(Color.WHITE);
				tc28.setColspan(4);
				t1.addCell(tc28);	
			 }
	 		 if(!rec.getChequestatus().equalsIgnoreCase("")){
	 			Cell tc28 = new Cell(new Paragraph("Cheque Status", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc28.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc28.setBorderColor(Color.WHITE);
				
				t1.addCell(tc28);
	 			Cell tc29 = new Cell(new Paragraph(rec.getChequestatus().toUpperCase(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL,
								new Color(0, 0, 0))));
				tc29.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc29.setBorderColor(Color.WHITE);
				
				t1.addCell(tc29);
				
				
				Cell tc30 = new Cell(new Paragraph(rec.getDepositdate().equalsIgnoreCase("")?"":"Date", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc30.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc30.setBorderColor(Color.WHITE);
				
				t1.addCell(tc30);  

				Cell tc31 = new Cell(new Paragraph(rec.getDepositdate(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc31.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc31.setBorderColor(Color.WHITE);
				
				t1.addCell(tc31);  
	 		 }
	 			
	 		
			
			if(rec.getChequestatus().equalsIgnoreCase("Returned")){
				Cell tc32 = new Cell(new Paragraph("Return Reason", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc32.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc32.setBorderColor(Color.WHITE);
				tc32.setColspan(1);
				t1.addCell(tc32);  
				Cell tc33 = new Cell(new Paragraph(rec.getReason(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD,
								new Color(0, 0, 0))));
				tc33.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc33.setBorderColor(Color.WHITE);
				tc33.setColspan(3);
				t1.addCell(tc33);  
			}
			

			Cell s1 = new Cell(new Paragraph("\n",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			s1.setHorizontalAlignment(Element.ALIGN_CENTER);
			s1.setVerticalAlignment(10);
			s1.setBorderColor(Color.WHITE);
			s1.setColspan(4);
			t1.addCell(s1);
			
			Cell c2 = new Cell(new Paragraph("",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			c2.setHorizontalAlignment(Element.ALIGN_LEFT);
			c2.setBorderColor(Color.WHITE);
			t1.addCell(c2);
			Cell c3 = new Cell(new Paragraph("",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			c3.setHorizontalAlignment(Element.ALIGN_LEFT);
			c3.setBorderColor(Color.WHITE);
		 	t1.addCell(c3);
	
			
			Cell c = new Cell(new Paragraph("Date : ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
							Font.NORMAL, new Color(0, 0, 0))));
			c.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c.setBorderColor(Color.WHITE);
		 
			t1.addCell(c);
			
		     Calendar currentDate = Calendar.getInstance();
			  SimpleDateFormat formatter= 
			  new SimpleDateFormat("ddMMMyyyy HH:mm:ss");
			  String dateNow = formatter.format(currentDate.getTime());
			  LogManager.push("Now the date is :=>  " + dateNow);
			 

			
			Cell c1 = new Cell(new Paragraph(dateNow,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
							Font.NORMAL, new Color(0, 0, 0))));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setBorderColor(Color.WHITE);
		 
			t1.addCell(c1);
			document.add(t1);
		
			
			  //end pdf
			  // document.add(new Paragraph(new Date().toString()));  
		  }
		/// Added for Cash cases	
		  else{
			  LogManager.push("Cash Part::");
			  try{
			  Table tt=new Table(4,10);
				tt.setWidth(100);
				tt.setBorderColor(Color.WHITE);
				
				Cell ct1 = new Cell(new Paragraph("\n\n",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				ct1.setHorizontalAlignment(Element.ALIGN_CENTER);
				ct1.setVerticalAlignment(10);
				ct1.setBorderColor(Color.WHITE);
				ct1.setColspan(4);
				tt.addCell(ct1);		 
				
				Cell ccc = new Cell(image);
			 	ccc.setHorizontalAlignment(Element.ALIGN_LEFT);
				ccc.setBorderColor(Color.white);
				ccc.setHeader(true);
				tt.addCell(ccc);
				
				Cell cc1 = new Cell(new Paragraph("",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				cc1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cc1.setBorderColor(Color.WHITE);
				 
				tt.addCell(cc1);
				Cell cc2 = new Cell(new Paragraph("",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				cc2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cc2.setBorderColor(Color.WHITE);
				 
				tt.addCell(cc2);
				Cell cc3 = new Cell(new Paragraph("",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				cc3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cc3.setBorderColor(Color.WHITE);
				 
				tt.addCell(cc3);
				
				document.add(tt);
			  Table t1 = new Table(4, 13);
			t1.setWidth(100);
			t1.setBorderColor(Color.WHITE);
			Cell tt1 = new Cell(new Paragraph("",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			tt1.setHorizontalAlignment(Element.ALIGN_CENTER);
			tt1.setBorderColor(Color.WHITE);
			tt1.setColspan(4);
			t1.addCell(tt1);
			
			
			Cell tc1 = new Cell(new Paragraph("RECEIPT DETAILS",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			tc1.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc1.setBorderColor(Color.WHITE);
			tc1.setColspan(4);
			t1.addCell(tc1);
			
			Cell tc2 = new Cell(new Paragraph("Agent Code : ", FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc2.setBorderColor(Color.WHITE);
			t1.addCell(tc2);
			Cell tc3 = new Cell(new Paragraph(rec.getReceiptagcode(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc3.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc3.setBorderColor(Color.WHITE);
			t1.addCell(tc3);
			Cell tc4 = new Cell(new Paragraph("Agent Name  : ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
							Font.NORMAL, new Color(0, 0, 0))));
			tc4.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc4.setBorderColor(Color.WHITE);
			t1.addCell(tc4);
			Cell tc5 = new Cell(new Paragraph(rec.getReceiptagname(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc5.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc5.setBorderColor(Color.WHITE);
			t1.addCell(tc5);
			
			Cell tc6 = new Cell(new Paragraph("Receipt Branch : ", FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc6.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc6.setBorderColor(Color.WHITE);
			t1.addCell(tc6);
			Cell tc7 = new Cell(new Paragraph(rec.getReceiptbranchcode(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc7.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc7.setBorderColor(Color.WHITE);
			t1.addCell(tc7);
			Cell tc8 = new Cell(new Paragraph("Receipt Date  : ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
							Font.NORMAL, new Color(0, 0, 0))));
			tc8.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc8.setBorderColor(Color.WHITE);
			t1.addCell(tc8);
			
			/////
			Cell tc9 = new Cell(new Paragraph(rec.getReceiptdate(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc9.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc9.setBorderColor(Color.WHITE);
			t1.addCell(tc9);
			
			Cell tc10 = new Cell(new Paragraph("Receipt No : ", FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc10.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc10.setBorderColor(Color.WHITE);
			t1.addCell(tc10);
			Cell tc11 = new Cell(new Paragraph(rec.getReceiptno(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc11.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc11.setBorderColor(Color.WHITE);
			t1.addCell(tc11);
			
			
			////////////////////\\\\
  
			
			Cell tc12 = new Cell(new Paragraph("Payment Type : ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
							Font.NORMAL, new Color(0, 0, 0))));
			tc12.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc12.setBorderColor(Color.WHITE);
			t1.addCell(tc12);
			Cell tc13 = new Cell(new Paragraph(rec.getPaymenttype(), FontFactory
					.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
							new Color(0, 0, 0))));
			tc13.setHorizontalAlignment(Element.ALIGN_LEFT);
			tc13.setBorderColor(Color.WHITE);
			t1.addCell(tc13);
			Cell tt2 = new Cell(new Paragraph("",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
							Font.BOLD, new Color(0, 0, 0))));
			tt2.setHorizontalAlignment(Element.ALIGN_CENTER);
			tt2.setColspan(4);
			t1.addCell(tt2);
			

	    	
	    	
	    

				Cell tc14 = new Cell(new Paragraph(" DETAILS",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				tc14.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc14.setBorderColor(Color.WHITE);
				tc14.setColspan(4);
				t1.addCell(tc14);
				
			
				
				Cell tc19 = new Cell(new Paragraph(" Amount : ", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc19.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc19.setBorderColor(Color.WHITE);
				t1.addCell(tc19);
				Cell tc20 = new Cell(new Paragraph(rec.getAmount(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc20.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc20.setBorderColor(Color.WHITE);
				t1.addCell(tc20);
				Cell tc21 = new Cell(new Paragraph("Bank Name And Location : ",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
								Font.NORMAL, new Color(0, 0, 0))));
				tc21.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc21.setBorderColor(Color.WHITE);
				t1.addCell(tc21);
				Cell tc22 = new Cell(new Paragraph(rec.getBanknameandloc(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc22.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc22.setBorderColor(Color.WHITE);
				t1.addCell(tc22);
				
				Cell tc23 = new Cell(new Paragraph("Particulars : ", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc23.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc23.setBorderColor(Color.WHITE);
				t1.addCell(tc23);
				Cell tc24 = new Cell(new Paragraph(rec.getParticulars(), FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc24.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc24.setBorderColor(Color.WHITE);
				t1.addCell(tc24);
				
				Cell tc25 = new Cell(new Paragraph("", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc25.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc25.setBorderColor(Color.WHITE);
				t1.addCell(tc25);
				
				////\\\\\\\\\\\
				Cell tc26 = new Cell(new Paragraph("", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
								new Color(0, 0, 0))));
				tc26.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc26.setBorderColor(Color.WHITE);
				t1.addCell(tc26);
				Cell tt3 = new Cell(new Paragraph("",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				tt3.setHorizontalAlignment(Element.ALIGN_CENTER);
				tt3.setColspan(4);
				t1.addCell(tt3);
				
				Cell tc27 = new Cell(new Paragraph("STATUS",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				tc27.setHorizontalAlignment(Element.ALIGN_LEFT);
				tc27.setBorderColor(Color.WHITE);
				tc27.setColspan(4);
				t1.addCell(tc27);
				
			 if(rec.getChequestatus().equalsIgnoreCase("")){
		 		Cell tc28 = new Cell(new Paragraph("NOT REALIZED", FontFactory
						.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD,
								new Color(0, 0, 0))));
				tc28.setHorizontalAlignment(Element.ALIGN_CENTER);
				tc28.setBorderColor(Color.WHITE);
				tc28.setColspan(4);
				t1.addCell(tc28);	
			 }
		 		 if(!rec.getChequestatus().equalsIgnoreCase("")){
		 			Cell tc28 = new Cell(new Paragraph(" Status", FontFactory
							.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
									new Color(0, 0, 0))));
					tc28.setHorizontalAlignment(Element.ALIGN_LEFT);
					tc28.setBorderColor(Color.WHITE);
					
					t1.addCell(tc28);
		 			Cell tc29 = new Cell(new Paragraph(rec.getChequestatus().toUpperCase(), FontFactory
							.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL,
									new Color(0, 0, 0))));
					tc29.setHorizontalAlignment(Element.ALIGN_LEFT);
					tc29.setBorderColor(Color.WHITE);
					
					t1.addCell(tc29);
					
					Cell tc30 = new Cell(new Paragraph("Date", FontFactory
							.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
									new Color(0, 0, 0))));
					tc30.setHorizontalAlignment(Element.ALIGN_LEFT);
					tc30.setBorderColor(Color.WHITE);
					
					t1.addCell(tc30);  

					Cell tc31 = new Cell(new Paragraph(rec.getDepositdate(), FontFactory
							.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL,
									new Color(0, 0, 0))));
					tc31.setHorizontalAlignment(Element.ALIGN_LEFT);
					tc31.setBorderColor(Color.WHITE);
					
					t1.addCell(tc31);  
		 		 }
		 			
		 		Cell s1 = new Cell(new Paragraph("\n",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				s1.setHorizontalAlignment(Element.ALIGN_CENTER);
				s1.setVerticalAlignment(10);
				s1.setBorderColor(Color.WHITE);
				s1.setColspan(4);
				t1.addCell(s1);
				
				Cell c2 = new Cell(new Paragraph("",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				c2.setHorizontalAlignment(Element.ALIGN_LEFT);
				c2.setBorderColor(Color.WHITE);
				t1.addCell(c2);
				Cell c3 = new Cell(new Paragraph("",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,
								Font.BOLD, new Color(0, 0, 0))));
				c3.setHorizontalAlignment(Element.ALIGN_LEFT);
				c3.setBorderColor(Color.WHITE);
			 	t1.addCell(c3);
		
				
				Cell c = new Cell(new Paragraph("Date : ",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
								Font.NORMAL, new Color(0, 0, 0))));
				c.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c.setBorderColor(Color.WHITE);
			 
				t1.addCell(c);
				
			     Calendar currentDate = Calendar.getInstance();
				  SimpleDateFormat formatter= 
				  new SimpleDateFormat("ddMMMyyyy HH:mm:ss");
				  String dateNow = formatter.format(currentDate.getTime());
				  LogManager.push("Now the date is :=>  " + dateNow);
				 

				
				Cell c1 = new Cell(new Paragraph(dateNow,
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 9,
								Font.NORMAL, new Color(0, 0, 0))));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setBorderColor(Color.WHITE);
				t1.addCell(c1);
				document.add(t1);
				 
			
			  }
			  catch(Exception e)
			  {
				  LogManager.push("msg " + e.getMessage());
			  }
			  }
			  }
		  catch(Exception e){   
			  e.printStackTrace(); 
			  }     
		  document.close(); 
		forward = mapping.findForward("receiptdetail2"); 		
		return forward;
	}
	
	
	public ActionForward submitSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SearchFormBean sbean = (SearchFormBean)form;
		ActionErrors errors = new ActionErrors();
		SearchCB scb=new SearchCB();
		forward="searchpage";
		String bankstatus=sbean.getSearchIn();
		LogManager.push("Bank status searched in:"+bankstatus);
		LogManager.push("Inside searchsubmit");
		List list = new ArrayList();
		//List banklist = new ArrayList();
		//List receiptlist = new ArrayList();
		//errors = validateForm(sbean);
		
		validateResult=scb.validateSearchInfo(sbean,validateResult);
		
		if("".equalsIgnoreCase(validateResult))
		{
			try{
				LogManager.push("Search Option: "+sbean.getBankId()+" Search Value: "+sbean.getChequeAmount());
				list=scb.getSearchList(sbean);
				//	banklist=scb.getBankSearchList(sbean);
				//	receiptlist=scb.getReceiptSearchList(sbean);
				LogManager.push("Bank LIST retrieved size:"+list);
				//LogManager.push("Receipt LIST retrieved size:"+list);
			}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
			
			request.setAttribute("PartToShow","SearchResult");
			request.setAttribute("PartToShow1",bankstatus);
			LogManager.push(request.getRequestURI());
			
			
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
		
		validateResult="";
		//sbean.setSearchResult(lists);
		LogManager.push("REALISE:-->"+sbean.getRealised());
		request.setAttribute("searchResult", list);
		//request.setAttribute("receiptSearchResult", receiptlist);
		
		return mapping.findForward(forward);
	}
	public ActionForward policySearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			SearchCB scb=new SearchCB();
			List list = new ArrayList();
			forward="policysearchpage";
			String search=request.getParameter("pagination")==null?"":request.getParameter("pagination");
			sbean.setSearch(search);
			String searchOption=sbean.getSearchOption();
						
			if(searchOption.equalsIgnoreCase("select"))
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {"Please select an option</br>"}));
			if(sbean.getSearchValue().length()<1)
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {"Please enter a value"}));
			else if(searchOption.equalsIgnoreCase("receipt no"))
			{
				if(sbean.getSearchValue().length()<6)
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {"Receipt no should contain minimum 6 characters."}));	
			}
			if(searchOption.equalsIgnoreCase("cheque no"))
			{
				if(sbean.getSearchValue().length()<4)
				{
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {"Cheque no should contain minimum 4 characters."}));
				}
			}
			if(!searchOption.equalsIgnoreCase("Transaction No"))
			{
				
			if(searchOption.equalsIgnoreCase("policy No"))
			{
				sbean.setPolicyNo(sbean.getSearchValue());
			}
			else if(searchOption.equalsIgnoreCase("receipt No"))
			{
			    sbean.setReceiptNo(sbean.getSearchValue());
			}
			else if(searchOption.equalsIgnoreCase("cheque No"))
			{
				sbean.setChequeNo(sbean.getSearchValue());
			}
							
			LogManager.push("Inside policySearch::==>");
			
			if(searchOption.equalsIgnoreCase("policy No"))
			{
	        validateResult=scb.validatePolicySearchInfo(sbean,validateResult);
			}
	          if("".equalsIgnoreCase(validateResult)&&errors.isEmpty())
	            {
	            	try{
	            		LogManager.push("Search Option: "+sbean.getPolicyNo());
	            		list=scb.getPolicySearchList(sbean);
	            		LogManager.push("Search List size:"+list);
		            	}
	            	catch(Exception e){
	            		LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);
	            		}
		            	request.setAttribute("PartToShow","SearchResult");
		            	LogManager.push(request.getRequestURI());
	            }
	            else
	            {
	            	//errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {validateResult}));
	            	request.setAttribute("PartToShow","Search");
	            	sbean.setPartToShow("Search");
	            }
	          request.setAttribute("searchResult", list);
	          
			}else if(searchOption.equalsIgnoreCase("Transaction No")&&errors.isEmpty())
			{
				TransactionForm tForm=new TransactionForm();
				TransactionCB tcb=new TransactionCB();
				tForm.setTransaction("RT");
				tForm.setTransactionNo(sbean.getSearchValue());
				try
				{
				list=tcb.getTransactedDetails(tForm);
				}
				catch(Exception e)
				{
					LogManager.debug(e);
				}
				request.setAttribute("details", list);
				request.setAttribute("PartToShow","ReceiptTransactions");
				sbean.setPartToShow("ReceiptTransactions");
			}
			else
			{
				request.setAttribute("PartToShow","Search");
            	sbean.setPartToShow("Search");
			}
	            saveMessages(request,errors);
	            saveErrors(request,errors);
			
			validateResult="";
			LogManager.push("Exit policySearch::==>");
			
			
	        return mapping.findForward(forward);
	}

	public ActionForward manualRealization(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CommonBaseException {
		
		SearchCB scb=new SearchCB();
		SearchFormBean sbean=(SearchFormBean)form;
		ActionErrors errors=new ActionErrors();
		String searchOption=sbean.getSearchOption();
			
		String searchValue=sbean.getSearchValue();
		List list=null;
		if(searchOption.equalsIgnoreCase("policy no"))
		{
		 sbean.setPolicyNo(searchValue);
		}
		else if(searchOption.equalsIgnoreCase("receipt NO"))
		{
			sbean.setReceiptNo(searchValue);
		}
		else if(searchOption.equalsIgnoreCase("cheque no"))
		{
			sbean.setChequeNo(searchValue);
		}
			
		scb.doManualRealization(searchOption,searchValue);
		list=scb.getManulRealizedList(sbean.getSearchOption(),sbean.getSearchValue());
		request.setAttribute("realizedList", list);
		request.setAttribute("PartToShow","RealizedList");
		return mapping.findForward("policysearchpage");
	}
	
	

	/*public ActionForward bankListSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			String bankstatus=sbean.getSearchIn();
			LogManager.push("Bank status searched in:"+bankstatus);
			
			LogManager.push("Inside bankListSearch");
			
			List banklist = new ArrayList();
			//errors = validateForm(sbean);
			
	        validateResult=scb.validateSearchInfo(sbean,validateResult);
			
	          if("".equalsIgnoreCase(validateResult))
	            {
	            	try{
	            		LogManager.push("Search Option: "+sbean.getBankId()+" Search Value: "+sbean.getChequeAmount());
	            		banklist=scb.getBankSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+banklist);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            	request.setAttribute("PartToShow","SearchResult");
	            	request.setAttribute("PartToShow1",bankstatus);
	    	       
	            	
	    	        
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
			
			validateResult="";
			//sbean.setSearchResult(lists);
			LogManager.push("REALISE:-->"+sbean.getRealised());
			request.setAttribute("bankSearchResult", banklist);
			
	return mapping.findForward(forward);
	}

	public ActionForward receiptListSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			SearchFormBean sbean = (SearchFormBean)form;
			ActionErrors errors = new ActionErrors();
			 SearchCB scb=new SearchCB();
			forward="searchpage";
			String bankstatus=sbean.getSearchIn();
			LogManager.push("Bank status searched in:"+bankstatus);
			LogManager.push("Inside receiptListSearch");
			
			List receiptlist = new ArrayList();
			//errors = validateForm(sbean);
			
	        validateResult=scb.validateSearchInfo(sbean,validateResult);
			
	          if("".equalsIgnoreCase(validateResult))
	            {
	            	try{
	            		LogManager.push("Search Option: "+sbean.getBankId()+" Search Value: "+sbean.getChequeAmount());
	            		
	            		receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Receipt LIST retrieved size:"+receiptlist);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            	request.setAttribute("PartToShow","SearchResult");
	            	request.setAttribute("PartToShow1",bankstatus);
	    	       
	            	
	    	        
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
			
			validateResult="";
			//sbean.setSearchResult(lists);
			LogManager.push("REALISE:-->"+sbean.getRealised());
			request.setAttribute("receiptSearchResult", receiptlist);
			
	return mapping.findForward(forward);
	}*/




	private ActionErrors validateForm(SearchFormBean sbean,final String[] chequeNo,final String[] chequeAmt ) throws CommonBaseException{
		
		ActionErrors errors = new ActionErrors();
		
		String result;
		for(int i=0;i<chequeNo.length;i++)
		{
			if(isString(chequeNo[i]) && chequeNo[i]!=null && chequeNo[i]!="" )
			{
					//result="Enter Cheque no. in numbers only";
					errors.add("searchFormBean", new ActionError("search.update.chequeno.invalid"));			
					
			}
			 if(isString(chequeAmt[i]) && chequeAmt[i]!=null && chequeAmt[i]!="")
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
	
	public ActionForward ajaxCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CommonBaseException {
		
		String receiptNo=request.getParameter("receiptNo")==null?"":request.getParameter("receiptNo");
		String checkStatus=request.getParameter("checkYN")==null?"":request.getParameter("checkYN");
		String name=request.getParameter("name");
		SearchCB scb=new SearchCB();
		
		if(!receiptNo.equalsIgnoreCase(""))
		{
		scb.updateChecked(receiptNo,checkStatus);
		}else
		{
			if(name.contains("checkbox"))
			{
				scb.updateChecked(name.replace("checkbox", ""), checkStatus);
			}
		}
				
	return null;
	}
	
	public ActionForward getManulRealizedList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CommonBaseException {
		
		SearchFormBean sbean=(SearchFormBean)form;
		SearchCB scb=new SearchCB();
		List list=new ArrayList();
		list=scb.getManulRealizedList(sbean.getSearchOption(),sbean.getSearchValue());
		request.setAttribute("realizedList", list);
		request.setAttribute("PartToShow","RealizedList");
		return mapping.findForward("policysearchpage");
		
		
	}
	
	public ActionForward getRecordsCount(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("getRecordsCount Start");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=new TransactionForm();
		SearchFormBean sbean=(SearchFormBean)form;
		tForm.setBankId(sbean.getBankId());
		tForm.setTransactionNo(sbean.getTransactionNo());
		String bankId=tForm.getBankId();
		LogManager.push(bankId);
		forward = mapping.findForward("user");
		if(bankId.equalsIgnoreCase("R"))
		{
		   List list=tCB.getRecords(tForm);
		   request.setAttribute("details", list);
		   request.setAttribute("PartToShow","ReceiptRecords");
		}
				
		return forward;
	}
	
	public ActionForward goRealised(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Realised Start");
		ActionForward forward = null;
		SearchFormBean sbean=(SearchFormBean)form;
		String transid=sbean.getTransactionNo();
		com.maan.search.SearchCB scb=new com.maan.search.SearchCB();
		com.maan.search.SearchFormBean sForm=new com.maan.search.SearchFormBean();
		sForm.setTransactionNo(sbean.getTransactionNo());
		sForm.setSearchIn(sbean.getSearchIn());
		sForm.setRealised(sbean.getRealised());
		sForm.setBankId(sbean.getBankId());
		List lists=scb.getSearchList(sForm);
		request.setAttribute("PartToShow","RealisedTransactionResult");	
		request.setAttribute("realisedTransactionResult", lists);
		forward = mapping.findForward("policysearchpage"); 		
		return forward; 
	}
	public ActionForward goInvalids(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Invalids Start");
		ActionForward forward = null;
		SearchFormBean sbean=(SearchFormBean)form;
		String transid=sbean.getTransactionNo();
		com.maan.search.SearchCB scb=new com.maan.search.SearchCB();
		List list=scb.getReceiptInvalids(transid);
		request.setAttribute("PartToShow","ReceiptInvalidsResult");
		request.setAttribute("receiptInvalidsResult", list);
		forward = mapping.findForward("policysearchpage"); 		
		return forward;
	}
	public ActionForward goReversals(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Reversals Start");
		ActionForward forward = null;
		SearchFormBean sbean=(SearchFormBean)form;
		String transid=sbean.getTransactionNo();
		com.maan.search.SearchCB scb=new com.maan.search.SearchCB();
		List list=scb.getReceiptReversals(transid);
		request.setAttribute("PartToShow","ReceiptReversalsResult");
		request.setAttribute("receiptReversalsResult", list);
		forward = mapping.findForward("policysearchpage"); 		
		return forward;
	}
	public ActionForward goNoCheques(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("goNoCheques Start");
		ActionForward forward = null;
		SearchFormBean sbean=(SearchFormBean)form;
		String transid=sbean.getTransactionNo();
		com.maan.search.SearchCB scb=new com.maan.search.SearchCB();
		List list=scb.getBankNocheqeus(transid,"");
		request.setAttribute("PartToShow","ReceiptNochequesResult");
		request.setAttribute("receiptNochequesResult", list);
		forward = mapping.findForward("policysearchpage"); 		
		return forward;
	}
	
	public ActionForward goReceiptPayments(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("goReceiptPayments Start");
		ActionForward forward = null;
		SearchFormBean sbean=(SearchFormBean)form;
		String transid=sbean.getTransactionNo();
		com.maan.search.SearchCB scb=new com.maan.search.SearchCB();
		List list=scb.getReceiptPayments(transid);
		request.setAttribute("PartToShow","ReceiptPaymentsResult");
		request.setAttribute("receiptPaymentsResult", list);
		forward = mapping.findForward("policysearchpage"); 		
		return forward;
	}
	public ActionForward goDuplicates(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Duplicates Start");
		ActionForward forward = null;
		SearchFormBean sbean=(SearchFormBean)form;
		String transid=sbean.getTransactionNo();
		com.maan.search.SearchCB scb=new com.maan.search.SearchCB();
		List list=scb.getReceiptDuplicates(transid);
		request.setAttribute("PartToShow","ReceiptDuplicatesResult");
		request.setAttribute("receiptDuplicatesResult", list);
		forward = mapping.findForward("policysearchpage"); 		
		return forward;
	}
	
	public ActionForward goPending(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("goReceiptPayments Start");
		ActionForward forward = null;
		SearchFormBean sbean=(SearchFormBean)form;
		String pagination=request.getParameter("pagination")==null?"":request.getParameter("pagination");
		sbean.setPagination(pagination);
		SearchCB scb=new SearchCB();
		List list=scb.getPendingList(sbean);
		request.setAttribute("PartToShow","NotRealizedTransactionList");
		request.setAttribute("pendingList", list);
		request.setAttribute("PartToShow1",sbean.getSearchIn());
		return mapping.findForward("policysearchpage");
	}

}
	
