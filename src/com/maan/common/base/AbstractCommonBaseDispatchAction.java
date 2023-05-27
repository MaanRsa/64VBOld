package com.maan.common.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public abstract class AbstractCommonBaseDispatchAction extends DispatchAction {

	protected ActionMessages getErrors(final HttpServletRequest request) {
		ActionMessages errors = (ActionMessages) request
				.getAttribute(Globals.ERROR_KEY);
		if (errors == null) {
			errors = new ActionMessages();
		}
		return errors;
	}

	protected ActionMessages getMessages(final HttpServletRequest request) {
		ActionMessages messages = (ActionMessages) request
				.getAttribute(Globals.MESSAGE_KEY);
		if (messages == null) {
			messages = new ActionMessages();
		}
		return messages;
	}

	protected void saveErrors(final HttpServletRequest request,
			final ActionMessages errors) {

		if ((errors == null) || errors.isEmpty()) {
			request.removeAttribute(Globals.ERROR_KEY);
			return;
		}

		request.setAttribute(Globals.ERROR_KEY, errors);

	}

	protected void saveMessages(final HttpServletRequest request,
			final ActionMessages messages) {

		if ((messages == null) || messages.isEmpty()) {
			request.removeAttribute(Globals.MESSAGE_KEY);
			return;
		}

		request.setAttribute(Globals.MESSAGE_KEY, messages);
	}

	protected void saveMessages(final HttpSession session,
			final ActionMessages messages) {

		if ((messages == null) || messages.isEmpty()) {
			session.removeAttribute(Globals.MESSAGE_KEY);
			return;
		}

		session.setAttribute(Globals.MESSAGE_KEY, messages);
	}

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

		request.setAttribute(Globals.ERROR_KEY, oldErrors);
	}

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
