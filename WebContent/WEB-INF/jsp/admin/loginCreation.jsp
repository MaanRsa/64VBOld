<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>

<script type="text/javascript">
function submitForm(val){
	document.getElementById("method").value = val;
}
</script>

<html:form action="/loginCreation">
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
						<logic:equal value="edit" property="mode" name="loginCreationForm">
							<tr>
								<td class="tableContent">
									<bean:message key="admin.logincreation.master.loginID" />
								</td>
								<td class="tableContent">
									<bean:write property="loginID" name="loginCreationForm" />
								</td>
							</tr>
						</logic:equal>
						<logic:equal value="add" property="mode" name="loginCreationForm">
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
								<html:select property="userType"
									onchange="fnGetMenu(this.value)" styleId="userType">
									<html:option value="-1">Select</html:option>
									<html:option value="admin">Admin</html:option>
									<html:option value="user">User</html:option>
									<html:option value="uploaduser">Upload User</html:option>
									<html:option value="investigateuser">Investigate User</html:option>
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
					<input type="submit" name="submit"
						onclick="javascript: submitForm('save')" value="Save"
						class="button">
					<input type="button" name="cancel" value="Cancel"
						onClick="history.back(-1);" class="button">
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
								<input type="submit" name="submit" onclick="submitForm('edit')"
									value="Edit" class="button">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" height="47">
					<input type="submit" name="submit"
						onclick="javascript: submitForm('newUser')" value="New User"
						class="button">
					<input type="button" name="cancel" value="Cancel"
						onClick="history.back(-1);" class="button">
				</td>
			</tr>
		</table>
		<%--
		End of User Creation part
	--%>
	</logic:equal>
	<html:hidden property="method" styleId="method" />
	<html:hidden property="mode" styleId="mode" />
</html:form>

