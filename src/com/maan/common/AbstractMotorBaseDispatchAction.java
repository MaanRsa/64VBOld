/*
 * Created on Jan 17, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.maan.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

/**
 * @author Vinoth.K
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class AbstractMotorBaseDispatchAction extends DispatchAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */

	/**
	 * Retrieves any existing errors placed in the request by previous actions.
	 * This method could be called instead of creating a
	 * <code>new ActionMessages()<code> at the beginning of an <code>Action<code>
	 * This will prevent saveErrors() from wiping out any existing Errors
	 *
	 * @return the Errors that already exist in the request, or a new ActionMessages object if empty.
	 * @param request The servlet request we are processing
	 * @since Struts 1.2.1
	 */
	protected ActionMessages getErrors(final HttpServletRequest request) {
		ActionMessages errors = (ActionMessages) request
				.getAttribute(Globals.ERROR_KEY);
		if (errors == null) {
			errors = new ActionMessages();
		}
		return errors;
	}

	/**
	 * Retrieves any existing messages placed in the request by previous
	 * actions. This method could be called instead of creating a
	 * <code>new ActionMessages()<code> at the beginning of an <code>Action<code>
	 * This will prevent saveMessages() from wiping out any existing Messages
	 *
	 * @return the Messages that already exist in the request, or a new ActionMessages object if empty.
	 * @param request The servlet request we are processing
	 * @since Struts 1.2.1
	 */
	protected ActionMessages getMessages(final HttpServletRequest request) {
		ActionMessages messages = (ActionMessages) request
				.getAttribute(Globals.MESSAGE_KEY);
		if (messages == null) {
			messages = new ActionMessages();
		}
		return messages;
	}

	/**
	 * <p>
	 * Save the specified error messages keys into the appropriate request
	 * attribute for use by the &lt;html:errors&gt; tag, if any messages are
	 * required. Otherwise, ensure that the request attribute is not created.
	 * </p>
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param errors
	 *            Error messages object
	 * @since Struts 1.2
	 */
	protected void saveErrors(final HttpServletRequest request,
			final ActionMessages errors) {

		// Remove any error messages attribute if none are required
		if ((errors == null) || errors.isEmpty()) {
			request.removeAttribute(Globals.ERROR_KEY);
			return;
		}

		// Save the error messages we need
		request.setAttribute(Globals.ERROR_KEY, errors);

	}

	/**
	 * <p>
	 * Save the specified messages keys into the appropriate request attribute
	 * for use by the &lt;html:messages&gt; tag (if messages="true" is set), if
	 * any messages are required. Otherwise, ensure that the request attribute
	 * is not created.
	 * </p>
	 * 
	 * @param request
	 *            The servlet request we are processing.
	 * @param messages
	 *            The messages to save. <code>null</code> or empty messages
	 *            removes any existing ActionMessages in the request.
	 * 
	 * @since Struts 1.1
	 */
	protected void saveMessages(final HttpServletRequest request,
			final ActionMessages messages) {

		// Remove any messages attribute if none are required
		if ((messages == null) || messages.isEmpty()) {
			request.removeAttribute(Globals.MESSAGE_KEY);
			return;
		}

		// Save the messages we need
		request.setAttribute(Globals.MESSAGE_KEY, messages);
	}

	/**
	 * <p>
	 * Save the specified messages keys into the appropriate session attribute
	 * for use by the &lt;html:messages&gt; tag (if messages="true" is set), if
	 * any messages are required. Otherwise, ensure that the session attribute
	 * is not created.
	 * </p>
	 * 
	 * @param session
	 *            The session to save the messages in.
	 * @param messages
	 *            The messages to save. <code>null</code> or empty messages
	 *            removes any existing ActionMessages in the session.
	 * 
	 * @since Struts 1.2
	 */
	protected void saveMessages(final HttpSession session,
			final ActionMessages messages) {

		// Remove any messages attribute if none are required
		if ((messages == null) || messages.isEmpty()) {
			session.removeAttribute(Globals.MESSAGE_KEY);
			return;
		}

		// Save the messages we need
		session.setAttribute(Globals.MESSAGE_KEY, messages);
	}

	/**
	 * Append the specified error messages keys into the appropriate request
	 * attribute for use by the &lt;struts:errors&gt; tag, if any messages are
	 * required. Otherwise, ensure that the request attribute is not created.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param errors
	 *            Error messages object
	 */
	protected void appendErrors(final HttpServletRequest request,
			final ActionMessages errors) {
		ActionMessages oldErrors = null;
		final Object obj = request.getAttribute(Globals.ERROR_KEY);
		if (obj != null) {
			oldErrors = (ActionMessages) obj;
		}
		if (oldErrors == null) {
			if ((errors == null) || errors.isEmpty()) {
				request.removeAttribute(Globals.ERROR_KEY);
				return;
			} else {
				oldErrors = errors;
			}
		} else {
			if ((errors != null) && !errors.isEmpty()) {
				oldErrors.add(errors);
			}
		}
		// Save the error messages we need
		request.setAttribute(Globals.ERROR_KEY, oldErrors);
	}

	/**
	 * Append the specified messages into the appropriate request attribute for
	 * use by the &lt;nua:messages&gt; tag, if any messages are required.
	 * Otherwise, ensure that the request attribute is not created.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param messages
	 *            Action messages object
	 */
	protected void appendMessages(final HttpServletRequest request,
			final ActionMessages messages) {
		ActionMessages oldMessages = null;
		final Object obj = request.getAttribute(Globals.MESSAGE_KEY);
		if (obj != null) {
			oldMessages = (ActionMessages) obj;
		}
		if (oldMessages == null || oldMessages.equals(messages)) {
			if ((messages == null) || messages.isEmpty()) {
				oldMessages = messages;
			} else {
				request.removeAttribute(Globals.MESSAGE_KEY);
				return;
			}
		} else {
			if ((messages != null) && !messages.isEmpty()) {
				oldMessages.add(messages);
			}
		}
		request.setAttribute(Globals.MESSAGE_KEY, oldMessages);
	}

}
