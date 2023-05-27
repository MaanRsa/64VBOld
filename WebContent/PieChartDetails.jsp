
<jsp:directive.page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"/>
<jsp:directive.page import="com.mysql.jdbc.Util"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.maan.transaction.TransactionVB"/><%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%Import List; 
Import TransactionVB;
%>
<%
		List li=(List)request.getAttribute("transResult"); 
		TransactionVB tVB=(TransactionVB)li.get(0);
		String pending = tVB.getPending();
		String valid = tVB.getTotalRecords();
		String invalid = tVB.getInvalid();
		String duplicate = tVB.getDuplicates();
		String matched = tVB.getMatched();
		
		%>
		
<html > 
  <head>
    <title>PieChartDetails.jsp</title>

		<!-- 1. Add these JavaScript inclusions in the head of your page -->
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/highcharts.js"></script>
		
		<!-- 1a) Optional: add a theme file -->
		<!--
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/themes/gray.js"></script>
		-->
		
		<!-- 1b) Optional: the exporting module -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/modules/exporting.js"></script>
		
		
		<!-- 2. Add the JavaScript to initialize the chart on document ready -->
		
		<script type="text/javascript">
		function chart(valid,invalid,duplicate,matched,pending){
		
			var chart;
			$(document).ready(function() {
				chart = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false
					},
					title: {
						text: 'Transaction Details'
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: false
							},
							showInLegend: true
						}
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
							['Valid', valid],
							['Invalid',  invalid],
							['Duplicates', duplicate],
							['Matched',    matched],
							['Pending',    pending]
							]
					}]
				});
			});
		}
		</script>
  </head>
  
  <body onload=chart(<%=valid %>,<%=invalid %>,<%=duplicate %>,<%=matched %>,<%=pending %>);>


 <table  align="center" width="98%">
     
     <tr>
     <td >
     <div id="container" style="width: 700px; height: 380px; margin: 0 auto"></div>
     </td>
    </tr>
  	<tr>
   <td colspan = "5" align="center"><br />
   	      <input type="button" name="Cancel" value="Close" class="tbut" onclick = "javascript:window.close()">
	</td>  	   			
	</tr>
</table>

</body>
</html>