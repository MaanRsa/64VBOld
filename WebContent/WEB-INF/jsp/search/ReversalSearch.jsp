<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>ReversalSearch.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
function searchReceipt(){
    document.reversalFormBean.method.value="receiptSearch";
	document.reversalFormBean.action='<%=request.getContextPath() %>/reversal.do';
	document.reversalFormBean.submit();
	return false;
}
function searchCiti(){
    document.reversalFormBean.method.value="citiSearch";
	document.reversalFormBean.action='<%=request.getContextPath() %>/reversal.do';
	document.reversalFormBean.submit();
	return false;
}
function searchScb(){
    document.reversalFormBean.method.value="scbSearch";
	document.reversalFormBean.action='<%=request.getContextPath() %>/reversal.do';
	document.reversalFormBean.submit();
	return false;
}

function searchAxis(){
    document.reversalFormBean.method.value="axisSearch";
	document.reversalFormBean.action='<%=request.getContextPath() %>/reversal.do';
	document.reversalFormBean.submit();
	return false;
}
function searchHdfc(){
    document.reversalFormBean.method.value="hdfcSearch";
    document.reversalFormBean.action='<%=request.getContextPath() %>/reversal.do';
    document.reversalFormBean.submit();
    return false;
}
function searchHsbc(){
    document.reversalFormBean.method.value="hsbcSearch";
	document.reversalFormBean.action='<%=request.getContextPath() %>/reversal.do';
	document.reversalFormBean.submit();
	return false;
}
function goBack()
{
  window.history.back();
  return true;
}
function callReceiptReversals(val1){
		document.reversalFormBean.method.value = "receiptReversals";
       	document.reversalFormBean.depositdate.value = val1;
        document.reversalFormBean.submit();	
   }


function callCitiReversals(val1){
		document.reversalFormBean.method.value = "citiReversals";
       	document.reversalFormBean.depositdate.value = val1;
        document.reversalFormBean.submit();	
   }

function callScbReversals(val1){
		document.reversalFormBean.method.value = "scbReversals";
       	document.reversalFormBean.depositdate.value = val1;
        document.reversalFormBean.submit();	
   }
   
   
function callAxisReversals(val1){
		document.reversalFormBean.method.value = "axisReversals";
       	document.reversalFormBean.depositdate.value = val1;
        document.reversalFormBean.submit();	
   }

function callHsbcReversals(val1){
        document.reversalFormBean.method.value = "hsbcReversals";
        document.reversalFormBean.depositdate.value = val1;
        document.reversalFormBean.submit(); 
   }
function callHdfcReversals(val1){
		document.reversalFormBean.method.value = "hdfcReversals";
       	document.reversalFormBean.depositdate.value = val1;
        document.reversalFormBean.submit();	
   }

