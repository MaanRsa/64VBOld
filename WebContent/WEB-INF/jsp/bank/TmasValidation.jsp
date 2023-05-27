<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<script type="text/javascript">

function submitForm()
   {
     document.forms[0].action="<%=request.getContextPath()%>/validationTmas.do?method=SubmitTmasValidation";
     document.forms[0].submit();
     return false; 
	}

	 function addNew()
   {

     document.mainForm.action="<%=request.getContextPath()%>/validationTmas.do?method=addNewTmas";
     document.mainForm.submit();
     return false;
   }
   
   
   function update(validId)
 {
 
   document.getElementById('validId').value=validId;
   document.mainForm.action="<%=request.getContextPath()%>/validationTmas.do?method=UpdateTmasValid";
   document.mainForm.submit();
   return false;
 }
  
   
   function Edit(branch_Id)
  {

    var pagenav=<%=request.getParameter("d-446779-p")%>;
    document.getElementById('pagenavination').value=pagenav;
 	document.getElementById('validId').value=branch_Id;
    document.mainForm.action="<%=request.getContextPath()%>/validationTmas.do?method=EditTmasMas";  
    document.mainForm.submit();
    return false;
  }
  
  function back2()
{
 var pageval=document.getElementById('pagenavination').value;
 window.history.forward();
 document.mainForm.action="<%=request.getContextPath()%>/validationTmas.do?method=initTmas&d-446779-p="+pageval;
 document.mainForm.submit();
 return false;
}
 function back1()
  {
   window.history.forward();
   document.mainForm.action="<%=request.getContextPath()%>/validationTmas.do?method=initTmas";
   document.mainForm.submit();
    return false;
  }

	
	

</script>
<html:form action="/validationTmas" styleId="mainForm">

<logic:equal value="submitTmasValidation" name="path"  >
	<table width="100%" border="0" align="center" cellspacing="0" cellpadding="5">
		
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="tmasValidation.heading" />
			</td>	
		</tr>
		<tr class="tabin">
			<td colspan="4" align="center">			    
			<html:messages id="message" message="true">
			<li style="color: red;">
			  <bean:write name="message"/>
			</li>
			</html:messages>
		    </td>
		</tr>	
		
			<tr class="tabin">
		     <td class="t">
		       <bean:message key="admin.tmas_Validation.typeid"/>
		     </td>
		     <td align="left" class="t">
				   <html:select property="typeid" styleId="" >
				   <html:option value="Select">-Select-</html:option>
				   <html:optionsCollection name="type" label="value" value="key"/>
				   </html:select>
				</td>
			</tr>
			
			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.dbColumnName"/>
			     </td>
			     <td class="t"> 
			        <html:text property="dbColumnName" styleId="dbColumnName" maxlength = "200"/>
			       </td>			     
			</tr>
			
			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.excelHeaderName"/>
			     </td>
			     <td class="t"> 
			        <html:text property="excelHeaderName" styleId="excelHeaderName" maxlength = "200"/>
			       </td>			     
			</tr>
			
			<tr class="tabin">
				<td class="t">
					<bean:message key="admin.tmas_Validation.validationStatus"/>
				</td>

				<td class="t">
					<html:radio property="validationStatus" value="Y" />Yes
					<html:radio property="validationStatus" value="N" />No
				</td>
			</tr>
			<tr class="tabin">
				<td class="t">
					<bean:message key="admin.tmas_Validation.mandatoryStatus"/>
				</td>

				<td class="t">
					<html:radio property="mandatoryStatus" value="P" />Primary Key
					<html:radio property="mandatoryStatus" value="Y" />Yes
					<html:radio property="mandatoryStatus" value="N" />No
				</td>
			</tr>

			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.fieldLength"/>
			     </td>
			     <td class="t"> 
			        <html:text property="fieldLength" styleId="fieldLength" maxlength = "200"/>
			       </td>			     
		    	</tr>

			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.dataType"/>
			     </td>
			     <td class="t"> 
			         <html:select property="dataType" styleId="" >
				      <html:option value="Select">-Select-</html:option>
				   <html:option value="">EMPTY</html:option>
				   <html:option value="VARCHAR">VARCHAR</html:option>
				   <html:option value="CHAR">CHAR</html:option>
				   <html:option value="DATE">DATE</html:option>
				   <html:option value="TIMESTAMP">TIMESTAMP</html:option>
				   <html:option value="TIME">TIME</html:option>
				   <html:option value="NUMBER">NUMBER</html:option>
				   </html:select>
			        
			       </td>			     
			</tr>

    		<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.dataFormat"/>
			     </td>
			     <td class="t"> 
			        <html:text property="dataFormat" styleId="dataFormat" maxlength = "200"/>
			       </td>			     
			</tr>
			
			<tr class="tabin">
				<td class="t">
					<bean:message key="admin.tmas_Validation.referenceStatus"/>
				</td>

				<td class="t">
					<html:radio property="referenceStatus" value="Y" />Yes
					<html:radio property="referenceStatus" value="N" />No
				</td>
			</tr>
			
			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.referenceTable"/>
			     </td>
			     <td class="t"> 
			        <html:text property="referenceTable" styleId="referenceTable" maxlength = "200"/>
			       </td>			     
			</tr>
					<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.referenceColumn"/>
			     </td>
			     <td class="t"> 
			        <html:text property="referenceColumn" styleId="referenceColumn" maxlength = "200"/>
			       </td>			     
			</tr>
	    	<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.referenceCondition"/>
			     </td>
			     <td class="t"> 
			        <html:text property="referenceCondition" styleId="referenceCondition" maxlength = "200"/>
			       </td>			     
			</tr>
			
		<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.checkValue"/>
			     </td>
			     <td class="t"> 
			        <html:text property="checkValue" styleId="checkValue" maxlength = "200"/>
			       </td>	
			       		     
			</tr>
				<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.checkValueCond"/>
			     </td>
			     <td class="t"> 
			        <html:text property="checkValueCond" styleId="checkValueCond" maxlength = "200"/>
			       </td>			     
			</tr>
			
			<tr class="tabin">
				<td class="t">
					<bean:message key="admin.tmas_Validation.active"/>
				</td>

				<td class="t">
					<html:radio property="active" value="1" />Yes
					<html:radio property="active" value="X" />No
				</td>
			</tr>
			
			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.xmlTagName"/>
			     </td>
			     <td class="t"> 
			        <html:text property="xmlTagName" styleId="xmlTagName" maxlength = "200"/>
			       </td>			     
			</tr>
			
			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.xgenColumn"/>
			     </td>
			     <td class="t"> 
			           <html:text property="xgenColumn" styleId="xgenColumn" maxlength = "200"/>
			       </td>			     
			</tr>
      
      
      	  <tr class="tabin">
				<td align="center" colspan="2" class="t">
                  <input type="button" value="Submit" onclick="submitForm()" class="button"/>
					<input type="button" value="Back" onclick="back1()" class="button"/>
           	</td>
			</tr>
		
		</table>
