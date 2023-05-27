/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.maan.admin.menu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.maan.common.LogManager;
import com.maan.common.base.AbstractCommonBaseDispatchAction;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;
import com.maan.common.use.CommonCB;

public class MenuAdminDispathAction extends AbstractCommonBaseDispatchAction {
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private final static String	PTS="partToShow";
	private final static String	STATUS="status";
	private final static String	ADMNM="adminMenu";
	private final static String	MNMPL="menuManipulation";
	private final static String	MENU="menu";
	public ActionForward initAdminMenu(final ActionMapping mapping,final ActionForm form,
			final HttpServletRequest request,final HttpServletResponse response)
			throws CommonBaseException {
		ActionForward forward;
		try {
			LogManager
					.push("MenuAdmin Controller initAdminMenu method() - Enter");

			request.setAttribute("menuMapList", new TreeMap());
			request.setAttribute(PTS, "initMenu");
			forward = mapping.findForward(ADMNM);
		} catch (Exception exception) {
			LogManager.debug(exception);
			throw new CommonBaseException(exception,
					CommonExceptionConstants.OTHER_ERROR);
		} finally {
			LogManager
					.debug("MenuAdmin Controller initAdminMenu method() - Exit");
			LogManager.popRemove(); // Should be the last statement
		}
		return forward;
	}

	public ActionForward addNewMenu(final ActionMapping mapping,final ActionForm form,
			final HttpServletRequest request,final HttpServletResponse response)
			throws CommonBaseException {
		final MenuAdminForm adminForm = (MenuAdminForm) form;
		ActionForward forward;
		final CommonCB commonCB = new CommonCB();
		try {
			LogManager.push("MenuAdmin Controller addNewMenu method() - Enter");

			adminForm.setMode("add");
			adminForm.setType("0");
			final int identity = commonCB.getMaxID("MENU_MASTER", "menu_id");
			adminForm.setId(identity);
			adminForm.setStatus("1");

			saveToken(request);
			request.setAttribute("menuMapList", new TreeMap());
			request.setAttribute(PTS, MNMPL);
			forward = mapping.findForward(ADMNM);
		} catch (Exception exception) {
			LogManager.debug(exception);
			throw new CommonBaseException(exception,
					CommonExceptionConstants.OTHER_ERROR);
		} finally {
			LogManager.debug("MenuAdmin Controller addNewMenu method() - Exit");
			LogManager.popRemove(); // Should be the last statement
		}
		return forward;
	}

	public ActionForward edit(final ActionMapping mapping,final ActionForm form,
			final	HttpServletRequest request,final HttpServletResponse response)
			throws CommonBaseException {
		final MenuAdminForm adminForm = (MenuAdminForm) form;
		ActionForward forward;
		AdminMenuVB menuVB = new AdminMenuVB();
		final AdminMenuCB menuCB = new AdminMenuCB();

		try {
			LogManager.push("MenuAdmin Controller edit method() - Enter");

			final ActionErrors errors = new ActionErrors();

			if (adminForm.getSuperMenu().equals("-1")) {
				errors.add("edit", new ActionError("error.admin.menu.edit"));
			}

			if (errors.isEmpty()) {
				adminForm.setMode("edit");
				final List list = menuCB.getMenuInfo(Integer.parseInt(adminForm
						.getSuperMenu()));
				menuVB = (AdminMenuVB) list.get(0);
				BeanUtils.copyProperties(adminForm, menuVB);

				saveToken(request);
				setRequestValue(request, response, adminForm, MENU);
				request.setAttribute(PTS, MNMPL);
			} else {
				setRequestValue(request, response, adminForm, MENU);
				saveMessages(request, errors);
				saveErrors(request, errors);
				request.setAttribute(PTS, "initMenu");
				forward = mapping.findForward(ADMNM);
			}

			forward = mapping.findForward(ADMNM);

		} catch (Exception exception) {
			LogManager.debug(exception);
			throw new CommonBaseException(exception,
					CommonExceptionConstants.OTHER_ERROR);
		} finally {
			LogManager.debug("MenuAdmin Controller edit method() - Exit");
			LogManager.popRemove(); // Should be the last statement
		}
		return forward;
	}