</script>
  </head>
  
  <body>
   <html:form action="/reversal.do">
   <logic:messagesPresent>
			<br />
			<table align="center" border="0" align="center" width="50%"
				cellpadding="1" cellspacing="1">

				<tr valign="top" align="center" style="height:5px">
					<td colspan="2">
						<font color="red"> <html:errors /> </font>
					</td>
				</tr>

			</table>
	</logic:messagesPresent>
	
	<logic:equal name="PartToShow" value="ReceiptReversalSearch">

			<table align="center" border="0" align="center" width="45%"
				cellpadding="1" cellspacing="1">

				<tr style="height:2px">
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" class="tbn" height="10" align="left">
						Search Receipt Reversals
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" height="10" align="left"></td>
				</tr>

				
				<tr>
					<td>
						<bean:message key="reversal.fromdate" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="fromDate" id="fromDate"
							readonly="true" />
						<a href="javascript:fromDate.popup();"
							onClick="document.getElementById('fromDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here to Pick up the date">
						</a> &nbsp;
						<script>
							var fromDate = new calendar1(document.forms[0].elements["fromDate"]);
							fromDate.year_scroll = true;
							fromDate.time_comp = false;
						</script>
					</td>

				</tr>
			    <tr>
					<td>
						<bean:message key="reversal.todate" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="toDate" id="toDate"
							readonly="true" />
						<a href="javascript:toDate.popup();"
							onClick="document.getElementById('toDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here Pick up the date">
						</a> &nbsp;
						<script>
							var toDate = new calendar1(document.forms[0].elements["toDate"]);
							toDate.year_scroll = true;
							toDate.time_comp = false;
						</script>
					</td>

				</tr>


				<tr>
					<td colspan="2" align="center">
						<br />
						<input type="button" class="tbut" value="Submit" onclick="searchReceipt();" />

					</td>

				</tr>

			</table>
			<br />
		</logic:equal>
		
	<logic:equal name="PartToShow" value="CitiReversalSearch">

			<table align="center" border="0" align="center" width="45%"
				cellpadding="1" cellspacing="1">

				<tr style="height:2px">
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" class="tbn" height="10" align="left">
						Search CITI Reversals
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" height="10" align="left"></td>
				</tr>

				
				<tr>
					<td>
						<bean:message key="reversal.fromdate" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="fromDate" id="fromDate"
							readonly="true" />
						<a href="javascript:fromDate.popup();"
							onClick="document.getElementById('fromDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here Pick up the date">
						</a> &nbsp;
						<script>
							var fromDate = new calendar1(document.forms[0].elements["fromDate"]);
							fromDate.year_scroll = true;
							fromDate.time_comp = false;
						</script>
					</td>

				</tr>
			    <tr>
					<td>
						<bean:message key="reversal.todate" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="toDate" id="toDate"
							readonly="true" />
						<a href="javascript:toDate.popup();"
							onClick="document.getElementById('toDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here Pick up the date">
						</a> &nbsp;
						<script>
							var toDate = new calendar1(document.forms[0].elements["toDate"]);
							toDate.year_scroll = true;
							toDate.time_comp = false;
						</script>
					</td>

				</tr>


				<tr>
					<td colspan="2" align="center">
						<br />
						<input type="button" class="tbut" value="Submit" onclick="searchCiti();" />

					</td>

				</tr>

			</table>
			<br />
		</logic:equal>
		<logic:equal name="PartToShow" value="ScbReversalSearch">

			<table align="center" border="0" align="center" width="45%"
				cellpadding="1" cellspacing="1">

				<tr style="height:2px">
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" class="tbn" height="10" align="left">
						Search SCB Reversals
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" height="10" align="left"></td>
				</tr>

				
				<tr>
					<td>
						<bean:message key="reversal.fromdate" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="fromDate" id="fromDate"
							readonly="true" />
						<a href="javascript:fromDate.popup();"
							onClick="document.getElementById('fromDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here Pick up the date">
						</a> &nbsp;
						<script>
							var fromDate = new calendar1(document.forms[0].elements["fromDate"]);
							fromDate.year_scroll = true;
							fromDate.time_comp = false;
						</script>
					</td>

				</tr>
			    <tr>
					<td>
						<bean:message key="reversal.todate" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="toDate" id="toDate"
							readonly="true" />
						<a href="javascript:toDate.popup();"
							onClick="document.getElementById('toDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here Pick up the date">
						</a> &nbsp;
						<script>
							var toDate = new calendar1(document.forms[0].elements["toDate"]);
							toDate.year_scroll = true;
							toDate.time_comp = false;
						</script>
					</td>

				</tr>


				<tr>
					<td colspan="2" align="center">
						<br />
						<input type="button" class="tbut" value="Submit" onclick="searchScb();" />

					</td>

				</tr>

			</table>
			<br />
		</logic:equal>
		<logic:equal name="PartToShow" value="AxisReversalSearch">

			<table align="center" border="0" align="center" width="45%"
				cellpadding="1" cellspacing="1">

				<tr style="height:2px">
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" class="tbn" height="10" align="left">
						Search AXIS Bank Reversals
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" height="10" align="left"></td>
				</tr>

				
				<tr>
					<td>
						<bean:message key="reversal.fromdate" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="fromDate" id="fromDate"
							readonly="true" />
						<a href="javascript:fromDate.popup();"
							onClick="document.getElementById('fromDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here Pick up the date">
						</a> &nbsp;
						<script>
							var fromDate = new calendar1(document.forms[0].elements["fromDate"]);
							fromDate.year_scroll = true;
							fromDate.time_comp = false;
						</script>
					</td>

				</tr>
			    <tr>
					<td>
						<bean:message key="reversal.todate" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="toDate" id="toDate"
							readonly="true" />
						<a href="javascript:toDate.popup();"
							onClick="document.getElementById('toDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here Pick up the date">
						</a> &nbsp;
						<script>
							var toDate = new calendar1(document.forms[0].elements["toDate"]);
							toDate.year_scroll = true;
							toDate.time_comp = false;
						</script>
					</td>

				</tr>


				<tr>
					<td colspan="2" align="center">
						<br />
						<input type="button" class="tbut" value="Submit" onclick="searchAxis();" />

					</td>

				</tr>

			</table>
			<br />
		</logic:equal>
		<logic:equal name="PartToShow" value="HsbcReversalSearch">
            <table align="center" border="0" align="center" width="45%"
                cellpadding="1" cellspacing="1">

                <tr style="height:2px">
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td colspan="2" width="100%" class="tbn" height="10" align="left">
                        Search HSBC Reversals
                    </td>
                </tr>
                <tr>
                    <td colspan="2" width="100%" height="10" align="left"></td>
                </tr>
                <tr>
                    <td>
                        <bean:message key="reversal.fromdate" />
                    </td>
                    <td align="left">
                        &nbsp;
                        <input type="text" name="fromDate" id="fromDate"
                            readonly="true" />
                        <a href="javascript:fromDate.popup();"
                            onClick="document.getElementById('fromDate').focus()"><img
                                src="<%=request.getContextPath()%>/images/cal.gif" width="16"
                                height="16" border="0" alt="Click Here Pick up the date">
                        </a> &nbsp;
                        <script>
                            var fromDate = new calendar1(document.forms[0].elements["fromDate"]);
                            fromDate.year_scroll = true;
                            fromDate.time_comp = false;
                        </script>
                    </td>

                </tr>
                <tr>
                    <td>
                        <bean:message key="reversal.todate" />
                    </td>
                    <td align="left">
                        &nbsp;
                        <input type="text" name="toDate" id="toDate"
                            readonly="true" />
                        <a href="javascript:toDate.popup();"
                            onClick="document.getElementById('toDate').focus()"><img
                                src="<%=request.getContextPath()%>/images/cal.gif" width="16"
                                height="16" border="0" alt="Click Here Pick up the date">
                        </a> &nbsp;
                        <script>
                            var toDate = new calendar1(document.forms[0].elements["toDate"]);
                            toDate.year_scroll = true;
                            toDate.time_comp = false;
                        </script>
                    </td>

                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <br />
                        <input type="button" class="tbut" value="Submit" onclick="searchHsbc();" />
                    </td>
                </tr>
            </table>
            <br />
        </logic:equal>
	<logic:equal name="PartToShow" value="HdfcReversalSearch">

			<table align="center" border="0" align="center" width="45%"
				cellpadding="1" cellspacing="1">

				<tr style="height:2px">
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" class="tbn" height="10" align="left">
						Search HDFC Reversals
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" height="10" align="left"></td>
				</tr>

				
				<tr>
					<td>
						<bean:message key="reversal.fromdate" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="fromDate" id="fromDate"
							readonly="true" />
						<a href="javascript:fromDate.popup();"
							onClick="document.getElementById('fromDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here Pick up the date">
						</a> &nbsp;
						<script>
							var fromDate = new calendar1(document.forms[0].elements["fromDate"]);
							fromDate.year_scroll = true;
							fromDate.time_comp = false;
						</script>
					</td>

				</tr>
			    <tr>
					<td>
						<bean:message key="reversal.todate" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="toDate" id="toDate"
							readonly="true" />
						<a href="javascript:toDate.popup();"
							onClick="document.getElementById('toDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here Pick up the date">
						</a> &nbsp;
						<script>
							var toDate = new calendar1(document.forms[0].elements["toDate"]);
							toDate.year_scroll = true;
							toDate.time_comp = false;
						</script>
					</td>

				</tr>


				<tr>
					<td colspan="2" align="center">
						<br />
						<input type="button" class="tbut" value="Submit" onclick="searchHdfc();" />

					</td>

				</tr>

			</table>
			<br />
		</logic:equal>