</logic:equal>


<logic:equal value="EditTmasValidation" name="path"  >
	<table width="100%" border="0" align="center" cellspacing="0" cellpadding="5">
		
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="tmasValidation.heading" />
			</td>	
		</tr>
		<tr class="tabin">
			<td colspan="4" align="center">			    
			<html:messages id="message" message="true">
			<li style="color: red;">
			  <bean:write name="message"/>
			</li>
			</html:messages>
		    </td>
		</tr>	
		
			<tr class="tabin">
		     <td class="t">
		       <bean:message key="admin.tmas_Validation.typeid"/>
		     </td>
		     <td align="left" class="t">
				   <html:select property="typeid" styleId="" >
				   <html:option value="Select">-Select-</html:option>
				   <html:optionsCollection name="type" label="value" value="key"/>
				   </html:select>
				</td>
			</tr>
			
			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.dbColumnName"/>
			     </td>
			     <td class="t"> 
			        <html:text property="dbColumnName" styleId="dbColumnName" maxlength = "200"/>
			       </td>			     
			</tr>
			
			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.excelHeaderName"/>
			     </td>
			     <td class="t"> 
			        <html:text property="excelHeaderName" styleId="excelHeaderName" maxlength = "200"/>
			       </td>			     
			</tr>
			
			<tr class="tabin">
				<td class="t">
					<bean:message key="admin.tmas_Validation.validationStatus"/>
				</td>

				<td class="t">
					<html:radio property="validationStatus" value="Y" />Yes
					<html:radio property="validationStatus" value="N" />No
				</td>
			</tr>
			<tr class="tabin">
				<td class="t">
					<bean:message key="admin.tmas_Validation.mandatoryStatus"/>
				</td>

				<td class="t">
					<html:radio property="mandatoryStatus" value="P" />Primary Key
					<html:radio property="mandatoryStatus" value="Y" />Yes
					<html:radio property="mandatoryStatus" value="N" />No
				</td>
			</tr>

			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.fieldLength"/>
			     </td>
			     <td class="t"> 
			        <html:text property="fieldLength" styleId="fieldLength" maxlength = "200"/>
			       </td>			     
		    	</tr>

			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.dataType"/>
			     </td>
			     <td class="t"> 
			        <html:text property="dataType" styleId="dataType" maxlength = "200"/>
			       </td>			     
			</tr>

    		<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.dataFormat"/>
			     </td>
			     <td class="t"> 
			        <html:text property="dataFormat" styleId="dataFormat" maxlength = "200"/>
			       </td>			     
			</tr>
			
			<tr class="tabin">
				<td class="t">
					<bean:message key="admin.tmas_Validation.referenceStatus"/>
				</td>

				<td class="t">
					<html:radio property="referenceStatus" value="Y" />Yes
					<html:radio property="referenceStatus" value="N" />No
				</td>
			</tr>
			
			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.referenceTable"/>
			     </td>
			     <td class="t"> 
			        <html:text property="referenceTable" styleId="referenceTable" maxlength = "200"/>
			       </td>			     
			</tr>
					<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.referenceColumn"/>
			     </td>
			     <td class="t"> 
			        <html:text property="referenceColumn" styleId="referenceColumn" maxlength = "200"/>
			       </td>			     
			</tr>
	    	<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.referenceCondition"/>
			     </td>
			     <td class="t"> 
			        <html:text property="referenceCondition" styleId="referenceCondition" maxlength = "200"/>
			       </td>			     
			</tr>
			
		<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.checkValue"/>
			     </td>
			     <td class="t"> 
			        <html:text property="checkValue" styleId="checkValue" maxlength = "200"/>
			       </td>	
			       		     
			</tr>
				<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.checkValueCond"/>
			     </td>
			     <td class="t"> 
			        <html:text property="checkValueCond" styleId="checkValueCond" maxlength = "200"/>
			       </td>			     
			</tr>
			
			<tr class="tabin">
				<td class="t">
					<bean:message key="admin.tmas_Validation.active"/>
				</td>

				<td class="t">
					<html:radio property="active" value="1" />Yes
					<html:radio property="active" value="X" />No
				</td>
			</tr>
			
			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.xmlTagName"/>
			     </td>
			     <td class="t"> 
			        <html:text property="xmlTagName" styleId="xmlTagName" maxlength = "200"/>
			       </td>			     
			</tr>
			
			<tr class="tabin">			         
		         <td class="t">
			       <bean:message key="admin.tmas_Validation.xgenColumn"/>
			     </td>
			     <td class="t"> 
			           <html:text property="xgenColumn" styleId="xgenColumn" maxlength = "200"/>
			       </td>			     
			</tr>
      
      
      	  <tr class="tabin">
				<td align="center" colspan="2" class="t">
                  <input type="button" value="Update" onclick="update('<bean:write name='tmasvalidationform' property="validId"/>')" class="button"/>
					<input type="button" value="back" onclick="back2()" class="button"/>
            	</td>
			</tr>
		
		</table>