	public ActionForward save(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws CommonBaseException {
		ActionForward forward;
		final MenuAdminForm adminForm = (MenuAdminForm) form;
		final AdminMenuVB menuVB = new AdminMenuVB();
		final AdminMenuCB menuCB = new AdminMenuCB();

		if (isTokenValid(request)) {// avoid duplicate submit
			try {
				LogManager.push("MenuAdmin Controller menu method() - Enter");

				ActionErrors errors = new ActionErrors();
				errors = validation(adminForm.getName(), "name", errors);
				errors = validation(adminForm.getUrl(), "url", errors);
				if (adminForm.getType().equals("-1")) {
					errors.add("edit", new ActionError(
							"error.admin.menu.type"));
				}
				if (adminForm.getSuperMenu().equals("-1")) {
					errors.add("edit", new ActionError(
							"error.admin.menu.supermenu"));
				}
				errors = validation(adminForm.getOrderby(), "orderby", errors);

				if (errors.isEmpty()) {
					BeanUtils.copyProperties(menuVB, adminForm);
					if (adminForm.getMode().equals("add")) {
						if (menuCB.insertMenu(menuVB)) {
							request.setAttribute(STATUS,
									"Menu Inserted Sucessfully");
							resetToken(request);
						} else {
							request.setAttribute(STATUS,
									"Error In Menu Insert");
						}
						forward = mapping.findForward(STATUS);
					} else {
						if (menuCB.updateMenu(menuVB)) {
							request.setAttribute(STATUS,
									"Menu Updated Sucessfully");
							resetToken(request);
						} else {
							request.setAttribute(STATUS,
									"Error In Menu Updation");
						}
						forward = mapping.findForward(STATUS);
					}
					request.setAttribute("action",
							"/menuAdmin.do?method=initAdminMenu");
					request.setAttribute("title", "Menu Master");
					request.setAttribute(PTS, MNMPL);

				} else {
					setRequestValue(request, response, adminForm, MENU);
					saveMessages(request, errors);
					saveErrors(request, errors);
					request.setAttribute(PTS, MNMPL);
					forward = mapping.findForward(ADMNM);
				}

			} catch (Exception exception) {
				LogManager.debug(exception);
				exception.printStackTrace();
				throw new CommonBaseException(exception,CommonExceptionConstants.OTHER_ERROR);
			} finally {
				LogManager.debug("MenuAdmin Controller save method() - Exit");
				LogManager.popRemove(); // Should be the last statement
			}
		} else {
			request.setAttribute(PTS, "initMenu");
			forward = mapping.findForward(ADMNM);
		}

		return forward;
	}

	public ActionForward ajax(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws CommonBaseException,
			IOException {
		final MenuAdminForm adminForm = (MenuAdminForm) form;
		final PrintWriter out = response.getWriter();

		try {
			LogManager.push("MenuAdmin Controller Ajax method() - Enter");

			final AdminMenuCB menuCB = new AdminMenuCB();
			final Map map = menuCB.getMenuMap(adminForm.getType(), true);

			StringBuffer menu = new StringBuffer(16);
			final Set keys = map.keySet();
			for (final Iterator i = keys.iterator(); i.hasNext();) {
				final String key = (String) i.next();
				final String value = (String) map.get(key);
				menu.append(key).append(',').append(value).append('#');
			}
			menu = new StringBuffer(menu.toString().substring(0, menu.lastIndexOf("#")));
			out.print(menu);

		} catch (Exception exception) {
			LogManager.debug(exception);
			throw new CommonBaseException(exception,
					CommonExceptionConstants.OTHER_ERROR);
		} finally {
			LogManager.debug("MenuAdmin Controller Ajax method() - Exit");
			LogManager.popRemove(); // Should be the last statement
		}
		return null;
	}

	public void setRequestValue(final HttpServletRequest request,
			final HttpServletResponse response, final MenuAdminForm adminForm,
			final String value) throws CommonBaseException {

		try {
			LogManager.push("setRequestValue method() - Enter");

			if (MENU.equals(value)) {
				final AdminMenuCB menuCB = new AdminMenuCB();
				final Map map = menuCB.getMenuMap(adminForm.getType(), true);
				request.setAttribute("menuMapList", map);
			}

		} catch (Exception e) {
			LogManager.debug(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		} finally {
			LogManager.debug("setRequestValue method() - Exit");
			LogManager.popRemove();
		}
	}

	private ActionErrors validation(final String value, final String field,
			final ActionErrors errors) {
		if (value == null || value.length() < 1) {
			errors.add(field, new ActionError("error.admin.menu." + field));
		}
		return errors;
	}
}