<logic:equal name="PartToShow" value="ReceiptSearchResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">RECEIPT REVERSAL DETAILS</td></tr> 
  	
   

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="70%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=receiptSearch" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
		 	
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Receipt Date" property="receiptDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="No. Of Reversals"   media="html" >
	 	<bean:define id="receiptDate" property="receiptDate" name="record"/>
	 	<a onclick="return callReceiptReversals('<%=receiptDate %>')" style="cursor: pointer;">
			 	<bean:write property="noOfReversal" name="record"/> 
	 	</a>
	 	</display:column>
	 	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>

  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>



    </table>
<br />
</logic:equal>
		
		
		
<logic:equal name="PartToShow" value="CitiSearchResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">CITI REVERSAL DETAILS</td></tr> 
  	
   

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="70%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=citiSearch" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
		 	
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="No. Of Reversals"   media="html" >
	 	<bean:define id="depositDate" property="depositDate" name="record"/>
	 	<a onclick="return callCitiReversals('<%=depositDate %>')" style="cursor: pointer;">
			 	<bean:write property="noOfReversal" name="record"/> 
	 	</a>
	 	</display:column>
	 	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>

  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>



    </table>
<br />
</logic:equal>
	
<logic:equal name="PartToShow" value="ScbSearchResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">SCB REVERSAL DETAILS</td></tr> 
  	
   

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="70%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=scbSearch" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
		 	
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="No. Of Reversals"   media="html" >
	 	<bean:define id="depositDate" property="depositDate" name="record"/>
	 	<a onclick="return callScbReversals('<%=depositDate %>')" style="cursor: pointer;">
			 	<bean:write property="noOfReversal" name="record"/> 
	 	</a>
	 	</display:column>
	 	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>

  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>



    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="AxisSearchResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">AXIS REVERSAL DETAILS</td></tr> 
  	
   

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="70%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=axisSearch" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
		 	
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="No. Of Reversals"   media="html" >
	 	<bean:define id="depositDate" property="depositDate" name="record"/>
	 	<a onclick="return callAxisReversals('<%=depositDate %>')" style="cursor: pointer;">
			 	<bean:write property="noOfReversal" name="record"/> 
	 	</a>
	 	</display:column>
	 	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>

  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>



    </table>
