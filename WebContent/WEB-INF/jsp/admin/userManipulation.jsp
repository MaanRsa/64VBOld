<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="../../displaytag.tld" prefix="display"%>
<%@ taglib uri="../../c.tld" prefix="c"%>
<%@ page isELIgnored ="false" %>
<script type="text/javascript">

function submitForm(val){
	document.getElementById("method").value = val;
	document.getElementById("mainForm").submit();
	return false;
}

function cancelForm(val){
	document.getElementById("method").value = val;
	document.getElementById("mainForm").action="<%=request.getContextPath()%>/menu.do";	
	document.getElementById("mainForm").submit();
	return false;
}

</script>

<html:form action="/userManipulation" styleId="mainForm">
	<logic:equal value="userManipulation" name="partToShow">
		<%--
		Start of User Manipulation part
	--%>
		<table width="585" border="0" cellspacing="0" cellpadding="1"
			align="center">
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="header" width="35">
					<img src="images/subsid_ic.gif" width="32" height="32">
				</td>
				<td class="header" width="389">
					<bean:message key="admin.logincreation.master.title" />
				</td>
			</tr>
			<tr>
				<td colspan="2" class="error">
					<html:messages id="message" message="true">
						<li>
							<bean:write name="message" />
						</li>
					</html:messages>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="tableBackGround">
					<table width="100%" border="0" cellspacing="1" cellpadding="3"
						bgcolor="#CCCCCC">
						<tr class="tableHead">
							<td width="250">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.id" />
							</td>
							<td class="tableContent">
								<html:text property="id" styleId="id" readonly="true" size="4" />
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.name" />
							</td>
							<td class="tableContent">
								<html:text property="userName" styleId="userName" />
							</td>
						</tr>
						<logic:equal value="edit" property="mode"
							name="userManipulationForm">
							<tr>
								<td class="tableContent">
									<bean:message key="admin.logincreation.master.loginID" />
								</td>
								<td class="tableContent">
									<bean:write property="loginID" name="userManipulationForm" />
								</td>
							</tr>
						</logic:equal>
						<logic:equal value="add" property="mode"
							name="userManipulationForm">
							<tr>
								<td class="tableContent">
									<bean:message key="admin.logincreation.master.loginID" />
								</td>
								<td class="tableContent">
									<html:text property="loginID" styleId="loginID" />
								</td>
							</tr>
							<tr>
								<td class="tableContent">
									<bean:message key="admin.logincreation.master.password" />
								</td>
								<td class="tableContent">
									<html:password property="password" styleId="password" />
								</td>
							</tr>
							<tr>
								<td class="tableContent">
									<bean:message key="admin.logincreation.master.rePassword" />
								</td>
								<td class="tableContent">
									<html:password property="rePassword" styleId="rePassword" />
								</td>
							</tr>
						</logic:equal>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.type" />
							</td>
							<td class="tableContent">
								<html:select property="userType" styleId="userType" onchange="fnGetMenu(this.value)">
									<html:optionsCollection name="userTypeList" value="key"
										label="value" />
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.status" />
							</td>
							<td class="tableContent">
								<html:radio property="status" value="1" />
								Active
								<html:radio property="status" value="0" />
								Deactive
							</td>
						</tr>
						
						
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" height="47">
					<input type="button" onclick="javascript: submitForm('save')"
						value="Save" class="button">
					<input type="button" value="Cancel" onClick="javascript: submitForm('initUser')"
						class="button">
				</td>
			</tr>
		</table>
		<%--
		End of User Manipulation part
	--%>
	</logic:equal>
	<logic:equal value="initUser" name="partToShow">
		<%--
		Start of User Creation part
	--%>
		<table width="585" border="0" cellspacing="0" cellpadding="1"
			align="center">
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="header" width="35">
					<img src="images/subsid_ic.gif" width="32" height="32">
				</td>
				<td class="header" width="389">
					<bean:message key="admin.logincreation.master.title" />
				</td>
			</tr>
			<tr>
				<td colspan="2" class="error">
					<html:messages id="message" message="true">
						<li>
							<bean:write name="message" />
						</li>
					</html:messages>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="tableBackGround">
					<table width="100%" border="0" cellspacing="1" cellpadding="3"
						bgcolor="#CCCCCC">
						<tr class="tableHead">
							<td width="250">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.loginID" />
							</td>
							<td class="tableContent">
								<html:select property="loginID" styleId="loginID">
									<html:optionsCollection name="loginIDList" value="key"
										label="value" />
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="tableContent" colspan="2" align="center">
								<input type="button" onclick="submitForm('edit')" value="Edit"
									class="button">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" height="47">
					<input type="button" onclick="submitForm('newUser')"
						value="New User" class="button">
					<input type="button" value="Cancel"
						onClick="javascript: cancelForm('adminMenu')" class="button">
				</td>
			</tr>
		</table>
		<%--
		End of User Creation part
	--%>
	</logic:equal>
	<logic:equal value="resetPassword" name="partToShow">
		<%--
		Start of User Creation part
	--%>
		<table width="585" border="0" cellspacing="0" cellpadding="1"
			align="center">
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="header" width="35">
					<img src="images/subsid_ic.gif" width="32" height="32">
				</td>
				<td class="header" width="389">
					<bean:message key="admin.logincreation.resetpwd.title" />
				</td>
			</tr>
			<tr>
				<td colspan="2" class="error">
					<html:messages id="message" message="true">
						<li>
							<bean:write name="message" />
						</li>
					</html:messages>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="tableBackGround">
					<table width="100%" border="0" cellspacing="1" cellpadding="3"
						bgcolor="#CCCCCC">
						<tr class="tableHead">
							<td width="250">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<%--<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.oldpassword" />
							</td>
							<td class="tableContent">
								<html:password property="oldPassword" styleId="oldPassword" />
							</td>
						</tr>
						--%>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.password" />
							</td>
							<td class="tableContent">
								<html:password property="password" styleId="password" />
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.rePassword" />
							</td>
							<td class="tableContent">
								<html:password property="rePassword" styleId="rePassword" />
								<html:hidden property="loginID" styleId="loginID" />
								
								
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" height="47">
					<input type="button" onclick="javascript: submitForm('resetPwd')"
						value="Change" class="button">
					<input type="button" value="Cancel" onClick="javascript: submitForm('userList')"
						class="button">
				</td>
			</tr>
		</table>
		<%--
		End of User Creation part
	--%>
	</logic:equal>
	<logic:equal value="userList" name="partToShow">
		<%--
		Start of User List part
	--%>
		<table width="585" border="1" cellspacing="0" cellpadding="1"
			align="center">
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="2" class="tableBackGround">
					<table width="100%" border="0" cellspacing="1" cellpadding="3"
						bgcolor="#CCCCCC">
						<tr class="tableHead">
							<td width="250">
								<bean:message key="admin.usermanipulation.searchTitle" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="tableContent" width="40%">
								<bean:message key="admin.usermanipulation.searchOn" />
								<html:select property="searchFor" styleId="searchFor">
									<html:optionsCollection name="searchForList" value="key"
										label="value" />
								</html:select>
							</td>
							<td class="tableContent" width="40%">
								<bean:message key="admin.usermanipulation.searchFor" />
								<html:text property="searchOn" styleId="searchOn" />
							</td>
							<td class="tableContent" width="20%">
								<input type="button"
									onclick="javascript: submitForm('userList')" value="Search"
									class="button">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="header" width="35">
					<img src="images/subsid_ic.gif" width="32" height="32">
				</td>
				<td class="header" width="389">
					<bean:message key="admin.usermanipulation.title" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="2" class="tableBackGround">
					<div id="display">
						<display:table style="backcolor:#cccccc;" name="result"
							pagesize="10" requestURI="/userManipulation.do" class="table"
							uid="row" id="record">
							<display:setProperty name="paging.banner.one_item_found" value="" />
							<display:setProperty name="paging.banner.one_items_found"
								value="" />
							<display:setProperty name="paging.banner.all_items_found"
								value="" />
							<display:setProperty name="paging.banner.some_items_found"
								value="" />
							<display:setProperty name="paging.banner.placement"
								value="bottom" />
							<display:setProperty name="paging.banner.onepage" value="" />

							<display:column sortable="true" title="S.No" property="id" />
							<display:column sortable="true" title="Login ID"
								property="loginID" />
							<display:column sortable="true" title="User Name"
								property="userName" />
							<display:column sortable="true" title="Status">
								<logic:equal name="record" property="status" value="1">Active</logic:equal>
								<logic:equal name="record" property="status" value="0">DeActive</logic:equal>
							</display:column>
							<display:column style="text-align: center;" sortable="true"
								title="Edit">
								<bean:define id="index" name="record" property="id" />
								<a
									href="<%=request.getContextPath()%>/userManipulation.do?fromList=1&method=edit&loginID=<%=index%>">Edit
								</a>
							</display:column>
							<display:column style="text-align: center;" sortable="true"
								title="Password">
								<bean:define id="index" name="record" property="id" />
								<a
									href="<%=request.getContextPath()%>/userManipulation.do?fromList=1&method=initResetPwd&loginID=<%=index%>">Change
								</a>
							</display:column>
							<display:column style="text-align: center;" sortable="true"
								title="Menu">
								<bean:define id="index" name="record" property="id" />
								<bean:define id="loginIDs" name="record" property="loginID" />
								<bean:define id="type" name="record" property="userType" />
								<a
									href="<%=request.getContextPath()%>/userManipulation.do?fromList=1&method=initMenuAllocate&userType=<%=type%>&id=<%=index%>&loginID=<%=loginIDs%>">Allocated
								</a>
							</display:column>
							
						</display:table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
		</table>
		<%--
		End of User List part
	--%>
	</logic:equal>
	<logic:equal value="menuAllocate" name="partToShow">
		<%--
		Start of Menu Allocate part
	--%>
		<table width="585" border="0" cellspacing="0" cellpadding="1"
			align="center">
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="header" width="35">
					<img src="images/subsid_ic.gif" width="32" height="32">
				</td>
				<td class="header" width="389">
					<bean:message key="admin.menu.allocation.title" />
				</td>
			</tr>
			<tr>
				<td colspan="2" class="error">
					<html:messages id="message" message="true">
						<li>
							<bean:write name="message" />
						</li>
					</html:messages>
				</td>
			</tr>
			<logic:notEmpty name="resultMsg">
				<tr>
					<td colspan="2" class="error">
						<bean:write name="resultMsg" />
					</td>
				</tr>
			</logic:notEmpty>
			<tr>
				<td colspan="2" class="tableBackGround">
					<table width="100%" border="0" cellspacing="1" cellpadding="3"
						bgcolor="#CCCCCC">
						<tr class="tableHead">
							<td width="250">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<logic:notEmpty name="menuList">
							<tr>
								<td class="tableContent" colspan="2">
									<bean:message key="admin.menu.allocation.choose" />
								</td>
							</tr>
							<tr>
								<td class="tableContent" colspan="2">
									<ul>
										<c:forEach var="main" items="${menuList}">
											<li>
												<bean:define id="id" property="id" name="main" />
												<html:multibox property="menuIDs" value='<%=(String) id%>' />
												<c:out value="${main.content}" />
												<c:if test="${main.list!=null}">
													<ul>
														<c:forEach var="sub" items="${main.list}">
															<li>
																<bean:define id="id1" property="id" name="sub" />
																<html:multibox property="menuIDs"
																	value='<%=(String) id1%>' />
																<c:out value="${sub.content}" />
																<c:if test="${sub.list!=null}">
																	<ul>
																		<c:forEach var="subsub" items="${sub.list}">
																			<li>
																				<bean:define id="id2" property="id" name="subsub" />
																				<html:multibox property="menuIDs"
																					value='<%=(String) id2%>' />
																				<c:out value="${subsub.content}" />
																			</li>
																		</c:forEach>
																	</ul>
																</c:if>
															</li>
														</c:forEach>
													</ul>
												</c:if>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</logic:notEmpty>.....
						<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.status" />
							</td>
							<td class="tableContent">
								<html:radio property="status" value="1" />
								Active
								<html:radio property="status" value="0" />
								Deactive
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" height="47">
					<input type="button"
						onclick="javascript: submitForm('menuAllocate')" value="Save"
						class="button">
					<input type="button" onclick="javascript: submitForm('userList')"
						value="Cancel" class="button">
				</td>
			</tr>
		</table>
		<%--
		End of Menu Allocate part
	--%>
	</logic:equal>
		<logic:equal value="stateAllocate" name="partToShow">
		<%--
		Start of State Allocate part
	--%>
		<table width="585" border="0" cellspacing="0" cellpadding="1"
			align="center">
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="header" width="35">
					<img src="images/subsid_ic.gif" width="32" height="32">
				</td>
				<td class="header" width="389">
					<bean:message key="admin.state.allocation.title" />
				</td>
			</tr>
			<tr>
				<td colspan="2" class="error">
					<html:messages id="message" message="true">
						<li>
							<bean:write name="message" />
						</li>
					</html:messages>
				</td>
			</tr>
			<logic:notEmpty name="resultMsg">
				<tr>
					<td colspan="2" class="error">
						<bean:write name="resultMsg" />
					</td>
				</tr>
			</logic:notEmpty>
			<tr>
				<td colspan="2" class="tableBackGround">
					<table width="100%" border="0" cellspacing="1" cellpadding="3"
						bgcolor="#CCCCCC">
						<tr class="tableHead">
							<td width="250">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<logic:notEmpty name="stateList">
							<tr>
								<td class="tableContent" colspan="2">
									<bean:message key="admin.state.allocation.choose" />
								</td>
							</tr>
							<tr>
								<td class="tableContent" colspan="2">
									<ul>
										<c:forEach var="main" items="${stateList}">
											<li>
												<bean:define id="id" property="stateCode" name="main" />
												<html:multibox property="stateCode" value='<%=(String) id%>' />
												<c:out value="${main.stateName}" />
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</logic:notEmpty>.....
						<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.status" />
							</td>
							<td class="tableContent">
								<html:radio property="status" value="1" />
								Active
								<html:radio property="status" value="0" />
								Deactive
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" height="47">
					<input type="button"
						onclick="javascript: submitForm('stateAllocate')" value="Save"
						class="button">
					<input type="button" onclick="javascript: submitForm('userList')"
						value="Cancel" class="button">
				</td>
			</tr>
		</table>
		<%--
		End of State Allocate part
	--%>
	</logic:equal>
	<logic:equal value="districtAllocate" name="partToShow">
		<%--
		Start of District Allocate part
	--%>
		<table width="585" border="0" cellspacing="0" cellpadding="1"
			align="center">
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="header" width="35">
					<img src="images/subsid_ic.gif" width="32" height="32">
				</td>
				<td class="header" width="389">
					<bean:message key="admin.district.allocation.title" />
				</td>
			</tr>
			<tr>
				<td colspan="2" class="error">
					<html:messages id="message" message="true">
						<li>
							<bean:write name="message" />
						</li>
					</html:messages>
					<logic:empty name="districtList">
						<li>
							Please Allocate State First
						</li>
					</logic:empty>
				</td>
			</tr>
			<logic:notEmpty name="resultMsg">
				<tr>
					<td colspan="2" class="error">
						<bean:write name="resultMsg" />
					</td>
				</tr>
			</logic:notEmpty>
			<tr>
				<td colspan="2" class="tableBackGround">
					<table width="100%" border="0" cellspacing="1" cellpadding="3"
						bgcolor="#CCCCCC">
						<tr class="tableHead">
							<td width="250">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<logic:notEmpty name="districtList">
							<tr>
								<td class="tableContent" colspan="2">
									<bean:message key="admin.district.allocation.choose" />
								</td>
							</tr>
							<tr>
								<td class="tableContent" colspan="2">
									<ul>
										<c:forEach var="main" items="${districtList}">
											<li>
												<bean:define id="id" property="districtCode" name="main" />
												<html:multibox property="districtCode" value='<%=(String) id%>' />
												<c:out value="${main.districtName}" />
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
							<tr>
								<td class="tableContent">
									<bean:message key="admin.logincreation.master.status" />
								</td>
								<td class="tableContent">
									<html:radio property="status" value="1" />
									Active
									<html:radio property="status" value="0" />
									Deactive
								</td>
							</tr>
						</logic:notEmpty>.....						
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" height="47">
				<logic:notEmpty name="districtList">
					<input type="button"
						onclick="javascript: submitForm('districtAllocate')" value="Save"
						class="button">
				</logic:notEmpty>
					<input type="button" onclick="javascript: submitForm('userList')"
						value="Cancel" class="button">
				</td>
			</tr>
		</table>
		<%--
		End of District Allocate part
	--%>
	</logic:equal>
	<logic:equal value="adminMenuAllocate" name="partToShow">
		<%--
		Start of Menu Allocate part
	--%>
		<table width="585" border="0" cellspacing="0" cellpadding="1"
			align="center">
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="header" width="35">
					<img src="images/subsid_ic.gif" width="32" height="32">
				</td>
				<td class="header" width="389">
					<bean:message key="admin.menu.allocation.title" />
				</td>
			</tr>
			<tr>
				<td colspan="2" class="error">
					<html:messages id="message" message="true">
						<li>
							<bean:write name="message" />
						</li>
					</html:messages>
				</td>
			</tr>
			<logic:notEmpty name="resultMsg">
				<tr>
					<td colspan="2">
						<bean:write name="resultMsg" />
					</td>
				</tr>
			</logic:notEmpty>
			<tr>
				<td colspan="2" class="tableBackGround">
					<table width="100%" border="0" cellspacing="1" cellpadding="3"
						bgcolor="#CCCCCC">
						<tr class="tableHead">
							<td width="250">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<logic:notEmpty name="menuList">
							<tr>
								<td class="tableContent" colspan="2">
									<bean:message key="admin.menu.allocation.choose" />
								</td>
							</tr>
							<tr>
								<td class="tableContent" colspan="2">
									<logic:notEmpty name="menuList">
										<ul>
											<c:forEach var="main" items="${menuList}">
												<li>
													<bean:define id="id" property="id" name="main" />
													<html:multibox property="menuIDs" value='<%=(String) id%>' />
													<c:out value="${main.content}" />
													<c:if test="${main.list!=null}">
														<ul>
															<c:forEach var="sub" items="${main.list}">
																<li>
																	<bean:define id="id1" property="id" name="sub" />
																	<html:multibox property="menuIDs"
																		value='<%=(String) id1%>' />
																	<c:out value="${sub.content}" />
																	<c:if test="${sub.list!=null}">
																		<ul>
																			<c:forEach var="subsub" items="${sub.list}">
																				<li>
																					<bean:define id="id2" property="id" name="subsub" />
																					<html:multibox property="menuIDs"
																						value='<%=(String) id2%>' />
																					<c:out value="${subsub.content}" />
																				</li>
																			</c:forEach>
																		</ul>
																	</c:if>
																</li>
															</c:forEach>
														</ul>
													</c:if>
												</li>
											</c:forEach>
										</ul>
									</logic:notEmpty>
								</td>
							</tr>
						</logic:notEmpty>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.logincreation.master.status" />
							</td>
							<td class="tableContent">
								<html:radio property="status" value="1" />
								Active
								<html:radio property="status" value="0" />
								Deactive
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" height="47">
					<input type="button"
						onclick="javascript: submitForm('menuAllocate')" value="Save"
						class="button">
					<input type="button" onclick="javascript: submitForm('userList')"
						value="Cancel" class="button">
				</td>
			</tr>
		</table>
		<%--
		End of Menu Allocate part
	--%>
	</logic:equal>
	<html:hidden property="fromList" styleId="fromList" />
	<html:hidden property="userType" styleId="userType" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="loginID" styleId="loginID" />
	<html:hidden property="method" styleId="method" />
	<html:hidden property="mode" styleId="mode" />
	<html:hidden property="searchOn" styleId="searchOn" />
	<html:hidden property="searchFor" styleId="searchFor" />
	<html:hidden property="loginSts" styleId="loginSts" />
</html:form>

