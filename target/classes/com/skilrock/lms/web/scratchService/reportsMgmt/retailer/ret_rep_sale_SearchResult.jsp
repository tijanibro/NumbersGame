
<%@page import="com.skilrock.lms.beans.DateBeans"%>
<%@page import="com.skilrock.lms.common.utility.GetDate"%><%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<s:head theme="ajax"/>

<body onload="disable()">

<br/>
  
<div align="center"><h3><u><s:text name="label.scratch.report.purchase.report"/> &nbsp;
	<%if("".equals(((DateBeans)session.getAttribute("datebean") ).getReportType())) {%>
  		 From : 
  		<%=GetDate.getConvertedDate(( (DateBeans)session.getAttribute("datebean") ).getStartDate())%>
  		&nbsp;&nbsp;To&nbsp;:&nbsp;
  		<%=GetDate.getConvertedDate(((DateBeans)session.getAttribute("datebean") ).getEndDate())%>
  		
	<%} else {out.println("Of : "+((DateBeans)session.getAttribute("datebean") ).getReportType());}%>
	</u></h3></div>	



  	
	 <table width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#CCCCCC" align="center">
		<tr>
				
			<th width="12%" nowrap="nowrap"><s:text name="label.scratch.game.name"/></th>
			<th width="6%"  nowrap="nowrap"><s:text name="label.scratch.cost.ticket"/></th>
			<th width="8%" ><s:text name="label.scratch.ticket.book"/></th> 
			<!--  <th width="8%" >Existing Books </th>-->			
			<th width="10%"><s:text name="label.scratch.books.purchased"/></th>			
			<th width="10%"><s:text name="label.scratch.books.returned"/></th>
			<th width="10%"><s:text name="label.scratch.books.remaining"/></th>	
		</tr>		
		
		<s:if test="#session.searchResultRet.size>0">
			<s:iterator id="beanCart" value="#session.searchResultRet">
			<tr class="startSortable">				
				<td width="12%"  align="left" nowrap="nowrap"><s:property value="gamename" /></td>
				<td width="6%" align="right"><s:property value="ticketCost" /></td>
				<td width="8%"  align="right"><s:property value="ticketsPerBook" /></td>
				<!--  <td width="8%" align="right"><s:property value="existingBooks" /></td>-->				
				<td width="10%" align="right"><s:property value="saleToRetailer" /></td>
				<td width="10%" align="right"><s:property value="saleReturnByRetailer" /></td>
				<th width="10%" align="right"><s:property value="agentsRemBooks" /></th>				
			</tr>	
			</s:iterator> 
		</s:if>
		<s:else>
			<tr>
				<td colspan="9" align="center"><s:text name="msg.error.scratch.no.record.to.process"/></td>
			</tr>
		</s:else>
		
	 </table>
	 <s:div id="naviga" cssStyle="text-align: center;width: 100%">
		<s:if test=" #session.APP_ORDER_LIST5.size >5 ">
    	
	<s:if test="#session.startValueOrderSearch!=0"><s:a theme="ajax" targets="down" href="agt_rep_sale_Navigate.action?end=first">First</s:a><s:a theme="ajax" targets="down" href="agt_rep_sale_Navigate.action?end=Previous" > Previous</s:a></s:if><s:else>First Previous</s:else>    
    <s:if test="#session.startValueOrderSearch==((#session.APP_ORDER_LIST5.size/10)*10)">Next Last</s:if><s:else><s:a theme="ajax" targets="down" href="agt_rep_sale_Navigate.action?end=Next" >Next</s:a><s:a theme="ajax" targets="down" href="agt_rep_sale_Navigate.action?end=last">Last</s:a></s:else>      
    
		</s:if>
		
	 </s:div>
	 

	
</body>
</html>
<code id="headId" style="visibility: hidden">
$RCSfile: ret_rep_sale_SearchResult.jsp,v $
$Revision: 1.2 $
</code>