<br />
</logic:equal>
<logic:equal name="PartToShow" value="HdfcSearchResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">HDFC BANK REVERSAL DETAILS</td></tr> 
</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="70%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=hdfcSearch" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="No. Of Reversals"   media="html" >
	 	<bean:define id="depositDate" property="depositDate" name="record"/>
	 	<a onclick="return callHdfcReversals('<%=depositDate %>')" style="cursor: pointer;">
			 	<bean:write property="noOfReversal" name="record"/> 
	 	</a>
	 	</display:column>
		</display:table>
	</td>   
    </tr>
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>
  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>
    </table>
<br />
</logic:equal>
<logic:equal name="PartToShow" value="HsbcSearchResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">HSBC BANK REVERSAL DETAILS</td></tr> 
</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="70%" align="center">
  <tr>
     <td align="center" colspan="2">
        <display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=hsbcSearch" class="table" uid="row" id="record" export="false">
        <display:setProperty name="paging.banner.one_item_found" value="" />
        <display:setProperty name="paging.banner.one_items_found" value="" />
        <display:setProperty name="paging.banner.all_items_found" value="" />
        <display:setProperty name="paging.banner.some_items_found" value="" />
        <display:setProperty name="paging.banner.placement" value="bottom" />
        <display:setProperty name="paging.banner.onepage" value="" />
        <display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   media="html" />
        <display:column style="text-align:center;width:130px" sortable="true" title="No. Of Reversals"   media="html" >
        <bean:define id="depositDate" property="depositDate" name="record"/>
        <a onclick="return callHsbcReversals('<%=depositDate %>')" style="cursor: pointer;">
                <bean:write property="noOfReversal" name="record"/> 
        </a>
        </display:column>
        </display:table>
    </td>   
    </tr>
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>
  <tr>
    <td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>
    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="ReceiptReversals">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">RECEIPT REVERSALS</td></tr> 
  	
   

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="90%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=receiptReversals" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 
	    <display:column style="text-align:center;width:130px" sortable="true" title="Receipt No" property="receiptNo"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Receipt Date" property="receiptDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Date" property="chequeDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque No" property="chequeNo"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Amount" property="amount"   media="html" />
	 	<display:column style="text-align:center;width:180px" sortable="true" title="Receipt AG Name"  property="receiptAGName" media="html"/>
	 	<display:column style="text-align:center;width:180px" sortable="true" title="Payment No"  property="receiptPayment" media="html"/>
	 	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>

  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>



    </table>	
