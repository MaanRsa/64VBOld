package com.maan.common.exception;

import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import com.maan.common.LogManager;

public class CommonBaseExceptionHandler extends ExceptionHandler {

	public ActionForward execute(final Exception exception,
			final ExceptionConfig config, final ActionMapping mapping,
			final ActionForm formInstance, final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException {
		try {
			final Calendar calendar = Calendar.getInstance();
			final String time = " TIME   ::  " + calendar.getTime();
			String expType;
			String expMsg;
			String msg;
			if (exception instanceof CommonBaseException) {
				exception.printStackTrace();
				LogManager.fatal(exception);
				final CommonBaseException hbex = (CommonBaseException) exception;
				expType = " EXCEPTION TYPE  ::  Application Error occured";
				msg = hbex.getErrorMessage();
			} else {
				expType = " EXCEPTION TYPE  ::  System Error occured";
				exception.printStackTrace();
				msg = exception.getMessage();
			}
			if (msg == null || msg.equals("")) {
				expMsg = " ";
			} else {
				expMsg = " MESSAGE   ::  " + msg;
			}
			request.setAttribute("ExpTime", time);
			request.setAttribute("ExpType", expType);
			request.setAttribute("ExpMsg", expMsg);
		} catch (Exception e) {
			LogManager.error(e);
		}
		return super.execute(exception, config, mapping, formInstance, request,
				response);
	}
}