</logic:equal>

		
<logic:equal value="DisplayTmasValidations" name="path">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	       <tr>
				<td class="header" width="40" height="44">
					<img src="images/submitcut.gif" width="25" height="26">
				</td>
				<td class="test" colspan="4">
					<bean:message key="tmasValidation.heading" />
				</td>
				<td class="s" width="150" height="44" align="center">
					<a href="Home.htm"></a>
				</td>
				<td>
					&nbsp;
				</td>		
			</tr>
			<tr>
				<td colspan="4">
					<html:messages id="message" message="true">
						<li style="color: red;">
							<bean:write name="message" />
						</li>
					</html:messages>
				</td>
			</tr>
<tr>
 <td colspan="8">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2">
					<display:table name="list" pagesize="10" requestURI="/validationTmas.do?method=initTmas" excludedParams="*" class="table" uid="row" id="record">
						<display:setProperty name="paging.banner.one_item_found"   value="" />
					    <display:setProperty name="paging.banner.one_items_found"  value="" />
						<display:setProperty name="paging.banner.all_items_found"  value="" /> 
						<display:setProperty name="paging.banner.some_items_found" value="" />
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="paging.banner.onepage" value="" />
						<display:column sortable="true" title="Type"               property="typeid" style="text-align:center;width:130px;" />
						<display:column sortable="true" title="Valid Id"           property="validId" style="text-align:center;width:130px;"/>
    					<display:column sortable="true" title="Db Column Name"     property="dbColumnName" style="text-align:center;width:130px;"/>
    					<display:column sortable="true" title="Reference Table"    property="referenceTable" style="text-align:center;width:130px;"/> 
    					<display:column sortable="true" title="Reference Column"   property="referenceColumn" style="text-align:center;width:130px;"/> 
    					<display:column sortable="true" title="Status"             property="validationStatus" style="text-align:center;width:80px;"/> 
    					
    					<display:column sortable="true" title="Action" style="text-align:center;">
    					<a style="color:000000" href="#" onclick="javascript:Edit('<bean:write name="record" property="validId"/>')"/>
						  <input type="button" value="Edit" class="button">
						</a>
    					</display:column>
    					
    				</display:table>
    				
    					</td>
							
							</tr>	
							
							<tr>
							   <td align="center">
							   		<input type="button" value="Add New" class="button" onclick="addNew()" /> 
						      </td>
							</tr>
							
					</table>
				</td>
			</tr>
		
		  <tr>
			<td colspan="3">
				&nbsp;
			</td>
		</tr>
		
	</table>
    				

           
</logic:equal>
<html:hidden property="method" styleId="method" />
<html:hidden property="mode" styleId="mode" />	
<html:hidden property="pagenavination" styleId="pagenavination"/>	
<html:hidden property="validId" styleId="validId" />
</html:form>
		