<br />
</logic:equal>

<logic:equal name="PartToShow" value="CitiReversals">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">CITI BANK  REVERSALS</td></tr> 
  	
   

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="90%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=citiReversals" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 
	    <display:column style="text-align:center;width:130px" sortable="true" title="Deposit Slip No" property="depSlipNo"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque No" property="chequeNo"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmt"   media="html" />

	 	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>

  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>



    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="HdfcReversals">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">HDFC BANK REVERSALS</td></tr> 
</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="90%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=hdfcReversals" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	    <display:column style="text-align:center;width:130px" sortable="true" title="Deposit Slip No" property="depSlipNo"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque No" property="chequeNo"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmt"   media="html" />
        </display:table>
	</td>
    </tr>
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>
  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>
    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="HsbcReversals">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">HSBC BANK REVERSALS</td></tr> 
</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="90%" align="center">
  <tr>
     <td align="center" colspan="2">
        <display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=hsbcReversals" class="table" uid="row" id="record" export="false">
        <display:setProperty name="paging.banner.one_item_found" value="" />
        <display:setProperty name="paging.banner.one_items_found" value="" />
        <display:setProperty name="paging.banner.all_items_found" value="" />
        <display:setProperty name="paging.banner.some_items_found" value="" />
        <display:setProperty name="paging.banner.placement" value="bottom" />
        <display:setProperty name="paging.banner.onepage" value="" />
        <display:column style="text-align:center;width:130px" sortable="true" title="Deposit Slip No" property="depSlipNo"   media="html" />
        <display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   media="html" />
        <display:column style="text-align:center;width:130px" sortable="true" title="Cheque No" property="chequeNo"   media="html" />
        <display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmt"   media="html" />
        </display:table>
    </td>
    </tr>
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>
  <tr>
    <td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>
    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="ScbReversals">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">SCB BANK  REVERSALS</td></tr> 
</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="90%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=scbReversals" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 
	    <display:column style="text-align:center;width:130px" sortable="true" title="Deposit No" property="depSlipNo"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque No" property="chequeNo"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmt"   media="html" />

	 	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>

  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>



    </table>
<br />
</logic:equal>
<logic:equal name="PartToShow" value="AxisReversals">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">AXIS BANK  REVERSALS</td></tr> 
  	
   

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="90%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/reversal.do?method=axisReversals" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 
	    <display:column style="text-align:center;width:130px" sortable="true" title="Deposit No" property="depSlipNo"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque No" property="chequeNo"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmt"   media="html" />

	 	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td>&nbsp;</td>
    </tr>

  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" /></td>
   </tr>



    </table>
<br />
</logic:equal>
   <html:hidden property="method"/>
    <html:hidden property="depositdate"/>
   </html:form>
  </body>
</html:html>
