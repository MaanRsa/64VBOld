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
function cancelForm(val){
	document.getElementById("method").value = val;
	document.getElementById("mainForm").action="<%=request.getContextPath()%>/menu.do";	
}
function fnGetMenu(val){
	var http;
	var url = "<%=request.getContextPath()%>/menuAdmin.do?method=ajax&type="+val;
	http = getHTTPObj();
	//alert(url);
	http.open('Post',url ,true);
	http.onreadystatechange = function(){
	if(http.readyState == 4 && http.status == 200) {
		 var res = http.responseText;
		    if(res){
		    	//alert(res);
			    deletePrevious(document.getElementById("superMenu"));
		    	var temp = res.split("#");
				for (var i = 0; i < temp.length; ++i) {
					if (temp[0] == "") {
						addOption(document.getElementById("superMenu"), "Select", "-1");
					} else {
						var valTemp = temp[i].split(",");
						addOption(document.getElementById("superMenu"), valTemp[1], valTemp[0]);
					}
				}
		    }
		}
	}
	http.send(null);
}

function getHTTPObj() {
	var http;
	var browser = navigator.appName;
	if (window.XMLHttpRequest) {
		http = new XMLHttpRequest();
	} else {
		if (window.ActiveXObject) {
			http = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	return http;
}
function addOption(theSel, theText, theValue) {
	var newOpt = new Option(theText, theValue);
	var selLength = theSel.length;
	theSel.options[selLength] = newOpt;
}
function deletePrevious(theSel) {
	var selLength = theSel.length;
	var i;
	for (i = selLength - 1; i > 0; i--) {
		if (theSel.length > 0) {
			theSel.options[i] = null;
		}
	}
}

</script>
<html:form action="/menuAdmin.do" styleId="mainForm">
	<%--
		Start of Init Menu part
	--%>
	<logic:equal value="initMenu" name="partToShow">
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
					<bean:message key="admin.menu.master.title" />
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
								<bean:message key="admin.menu.master.type" />
							</td>
							<td class="tableContent">
								<html:select property="type" onchange="fnGetMenu(this.value)"
									styleId="type">
									<html:option value="0">Select</html:option>
									<html:option value="admin">admin</html:option>
									<html:option value="user">user</html:option>
									<html:option value="uploaduser">uploaduser</html:option>
									<html:option value="investigateuser">Investigate User</html:option>
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.menu.master.supermenu" />
							</td>
							<td class="tableContent">
								<html:select property="superMenu" styleId="superMenu">
									<html:option value="-1">Select</html:option>
									<html:optionsCollection name="menuMapList" value="key"
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
						onclick="javascript: submitForm('addNewMenu')" value="New Menu"
						class="button">
					<input type="submit" name="cancel" value="Cancel"
						onClick="javascript: cancelForm('adminMenu')" class="button">
				</td>
			</tr>
		</table>
	</logic:equal>
	<%--
		Start of Menu Creation part
	--%>
	<%--
		Start of Menu Creation part
	--%>
	<logic:equal value="menuManipulation" name="partToShow">
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
					<bean:message key="admin.menu.master.title" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<html:messages id="message" message="true">
						<li style="color: red;">
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
								<bean:message key="admin.menu.master.id" />
							</td>
							<td class="tableContent">
								<html:text property="id" styleId="id" readonly="true" size="4" />
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.menu.master.name" />
							</td>
							<td class="tableContent">
								<html:text property="name" styleId="name" />
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.menu.master.url" />
							</td>
							<td class="tableContent">
								<html:text property="url" styleId="url" />
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.menu.master.type" />
							</td>
							<td class="tableContent">
								<html:select property="type" onchange="fnGetMenu(this.value)"
									styleId="type">
									<html:option value="-1">Select</html:option>
									<html:option value="admin">admin</html:option>
									<html:option value="user">user</html:option>
									<html:option value="uploaduser">uploaduser</html:option>
									<html:option value="investigateuser">Investigate User</html:option>
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.menu.master.supermenu" />
							</td>
							<td class="tableContent">
								<html:select property="superMenu" styleId="superMenu">
									<html:option value="0">Head</html:option>
									<html:optionsCollection name="menuMapList" value="key"
										label="value" />
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.menu.master.orderby" />
							</td>
							<td class="tableContent">
								<html:text property="orderby" styleId="orderby" />
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.menu.master.remark" />
							</td>
							<td class="tableContent">
								<html:text property="remark" styleId="remark" />
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.menu.master.startDate" />
							</td>
							<td class="tableContent">
								<html:text property="startDate" styleId="startDate"
									readonly="true" size="12" />
								<a href="javascript:start.popup();"
									onClick="document.getElementById('startDate').focus()"><img
										src="<%=request.getContextPath()%>/images/cal.gif" width="16"
										height="16" border="0" alt="Click Here Pick up the date">
								</a>
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.menu.master.endDate" />
							</td>
							<td class="tableContent">
								<html:text property="endDate" styleId="endDate" readonly="true"
									size="12" />
								<a href="javascript:end.popup();"
									onClick="document.getElementById('endDate').focus()"><img
										src="<%=request.getContextPath()%>/images/cal.gif" width="16"
										height="16" border="0" alt="Click Here Pick up the date">
								</a>
							</td>
						</tr>
						<tr>
							<td class="tableContent">
								<bean:message key="admin.menu.master.status" />
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
						onclick="javascript: submitForm('save')" value="Submit"
						class="button">
					<input type="submit" name="cancel" value="Cancel"
						onClick="javascript: submitForm('initAdminMenu')" class="button">
				</td>
			</tr>
		</table>
		<script>
			var end = new calendar1(document.forms["menuAdminForm"].elements["endDate"]);
			end.year_scroll = true;
			end.time_comp = false;
			
			var start = new calendar1(document.forms["menuAdminForm"].elements["startDate"]);
			start.year_scroll = true;
			start.time_comp = false;
		</script>
	</logic:equal>
	<%--
		Start of Menu Creation part
	--%>
	<html:hidden property="method" styleId="method" />
	<html:hidden property="mode" styleId="mode" />
</html:form>
