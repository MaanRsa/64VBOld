package com.maan.reports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.maan.common.LogManager;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.MotorBaseException;
import com.maan.common.exception.MotorExceptionConstants;

public class ReportsDispatchAction extends DispatchAction {
	
	public ActionForward reportsList(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
	LogManager.push("reportsList() - Enter");	
	ReportsFormBean bean=(ReportsFormBean)form;	
	ReportsCB rcb=new ReportsCB();
	String requestFrom=request.getParameter("requestFrom")==null?"":request.getParameter("requestFrom");
	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	String requestFor=bean.getRequestFrom()==null?"":bean.getRequestFrom();
	try {
	if(requestFrom.equalsIgnoreCase("direct"))
	{
		bean.setPaymentType("CHQ");
		bean.setStartDate(formatter.format(DateUtils.addMonths(new Date(), -3)));
		bean.setEndDate(formatter.format(new Date()));
	}else if (requestFor.equalsIgnoreCase("CASH")) {
		bean.setPaymentType("CASH");
		if (!(bean.getStartDate().length()>0)){ 
		bean.setStartDate(formatter.format(DateUtils.addMonths(new Date(), -3)));
		bean.setEndDate(formatter.format(new Date()));
	     }
	}else if(requestFor.equalsIgnoreCase("CHQ")){
		bean.setPaymentType("CHQ");
		if (!(bean.getStartDate().length()>0)){ 
			bean.setStartDate(formatter.format(DateUtils.addMonths(new Date(), -3)));
			bean.setEndDate(formatter.format(new Date()));
	     }
	}
	LogManager.push("Payment Type"+bean.getPaymentType());
	rcb.getReportsList(bean);
	bean.setDisplay("report");
	}catch (Exception e) {
		LogManager.debug(e);
	} finally {
		LogManager.push("reportsList() - Exit");
		LogManager.popRemove();
	}
	return mapping.findForward("reports");
}
	@SuppressWarnings("deprecation")
	public ActionForward Advance(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.push("Advance() - Enter");
		ReportsFormBean bean=(ReportsFormBean)form;	
		ReportsCB rcb=new ReportsCB();
		ActionErrors errors=new ActionErrors();
		LogManager.push("Payment Type"+bean.getPaymentType());
		String requestFrom=bean.getRequestFrom();
		try{
		if(requestFrom.equalsIgnoreCase("init")){
			  bean.setDisplay("input");
			  bean.setPaymentType("CHQ");
		}else {
			if(bean.getStartDate()==null||bean.getStartDate().isEmpty())
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("report.error.startdate", new Object[] {"Start Date Required<br/>"}));
			}
			if(bean.getEndDate()==null||bean.getEndDate().isEmpty())
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("report.error.enddate", new Object[] {"End Date Required<br/>"}));
			}
			saveMessages(request,errors);
            saveErrors(request,errors);
			if(errors.isEmpty()){
		     rcb.getReportsList(bean);
			 bean.setDisplay("report");
			}else{
				bean.setDisplay("input");
			}
			}
		  }catch(Exception e){
			LogManager.debug(e);
		  }
		  LogManager.push("Advance() - Exit"); 
		return mapping.findForward("reports");
    }
	
}
