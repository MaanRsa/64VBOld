package com.maan.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.tiles.TilesRequestProcessor;

public class MyRequestProcessor extends TilesRequestProcessor {
	
	protected boolean processPreprocess(final HttpServletRequest request,
			final HttpServletResponse response) {

		boolean result=false;
		final HttpSession session = request.getSession(false);

		// Check if userName attribute is there is session.
		// If so, it means user has allready logged in

		if (request.getServletPath().equals("/welcome.do")
				|| request.getServletPath().equals("/login.do")) {
			result = true;
		}
		// LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
		// LogManager.info("session id is"+session.getId());

		else if (session.getAttribute("loginForm") == null) {
			try {
				// If no redirect user to login Page
				//LogManager.info("session id getting"+session.getId());
				//request.getRequestDispatcher("/sessionExpire.do").forward(request, response);
				//request.getRequestDispatcher("sessionError").forward(request, response);
				request.getRequestDispatcher("/session/error_messg.jsp").include(request, response);
				//response.sendRedirect("/session/error_messg.jsp");
				result = false;
			} catch (Exception ex) {
				LogManager.debug(ex);
			}
		} else {
			result = true;
		}
		return result;

		// return super.processPreprocess(request, response);
	}
}
