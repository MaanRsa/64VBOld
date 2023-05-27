package com.maan.admin.TmasValidation;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.BeanUtils; 
import com.maan.common.AbstractMotorBaseDispatchAction;
import com.maan.common.LogManager;
import com.maan.common.Validation;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.MotorBaseException;
import com.maan.common.exception.MotorExceptionConstants;

public class TmasValidationDispatchAction extends AbstractMotorBaseDispatchAction
{
	private static final String MFRID="0";
	private static final String value="0";
	private static final String PATH="path";
	private static final String PATHS="";
	
	ActionForward forward;
	public ActionForward initTmas(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws CommonBaseException
	{
		LogManager.push("--->Method Inside InitTmas--->");
		LogManager.push("====Method Enter Into TmasValidation====");
		final TmasValidationForm formObj=(TmasValidationForm) form;
		 
		final TmasValidationCB tmasCB=new TmasValidationCB();
		final HttpSession session = request.getSession();
		formObj.setValidationStatus("N");
	 	formObj.setMandatoryStatus("N");
	 	formObj.setReferenceStatus("N");
	 	formObj.setActive("X");
		LogManager.push("Mfrid =====>"+(String)session.getAttribute(MFRID));
		final List list=tmasCB.ListTmasValidation(value);
		request.setAttribute("list",list);
	    request.setAttribute(PATH,"DisplayTmasValidations");
	   	forward=mapping.findForward("validationTmas");
		LogManager.push("====Method Enter Into TmasValidation====Exit");
		LogManager.push("----->Method Exit---->");
		LogManager.push("====>Method Enter Into InitTmas Dispatch===>Exit");
	  return forward;	
	}
	
	public ActionForward addNewTmas(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws CommonBaseException
	{
	  
		LogManager.push("--->Method Inside addNewTmas--->");
		final TmasValidationForm formObj=(TmasValidationForm) form;
		final TmasValidationCB tmasCB=new TmasValidationCB();
		    formObj.setValidationStatus("N");
		    formObj.setMandatoryStatus("N");
		    formObj.setReferenceStatus("N");
		    formObj.setActive("X");
		    formObj.setTypeid("");
			formObj.setDbColumnName("");
			formObj.setExcelHeaderName("");
			formObj.setFieldLength("");
			formObj.setDataType("-Select-");
			formObj.setDataFormat("");
			formObj.setReferenceTable("");
			formObj.setReferenceColumn("");
			formObj.setReferenceCondition("");
			formObj.setCheckValue("");
			formObj.setCheckValueCond("");
			formObj.setXgenColumn("");
			formObj.setXmlTagName("");
		 
		final Map Type_id=tmasCB.getTypeId(formObj,MFRID);
		request.setAttribute("type",Type_id);
		request.setAttribute(PATH,"submitTmasValidation" );
		forward=mapping.findForward("validationTmas");
		LogManager.push("--->Method Inside addNewTmas--->Exit");
		return forward;
	}
	
	public ActionForward EditTmasMas(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws CommonBaseException
	{
		
		LogManager.push("---------Method Enter Into EditTmasMas-------->"+request.getParameter("validId"));
		final TmasValidationForm formObj=(TmasValidationForm) form;
		final TmasValidationCB tmasCB=new TmasValidationCB();
		final HttpSession session=request.getSession();
		final Map Type_id=tmasCB.getTypeId(formObj,MFRID);
		formObj.reset(mapping, request);
	 	request.setAttribute("type",Type_id);
	 	
	 		LogManager.push("---------Method Enter Into EditTmasMas-------->"+request.getParameter("validId"));
	 		final List list=tmasCB.getEditTmasValidation(request.getParameter("validId"));
	 		final Iterator iterator = list.iterator();
	 		while (iterator.hasNext()) 
	 		{
	 			final TmasValidationBean beanObj=(TmasValidationBean)iterator.next();
	 			    
	 			    formObj.setValidId(beanObj.getValidId());
				    formObj.setTypeid(beanObj.getTypeid());
				    formObj.setDbColumnName(beanObj.getDbColumnName());
				    formObj.setExcelHeaderName(beanObj.getExcelHeaderName());
				    formObj.setValidationStatus(beanObj.getValidationStatus());
				    formObj.setMandatoryStatus(beanObj.getMandatoryStatus());
				    formObj.setFieldLength(beanObj.getFieldLength());
				    formObj.setDataType(beanObj.getDataType());
				    formObj.setDataFormat(beanObj.getDataFormat());
				    formObj.setReferenceStatus(beanObj.getReferenceStatus());
				    formObj.setReferenceTable(beanObj.getReferenceTable());
				    formObj.setReferenceColumn(beanObj.getReferenceColumn());
				    formObj.setReferenceCondition(beanObj.getReferenceCondition());
				    formObj.setCheckValue(beanObj.getCheckValue());
				    formObj.setCheckValueCond(beanObj.getCheckValueCond());
				    formObj.setActive(beanObj.getActive());
				    formObj.setXgenColumn(beanObj.getXgenColumn());
				    formObj.setXmlTagName(beanObj.getXmlTagName());
	 		}
	 		request.setAttribute(PATH,"EditTmasValidation" );
	 		forward = mapping.findForward("validationTmas");
		return forward;
	}  
	
	public ActionForward UpdateTmasValid(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws CommonBaseException
	{
		
		LogManager.push("---------Method Enter Into UpdateTmasValid-------->"+request.getParameter("validId"));
		final TmasValidationForm formObj=(TmasValidationForm) form;
		final TmasValidationBean beanObj=new TmasValidationBean();
		final TmasValidationCB tmasCB=new TmasValidationCB();
		
		try {
			BeanUtils.copyProperties(beanObj,formObj);
			tmasCB.UpdateTmasValidation(beanObj);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final List list=tmasCB.ListTmasValidation(value);
		tmasCB.UpdateTmasValidation(beanObj);
		request.setAttribute("list",list);
	    request.setAttribute(PATH,"DisplayTmasValidations");
	   	forward=mapping.findForward("validationTmas");
		
		return forward;
	}
	public ActionForward SubmitTmasValidation(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws CommonBaseException
	{
		
		LogManager.push("---------Method Enter Into SubmitTmasValidation-------->");
		final TmasValidationForm formObj=(TmasValidationForm) form;
		final TmasValidationCB tmasCB=new TmasValidationCB();
		final TmasValidationBean beanObj=new TmasValidationBean();
		final Map Type_id=tmasCB.getTypeId(formObj,MFRID);
		request.setAttribute("type",Type_id);
		ActionErrors errors=new ActionErrors();
		 errors = validString(formObj.getTypeid(),"typeid",errors);
		 errors = validString(formObj.getDbColumnName(),"dbColumnName",errors);
		 errors = validString(formObj.getExcelHeaderName(),"excelHeaderName",errors);
		 errors = validString(formObj.getValidationStatus(),"validationStatus",errors);
		 errors = validString(formObj.getMandatoryStatus(),"mandatoryStatus",errors);
		 errors = validString(formObj.getFieldLength(),"fieldLength",errors);
		 errors = validint(formObj.getFieldLength(),"fieldLengths",errors);
		 errors = validString(formObj.getDataType(),"dataType",errors);
		 errors = validString(formObj.getDataFormat(),"dataFormat",errors);
		 errors = validString(formObj.getReferenceStatus(),"referenceStatus",errors);
		 errors = validString(formObj.getReferenceTable(),"referenceTable",errors);
		 errors = validString(formObj.getReferenceColumn(),"referenceColumn",errors);
		 errors = validString(formObj.getReferenceCondition(),"referenceCondition",errors);
		 errors = validString(formObj.getCheckValue(),"checkValue",errors);
		 errors = validString(formObj.getCheckValueCond(),"checkValueCond",errors);
		 errors = validString(formObj.getActive(),"active",errors);
		 errors = validString(formObj.getXgenColumn(),"xgenColumn",errors);
		 errors = validString(formObj.getXmlTagName(),"xmlTagName",errors);
		 LogManager.push("after Error======>");
		 if(errors.isEmpty())
		 {
		   boolean flag=false;
		   		try
		   		{
		   			LogManager.push("Inserted Started");
		   			BeanUtils.copyProperties(beanObj,formObj);
		   			flag=tmasCB.insertTmasValidation(beanObj);
		   		}
		   		catch (IllegalAccessException e) 
		   		{
		   			e.printStackTrace();
		   		}
		   		catch (InvocationTargetException e) 
		   		{
			     e.printStackTrace();
		         }
	     if(flag)
		 {
	    	 LogManager.push("---->Method Enter Into Flag---->");
			 final List list=tmasCB.ListTmasValidation(value);
				request.setAttribute("list",list);
			    request.setAttribute(PATH,"DisplayTmasValidations");
				
			 	    formObj.setTypeid("");
					formObj.setDbColumnName("");
					formObj.setExcelHeaderName("");
					formObj.setValidationStatus("");
					formObj.setMandatoryStatus("");
					formObj.setFieldLength("");
					//formObj.setDataType("");
					formObj.setDataFormat("");
					formObj.setReferenceStatus("");
					formObj.setReferenceTable("");
					formObj.setReferenceColumn("");
					formObj.setReferenceCondition("");
					formObj.setCheckValue("");
					formObj.setCheckValueCond("");
					formObj.setActive("");
					formObj.setXgenColumn("");
					formObj.setXmlTagName("");
					formObj.setDataType("-Select-");
					forward=mapping.findForward("validationTmas");
		 }
				
	}
	 else
	 {
		 LogManager.push("------>Method Enter into Error---->" );
	     request.setAttribute(PATH,"submitTmasValidation" );
	     saveMessages(request, errors);
		 LogManager.push ("error"+errors.toString() );
		saveErrors(request, errors);
	    forward=mapping.findForward("validationTmas");
	  
	 }
	 
		
		LogManager.push("---------Method Enter Into SubmitTmasValidation-------->Exit");
		return forward;
	}
		
      /*public ActionForward initTmas(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws MotorBaseException
	{
		LogManager.push("Inside TMAS_--_>");
		ActionForward forward=null;
		final TmasValidationForm formObj=(TmasValidationForm) form;
		final TmasValidationCB tmasCB=new TmasValidationCB();
		final HttpSession session = request.getSession();
		try
		{
			LogManager.push("---------------Method Enter into TmasValidation Dispatch Action---------");
			LogManager.push("============>"+(String)session.getAttribute(MFRID));
		 	final Map Type_id=tmasCB.getTypeId(formObj,(String)session.getAttribute(MFRID));
		 	formObj.reset(mapping, request);
		 	request.setAttribute("type",Type_id);
		 	formObj.setValidationStatus("N");
		 	formObj.setMandatoryStatus("N");
		 	formObj.setReferenceStatus("N");
		 	formObj.setActive("X");
		 	formObj.setMode("add");
        	request.setAttribute("Operation","Add");
		 	request.setAttribute(PATH,"submitTmasValidation" );
			forward = mapping.findForward("validationTmas");
			
		}
		catch (Exception e)
		{
			LogManager.debug(e);
        	throw new MotorBaseException(e, MotorExceptionConstants.OTHER_ERROR);
        
		}
		finally
		{
			LogManager.debug("TmasValidationDispatchAction Controller Init method()- Exit");
        	LogManager.popRemove();
		}
		return forward;
	}

	public ActionForward SubmitTmasValidation(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws MotorBaseException
	{
		ActionForward forward=null;
		final TmasValidationForm formObj=(TmasValidationForm) form;
		final TmasValidationCB tmasCB=new TmasValidationCB();
		final TmasValidationBean beanObj=new TmasValidationBean();
		final HttpSession session=request.getSession();
		String Mfrid=session.getAttribute("mfrid").toString();
	     beanObj.setMfrid(Mfrid);
		final Map Type_id=tmasCB.getTypeId(formObj,(String)session.getAttribute(MFRID));
		formObj.reset(mapping, request);
	 	request.setAttribute("type",Type_id);
		try
		{
			LogManager.push("---------------Method Enter into SubmitTmasValidation Dispatch Action---------");
			ActionErrors errors=new ActionErrors();
			 errors = validString(formObj.getTypeid(),"typeid",errors);
			 errors = validString(formObj.getDbColumnName(),"dbColumnName",errors);
			 errors = validString(formObj.getExcelHeaderName(),"excelHeaderName",errors);
			 errors = validString(formObj.getValidationStatus(),"validationStatus",errors);
			 errors = validString(formObj.getMandatoryStatus(),"mandatoryStatus",errors);
			 errors = validString(formObj.getFieldLength(),"fieldLength",errors);
			 errors = validString(formObj.getDataType(),"dataType",errors);
			 errors = validString(formObj.getDataFormat(),"dataFormat",errors);
			 errors = validString(formObj.getReferenceStatus(),"referenceStatus",errors);
			 errors = validString(formObj.getReferenceTable(),"referenceTable",errors);
			 errors = validString(formObj.getReferenceColumn(),"referenceColumn",errors);
			 errors = validString(formObj.getReferenceCondition(),"referenceCondition",errors);
			 errors = validString(formObj.getCheckValue(),"checkValue",errors);
			 errors = validString(formObj.getCheckValueCond(),"checkValueCond",errors);
			 errors = validString(formObj.getActive(),"active",errors);
			 errors = validString(formObj.getXgenColumn(),"xgenColumn",errors);
			 errors = validString(formObj.getXmlTagName(),"xmlTagName",errors);
			 if(errors.isEmpty())
			 {
				 boolean flag=false;
				 if("add".equalsIgnoreCase(formObj.getMode()))
				{
				 BeanUtils.copyProperties(beanObj,formObj);
				 flag=tmasCB.insertTmasValidation(beanObj);
				 
				 	//request.setAttribute("submitTmas","insert");
				 }
				 else
				 {
					 BeanUtils.copyProperties(beanObj,formObj);
					 tmasCB.UpdateTmasValidation(beanObj);
				 }
				 if(flag)
				 {
					// request.setAttribute(PATH,"submitTmasValidation" );
					 final List list=tmasCB.ListTmasValidation((String)session.getAttribute(MFRID));
						request.setAttribute("list",list);
						 formObj.setTypeid("");
							formObj.setDbColumnName("");
							formObj.setExcelHeaderName("");
							formObj.setValidationStatus("");
							formObj.setMandatoryStatus("");
							formObj.setFieldLength("");
							formObj.setDataType("");
							formObj.setDataFormat("");
							formObj.setReferenceStatus("");
							formObj.setReferenceTable("");
							formObj.setReferenceColumn("");
							formObj.setReferenceCondition("");
							formObj.setCheckValue("");
							formObj.setCheckValueCond("");
							formObj.setActive("");
							formObj.setXgenColumn("");
							formObj.setXmlTagName("");
						request.setAttribute(PATH,"ListTmasValidations");
					
				 forward=mapping.findForward("TmasValidationDispatchAction");
				 }
						
			 }
			 else
			 {
			  request.setAttribute(PATH,"submitTmasValidation" );
			    saveMessages(request, errors);
				LogManager.push ("error"+errors.toString() );
				saveErrors(request, errors);
			  forward=mapping.findForward("TmasValidationDispatchAction");
			  
			 }
			 
			
		}
		catch (Exception e)
		{
			LogManager.debug(e);
        	throw new MotorBaseException(e, MotorExceptionConstants.OTHER_ERROR);
        
		}
		finally
		{
			LogManager.debug("TmasValidationDispatchAction Controller Init method()- Exit");
        	LogManager.popRemove();
		}
		
		
		return forward;
	}
	public ActionForward Init(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws MotorBaseException
	{
		ActionForward forward=null;
		final TmasValidationForm formObj=(TmasValidationForm) form;
		final TmasValidationCB tmasCB=new TmasValidationCB();
		final TmasValidationBean beanObj=new TmasValidationBean();
		  try
		  {
			    LogManager.push("====>Method Enter Into InitTmas Dispatch===>");
				LogManager.push("--------Method Enter ViewDispatch Action------------");
				final HttpSession session = request.getSession(true);
				LogManager.push("Mfrid =====>"+(String)session.getAttribute(MFRID));
				final List list=tmasCB.ListTmasValidation(value);
				request.setAttribute("list",list);
			    request.setAttribute(PATH,"DisplayTmasValidations");
			    forward = mapping.findForward("TmasValidationDispatchAction");
			    LogManager.push("====>Method Enter Into InitTmas Dispatch===>Exit");
				
		   }
		  catch (Exception e)
			{
				LogManager.debug(e);
	        	throw new MotorBaseException(e, MotorExceptionConstants.OTHER_ERROR);
	        
			}
			finally
			{
				LogManager.debug("TmasValidationDispatchAction Controller Init method()- Exit");
	        	LogManager.popRemove();
			}
		return forward;
	}
	
	public ActionForward EditTmasMas(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws MotorBaseException
	{
		ActionForward forward=null;
		final TmasValidationForm formObj=(TmasValidationForm) form;
		final TmasValidationCB tmasCB=new TmasValidationCB();
		final HttpSession session=request.getSession();
		final Map Type_id=tmasCB.getTypeId(formObj,(String)session.getAttribute(MFRID));
		formObj.reset(mapping, request);
	 	request.setAttribute("type",Type_id);
	 	try
	 	{
	 		LogManager.push("---------Method Enter Into UpdateTmasValidations--------");
	 		final List list=tmasCB.getEditTmasValidation(formObj.getUpdateTmasValidationId());
	 		final Iterator iterator = list.iterator();
	 		while (iterator.hasNext()) 
	 		{
	 			final TmasValidationBean beanObj=(TmasValidationBean)iterator.next();
	 			    
	 			    formObj.setValidId(beanObj.getValidId());
				    formObj.setTypeid(beanObj.getTypeid());
				    formObj.setDbColumnName(beanObj.getDbColumnName());
				    formObj.setExcelHeaderName(beanObj.getExcelHeaderName());
				    formObj.setValidationStatus(beanObj.getValidationStatus());
				    formObj.setMandatoryStatus(beanObj.getMandatoryStatus());
				    formObj.setFieldLength(beanObj.getFieldLength());
				    formObj.setDataType(beanObj.getDataType());
				    formObj.setDataFormat(beanObj.getDataFormat());
				    formObj.setReferenceStatus(beanObj.getReferenceStatus());
				    formObj.setReferenceTable(beanObj.getReferenceTable());
				    formObj.setReferenceColumn(beanObj.getReferenceColumn());
				    formObj.setReferenceCondition(beanObj.getReferenceCondition());
				    formObj.setCheckValue(beanObj.getCheckValue());
				    formObj.setCheckValueCond(beanObj.getCheckValueCond());
				    formObj.setActive(beanObj.getActive());
				    formObj.setMfrid(beanObj.getMfrid());
				    formObj.setXgenColumn(beanObj.getXgenColumn());
				    formObj.setXmlTagName(beanObj.getXmlTagName());
	 		}
	 		
	 		formObj.setMode("update");
	 		request.setAttribute(PATH,"submitTmasValidation" );
	 		forward = mapping.findForward("TmasValidationDispatchAction");
	 		
	 	}
	   
	  catch (Exception e)
		{
			LogManager.debug(e);
      	throw new MotorBaseException(e, MotorExceptionConstants.OTHER_ERROR);
      
		}
		finally
		{
			LogManager.debug("TmasValidationDispatchAction Controller Init method()- Exit");
      	LogManager.popRemove();
		}
	 	
		
		return forward;
	}*/

	
	private ActionErrors validint(final String value, final String field,final ActionErrors errors) 
	{
	
			LogManager.push("------>Validate Integer------>"+value);
			final String Value = value.trim();
			 int f=0;
			for (int i = 0; i < Value.length(); ++i) 
			{
				final char car = Value.charAt(i);
				if (Character.isLetter(car))
				{
					 f=1;
					LogManager.push("TmasValidationDispatchAction Controller validString method() - field---"+field);
				}
					
			}
			if(f==1)
			{
				errors.add(field, new ActionError("error.tmas_Valid." + field));
			}
			
				return errors;
   }
	
	
	private ActionErrors validString(final String value, final String field,final ActionErrors errors) 
	{
		//Validation val=new Validation();
		try
		{
			LogManager.push("XmlConstantDispatchAction Controller validString method() - Enter---"+value);
	
			if (value == null || value.length()<=0 || "Select".equalsIgnoreCase(value) || "null".equalsIgnoreCase(value))
			{				
				errors.add(field, new ActionError("error.tmas_Validation." + field));
			    LogManager.push("TmasValidationDispatchAction Controller validString method() - field---"+field);
			}	
			
		} 
		catch (Exception e) 
		{
			LogManager.debug(e);	
		}
		finally {
			LogManager.debug("TmasValidationDispatchAction Controller validString method()- Exit");
			LogManager.popRemove();
		}	
		return errors;
	}
	
}
	

