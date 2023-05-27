<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"
	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
String path = request.getContextPath();
try{
%>
<script type="text/javascript"><!--

function callReversal(batch){
 	window.location.href ='<%=request.getContextPath()%>/reversalReceipt.do?method=transactionDetails&bid='+batch;
	return false;
}

function callResult(batch){
   	  	window.location.href ='<%=request.getContextPath()%>/upload3.do?method=transactionDetails2&batchid='+batch;
      	return false;
   }
function callNonMatchedResult(val){
   	  	window.location.href ='<%=request.getContextPath()%>/upload3.do?method=transactionDetails3&batchid='+val;
      	return false;
   }
function callRealized(batch,status)
{
  window.location.href ='<%=request.getContextPath()%>/upload3.do?method=realizedPolicy&batchid='+batch+'&status='+status;
      	return false;
}   
function processCitiPage(val,batchid){   
 
      	//document.mainForm.method.value = "processRecords";
      	//document.mainForm.batchid.value = batchid;
      	document.getElementById("method").value="processRecords";
      	document.getElementById("batchid").value=batchid;
      	document.getElementById("bankName").value='CIT';
      	//document.mainForm.bankName.value= 'CIT';
       	//document.mainForm.submit();
       	document.getElementById("mainForm").submit();
      	if( document.getElementById("uploadsts1").style.display == 'none' )
			document.getElementById("uploadsts1").style.display = 'block';
		else
			document.getElementById("uploadsts1").style.display = 'none';
		val.disabled=true;
      	document.getElementById("uploadFile").disabled = true;
      		
   }
   
   function processAxisPage(val,batchid){
       
      	//document.mainForm.method.value = "processRecords";
      	//document.mainForm.batchid.value = batchid;
      	document.getElementById("method").value="processRecords";
      	document.getElementById("batchid").value=batchid;
      	document.getElementById("bankName").value='AXB';
      	//document.mainForm.bankName.value= 'AXB';
       	//document.mainForm.submit();
       	document.getElementById("mainForm").submit();
      	if( document.getElementById("uploadsts1").style.display == 'none' )
			document.getElementById("uploadsts1").style.display = 'block';
		else
			document.getElementById("uploadsts1").style.display = 'none';
		val.disabled=true;
      	document.getElementById("uploadFile").disabled = true;
      		
   }
function processHDFCPage(val,batchid){
       
      	//document.mainForm.method.value = "processRecords";
      	//document.mainForm.batchid.value = batchid;
      	//document.mainForm.bankName.value= 'HDB';
       	//document.mainForm.submit();
       	document.getElementById("method").value="processRecords";
      	document.getElementById("batchid").value=batchid;
      	document.getElementById("bankName").value='HDB';
      	document.getElementById("mainForm").submit();
      	if( document.getElementById("uploadsts1").style.display == 'none' )
			document.getElementById("uploadsts1").style.display = 'block';
		else
			document.getElementById("uploadsts1").style.display = 'none';
      	val.disabled=true;
      		
   }
   
function processKotakPage(val,batchid){
	
   	document.getElementById("method").value="processRecords";
  	document.getElementById("batchid").value=batchid;
  	document.getElementById("bankName").value='KOT';
  	document.getElementById("mainForm").submit();
  	if( document.getElementById("uploadsts1").style.display == 'none' )
		document.getElementById("uploadsts1").style.display = 'block';
	else
		document.getElementById("uploadsts1").style.display = 'none';
  	val.disabled=true;
  		
}

function processHSBCPage(val,batchid){
       
      	//document.mainForm.method.value = "processRecords";
      	//document.mainForm.batchid.value = batchid;
      	//document.mainForm.bankName.value= 'HSB';
       	//document.mainForm.submit();
       	document.getElementById("method").value="processRecords";
      	document.getElementById("batchid").value=batchid;
      	document.getElementById("bankName").value='HSB';
      	document.getElementById("mainForm").submit();
      	
      	if( document.getElementById("uploadsts1").style.display == 'none' )
			document.getElementById("uploadsts1").style.display = 'block';
		else
			document.getElementById("uploadsts1").style.display = 'none';
      	val.disabled=true;
      		
   }

function processReceipt(val,batchid){
       
      	document.mainForm.method.value = "processRecords";
      	document.mainForm.batchid.value = batchid;
      	document.mainForm.bankName.value= 'ALL';
       	document.mainForm.submit();
      	if( document.getElementById("uploadsts1").style.display == 'none' )
			document.getElementById("uploadsts1").style.display = 'block';
		else
			document.getElementById("uploadsts1").style.display = 'none';
      	val.disabled=true;
      		
   }
   
   
 function processSCBPage(val,batchid){
       
      	//document.mainForm.method.value = "processRecords";
      	//document.mainForm.batchid.value = batchid;
      	//document.mainForm.bankName.value= 'SCB';
       	//document.mainForm.submit();
       	document.getElementById("method").value="processRecords";
      	document.getElementById("batchid").value=batchid;
      	document.getElementById("bankName").value='SCB';
      	document.getElementById("mainForm").submit();
       	
      	if( document.getElementById("uploadsts1").style.display == 'none' )
			document.getElementById("uploadsts1").style.display = 'block';
		else
			document.getElementById("uploadsts1").style.display = 'none';
      	val.disabled=true;
      		
   }	
 
function fnSubmit(val){
	document.getElementById("method").value = "Upload";	
	document.getElementById("mainForm").submit();
	if( document.getElementById("uploadsts1").style.display == 'none' )
		document.getElementById("uploadsts1").style.display = 'block';
	else
		document.getElementById("uploadsts1").style.display = 'none';
    val.disabled=true;
    document.getElementById("uploadFile").disabled = true;
        
}

function fnUserSubmit(val,valnew,prop){
   
	document.getElementById("method").value = valnew;
	document.getElementById(prop).value = val;
	document.getElementById("mainForm").submit();
	return false;
}
function cancel(val){

document.getElementById("uploadType").value=val;
document.getElementById("mainForm").action = "<%=request.getContextPath()%>/fileUpload.do?method=init";
document.getElementById("mainForm").submit();


}
--></script>

<html:form  action="/fileUpload" styleId="mainForm"  enctype="multipart/form-data">
<% String typename=(String)request.getAttribute("typename")==null?"":(String)request.getAttribute("typename");%>
	<html:messages id="message" message="true">
		<li style="color: red;">
			<bean:write name="message" />
		</li>
	</html:messages>
<logic:equal value="upload" name="partToShow">
		<%--
		Start of Upload part
	--%>
	   <%
  String typeid=(String)request.getAttribute("typeid");
  
  %> 
  
  <table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><table width="585" border="0" cellspacing="0" cellpadding="1" align="center">
          <tr>
            <td class="header" width="40" height="44"><img src="<%=request.getContextPath()%>/images/submitcut.gif" width="25" height="26"></td>
            <td class="header" width="389" height="44"> File Upload </td>
            <td class="s" width="150" height="44" align="center"><a href="Home.htm"></a></td>
          </tr>
        </table></td>
    </tr>
  </table>
  <table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center">&nbsp; Please use ASAP Utility and ensure the Excel header format before upload. </td>
    </tr>
   
    <logic:notEmpty name="error">
				<tr>
					<td colspan="2" style="color: red;">
						<bean:write name="error" />
						
					</td>
				</tr>
			</logic:notEmpty>
			
  </table>
  
<div id="uploadsts1" style="display:none">
	<table align="center">
	
		<tr align="center">
			<td colspan="2"><font color="#488AC7" size="4pt"><b>Upload In Progress</b></font></td>
		</tr>
		<tr>
			<td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
		</tr>
		<tr>
		
		
		</tr>
	</table>
</div>
  <table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><table width="585" border="0" cellspacing="0" cellpadding="1" align="center">
      
          <tr>
            <td height="185" colspan="2" ><table width="100%"  border="0" cellpadding="3" cellspacing="0" bgcolor="#CCCCCC">
                <tr>
                  <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="3" bgcolor="#CCCCCC">
                      <tr>
                        <td class="tbn" height="21" align="left">
                        <% if(typeid.equalsIgnoreCase("101")){ %>
                        Receipt 
                        <% }  else if(typeid.equalsIgnoreCase("102")){ %>
                        CITI Bank 
                        <% }  else if(typeid.equalsIgnoreCase("103")){ %>
                        HDFC Bank
                        <% }  else if(typeid.equalsIgnoreCase("104")){ %>
                        Receipt Nos
                        <% }  else if(typeid.equalsIgnoreCase("105")){ %>
                        SCB 
                        <%}   else if(typeid.equalsIgnoreCase("106")){ %>
                        AXIS Bank
                        <%}   else if(typeid.equalsIgnoreCase("107")){ %>
                        Receipt Reversal
                        <% } else if(typeid.equalsIgnoreCase("108")){ %>
                        HSBC Bank
                        <% }else if(typeid.equalsIgnoreCase("109")) {%>
                        Policy Numbers
                        <%}else if(typeid.equalsIgnoreCase("115")){%>
                        KOTAK BANK
                       <% }%>
                        File Upload </td>
                        <td class="tbn" height="21" align="right"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr class="tabin">
                  <td width="31%"  height="85" align="right" class="t">
                  <bean:message key="file.upload.upload" /> </td>
                  <td width="69%" align="center" class="t">&nbsp;
                  <html:file property="uploadFile" styleId="uploadFile"></html:file>
                   </td>
                </tr>
                <logic:equal value="104" name="typeid" scope="request">
                <tr class="tabin">
                  <td width="31%"  height="85" align="right" class="t">
                  Do you want to update the status as realized </td>
                  <td width="69%" align="center" class="t">&nbsp;
                  <html:radio property="realizeStatus" value="N" />No
                  <html:radio property="realizeStatus" value="Y" />Yes
                  
                   </td>
                </tr>
                </logic:equal>
                <tr class="tabin">
                  <td  height="1" colspan="2"  align="center"><p> </p></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td colspan="2" align="center" height="2">&nbsp;
              
              <input type="button" name="Submit2" value="Submit" class="tbut" onClick="return fnSubmit(this)">
              <input type="button" name="button2" value="Cancel" onClick="return cancel('<%=typeid %>')" class="tbut">
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
  <table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center">&nbsp;</td>
    </tr>
  </table>
  <%--
		End of Course Upload part
	--%>
	</logic:equal>
	<logic:equal value="isDirectMovement" name="partToShow">
		<table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><table width="585" border="0" cellspacing="0" cellpadding="1" align="center">
          <tr>
            <td height="185" colspan="2" ><table width="100%"  border="0" cellpadding="3" cellspacing="0" bgcolor="#CCCCCC">
                <tr>
                  <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="3" bgcolor="#CCCCCC">
                      <tr>
                        <td class="tbn" height="21" align="left">
                        <display:table name="tranIdList" pagesize="20"
							requestURI="/fileUpload.do?method=Upload" class="table" uid="row" id="record">
							<display:setProperty name="paging.banner.one_item_found" value="" />
							<display:setProperty name="paging.banner.one_items_found" value="" />
							<display:setProperty name="paging.banner.all_items_found" value="" />
							<display:setProperty name="paging.banner.some_items_found" value="" />
							<display:setProperty name="paging.banner.placement" value="bottom" />
							<display:setProperty name="paging.banner.onepage" value="" />
							<display:column style="text-align:center;width:50px" title="Transaction Id" sortable="true">
								<bean:define id="transID" name="record" property="isDirect"></bean:define>
								<%String tranid = transID==null?"":transID.toString(); %>
								<html:radio name="record" property="isDirect" value="<%=tranid%>" /><%=tranid%>
							</display:column>
							<display:column sortable="true" style="text-align:center;width:100px" title="Click the button for Receipt Movement">
								<input type="button" name="Submit" value="Submit" class="tbut" onClick="return fnSubmit(this);">
							</display:column>
						</display:table>                        
                        </td>
                      </tr>
                    </table></td>
                </tr>
                <tr class="tabin">
                  <td  height="1" colspan="2"  align="center"><p> </p></td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
  </table>
</logic:equal>
	<logic:equal value="moveXgen" name="partToShow">
  <%
  String status=(String)request.getAttribute("status");
  String batchid=(String)request.getAttribute("batchid");
  String typeid1=(String)request.getAttribute("typeid");
  %>   
 <div id="uploadsts1" style="display:none">
	<table align="center">
	
		<tr align="center">
			<td colspan="2"><font color="#488AC7" size="4pt"><b>Processing..</b></font></td>
		</tr>
		
		<tr>
			<td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
		</tr>
	</table>
</div>
  <table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><table width="585" border="0" cellspacing="0" cellpadding="1" align="center">
          <tr>
            <td height="185" colspan="2" ><table width="100%"  border="0" cellpadding="3" cellspacing="0" bgcolor="#CCCCCC">
                <tr>
                  <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="3" bgcolor="#CCCCCC">
                      <tr>
                        <td class="tbn" height="21" align="left"><%=typename%> - Transaction status</td>
                        <td class="tbn" height="21" align="right"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr class="tabin">                 
                  <td width="100%" align="center" class="t">&nbsp;                  
                  <%=status %>
                 <% if(typeid1.equalsIgnoreCase("104")) {%>  <br/>
                  Not RECT Records-
						<bean:write name="NotRect" scope="request" />&nbsp;&nbsp;&nbsp;
						<br />
						&nbsp;&nbsp;Matched Records-
						<bean:write name="Matched" scope="request" />&nbsp;
						<br/>
						Non Matched Records-
						<bean:write name="NotMatched"
								scope="request" />&nbsp;&nbsp;&nbsp;
				<%} if(typeid1.equalsIgnoreCase("107")) {%>
        	    <br/>Records Available -<a href="#" onclick="return callReversal('<%=batchid %>');"> 
        	    <bean:write name="Available"
								scope="request" />
        	    </a>&nbsp;<br/>
				Records Not Available -
				<bean:write name="NotAvailable"
								scope="request" />
				<%} if(typeid1.equalsIgnoreCase("109")){%>
				    <br/>Realized:  <bean:write name="Realized" scope="request" /><br/>
				    Returned:  <bean:write name="Returned" scope="request" /><br/>
				    NotKnown:  <bean:write name="NotKnown" scope="request" /><br/>
				    Not Available in xgen:<bean:write name="NotAvailable" scope="request" /><br/>
				<%} %>
                  <br><br>                  
                   </td>
                </tr>
                <tr class="tabin">
                  <td  height="1" colspan="2"  align="center"><p> </p></td>
                </tr>
              </table></td>
          </tr>         				
          <tr>
          <td  colspan="2" align="center">       
          <%if (typeid1.equalsIgnoreCase("102")) {%>
           <input type="button" class="tbut" property="Process" value="Process" onclick = "return processCitiPage(this,'<%=batchid %>')" />	
           <%} else if(typeid1.equalsIgnoreCase("103")){%>
            <input type="button" class="tbut" property="Process" value="Process" onclick = "return processHDFCPage(this,'<%=batchid %>')" />
            <%} else if(typeid1.equalsIgnoreCase("108")){%>
            <input type="button" class="tbut" property="Process" value="Process" onclick = "return processHSBCPage(this,'<%=batchid %>')" />		
            <%} else if(typeid1.equalsIgnoreCase("105")){%>
            <input type="button" class="tbut" property="Process" value="Process" onclick = "return processSCBPage(this,'<%=batchid %>')" />		
           <%} else if(typeid1.equalsIgnoreCase("106")){%>
            <input type="button" class="tbut" property="Process" value="Process" onclick = "return processAxisPage(this,'<%=batchid %>')" />
           <%} else if(typeid1.equalsIgnoreCase("104")) {%>
        	<a href="#" onclick="return callResult('<%=batchid %>');">Click here to
				Download Matched Data</a>&nbsp;<br/>
				<a href="#" onclick="return callNonMatchedResult('<%=batchid %>');">Click here to
				Download Non Matched Data</a>
			<%} else if(typeid1.equalsIgnoreCase("109")){%>
			<div align="center">
			<logic:greaterThan name="Realized" value="0" scope="request">
			<a href="#" onclick="return callRealized('<%=batchid %>','Realized');" >Click here to download Realized cases</a><br/>
			</logic:greaterThan>
			<logic:greaterThan name="Returned" value="0" scope="request">
			<a href="#" onclick="return callRealized('<%=batchid %>','Returned');">Click here to download Returned cases</a><br/>
			</logic:greaterThan>
			<logic:greaterThan name="NotKnown" value="0" scope="request">
			<a href="#" onclick="return callRealized('<%=batchid %>','Not Known');">Click here to download Status notknown cases</a><br/>
			</logic:greaterThan>
		   </div>
			<%} else if(typeid1.equalsIgnoreCase("115")){%>
            <input type="button" class="tbut" property="Process" value="Process" onclick = "return processKotakPage(this,'<%=batchid %>')" />		
            <%} %>
           </td> 
		</tr>		
        </table></td>
    </tr>
  </table>
  <table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center">&nbsp;</td>
    </tr>
  </table>
</logic:equal>	
	<logic:equal value="XgenStatus" name="partToShow">
  <%String status1=(String)request.getAttribute("status");
  String typeid2=(String)request.getAttribute("typeid");
  %> <table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><table width="585" border="0" cellspacing="0" cellpadding="1" align="center">
          <tr>
            <td height="185" colspan="2" ><table width="100%"  border="0" cellpadding="3" cellspacing="0" bgcolor="#CCCCCC">
                <tr>
                  <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="3" bgcolor="#CCCCCC">
                      <tr>
                        <td class="tbn" height="21" align="left"><%=typename%> - Movement Status </td>
                        <td class="tbn" height="21" align="right"><a href="#" onClick="window.open('help.htm#per','help','width=400, height=175, scrollbars=yes,toolbar=no')"><img src="images/help.gif" width="38" height="15" border="0" hspace="3"></a></td>
                      </tr>
                    </table></td>
                </tr>
                <tr class="tabin">
                 
                  <td width="100%" align="center" class="t">&nbsp;
                  
                  <%=status1 %>
                   </td>
                </tr>
                <tr class="tabin">
                  <td  height="1" colspan="2"  align="center"><p> </p></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td colspan="2" align="center" height="2">&nbsp;
              
          
              <input type="button" name="button2" value="Submit" onClick="return cancel('<%=typeid2 %>')" class="tbut">
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
  <table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center">&nbsp;</td>
    </tr>
  </table>
   <%--
		End of Course Upload part
	--%>
	</logic:equal>	
		<logic:equal value="triggerXgenStatus" name="partToShow">
		<%--
		Start of Upload part
	--%>
 	 <%
		String pendingCount = request.getAttribute("pendingCount")==null?"0":request.getAttribute("pendingCount").toString();
	 %> <table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><table width="585" border="0" cellspacing="0" cellpadding="1" align="center">
          <tr>
            <td height="185" colspan="2" ><table width="100%"  border="0" cellpadding="3" cellspacing="0" bgcolor="#CCCCCC">
                <tr>
                  <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="3" bgcolor="#CCCCCC">
                      <tr>
                        <td class="tbn" height="21" align="right">Total Pending Records - </td>
                        <td class="tbn" height="21" align="left">&nbsp;<%=pendingCount %></td>
                      </tr>
                      <%
                      	if(!("0".equals(pendingCount)))
                      	{
                       %>
                      <tr class="tabin">                 
                  <td colspan="2" align="center" class="t">&nbsp;<br>
                  <font color="red">Press submit for xgen movement </font>
                  
                   </td>
                </tr>
                <%} %>
                    </table></td>
                </tr>
                <tr class="tabin">
                  <td  height="1" colspan="2"  align="center"><p> </p></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td colspan="2" align="center" height="2">&nbsp;                        
              <input type="button" name="button2" value="Submit" onClick="return fnSubmit('','MasterClearMove')" class="tbut">
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
  <table width="779" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center">&nbsp;</td>
    </tr>
  </table>
   <%--
		End of Course Upload part
	--%>
	</logic:equal>	
<logic:equal name="PartToShow" value="ProcessResult">
<div id="uploadsts2" style="display:none">
	<table align="center">	
		<tr align="center">
			<td colspan="2"><font color="#488AC7" size="4pt"><b>Processing..</b></font></td>
		</tr>		
		<tr>
			<td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
		</tr>
	</table>
</div>
	<table  border="0" cellpadding="5" cellspacing="0"  width="80%" height="60%" align="center"> 	    
		<tr style="height:15px">
    <td>&nbsp;</td>
    </tr>
 		<tr class="tabin">
			<td class="title" width="100%" height="10" align="center" colspan= "2">Process Result</td>
		</tr>
		<tr class="tabin">			
			<td class="tb" width="100%" height="20" align="center">
				Total No. of Records Matched In Last Transaction&nbsp;:&nbsp;<span class="st"><bean:write name="TotalRecords" scope="request" />&nbsp;&nbsp;&nbsp;</span>
			</td>
		</tr>
		<tr style="height:80px">
    <td>&nbsp;</td>
    </tr>
	</table>
</logic:equal>
<html:hidden property="mode" styleId="mode" />
<html:hidden property="method" styleId="method" value="Upload"/>
<html:hidden property="batchid" styleId="batchid" />
<html:hidden property="uploadType" styleId="uploadType" />
<html:hidden property="bankName" styleId="bankName"/>
</html:form>
<%}catch(Exception e){e.printStackTrace();}%>