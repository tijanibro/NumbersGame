<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.skilrock.lms.dge.beans.ReportBeanDrawModule"%>
<%@page import="com.skilrock.lms.dge.beans.ReportDrawBean"%>
<%@page import="com.skilrock.lms.dge.beans.ReportGameBean"%>
<%@page import="com.skilrock.lms.common.utility.FormatNumber"%>
<%
	//response.setDateHeader("Expires", System.currentTimeMillis() + 10
	//	* 24 * 60 * 60 * 1000);
%>
<%
	response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<head>
		<s:head theme="ajax" debug="false" />
		<meta http-equiv="Content-Type"
			content="text/html; charset=utf-8" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/LMSImages/css/styles.css"
			type="text/css" />
		<title><%=application.getAttribute("JSP_PAGE_TITLE")%></title>
	</head>

	<body>

		<div id="wrap">
			<div id="top_form">
			<s:actionerror/>	
				
			
			
			<s:set name="dataBeanMap" value="regionDataMap"/>
					<s:if test="#dataBeanMap.size>0">
					<div id="export" align="left">
				<s:form id="excelFrm" action="exportReportAsExcel.action" method="post"
					enctype="multipart/form-data">
					<s:hidden id="tableValue" name="reportData" />
					<s:hidden name="reportName" value="Region  wise Draw Report" />
					<s:submit key="btn.exporttoexcel"
						cssStyle="margin-right:-400px;font-size:15px" onclick="getData();" />
				</s:form>
			</div>
				<div id="tableDataDiv">
			<table align="center">
			<tr>
			<td><h3><u>  <s:text name="State"/><s:text name="label.wise.draw.rep"/> <s:text name="label.from.date"/> : <s:property value="startDate"/> <s:text name="label.to.date"/>: <s:property value="endDate"/> </u></h3></td>
			</tr>
			</table>
		
				<table width="100%" border="1" cellpadding="3" cellspacing="0"	bordercolor="#CCCCCC" align="center">
					<tr>
						<s:if test="%{repType=='DT'}">
						<th colspan="2" align="center">
						 <s:text name="State"/> <s:text name="label.wise.draw.rep"/>
						
						</th>
						</s:if>
							<s:else>
							<th colspan="3" align="center">
							 <s:text name="State"/> <s:text name="label.wise.draw.rep"/>
						
							</th>
							</s:else>
				<th align="center"><s:text name="label.amt.in"/> <s:property value="#application.CURRENCY_SYMBOL" /></th>
						
					</tr>
					<tr>
					<s:if test="%{repType=='DT'}">
					<th colspan="3" align="center">
							<s:text name="label.draw.data"/> <s:text name="State"></s:text> <s:text name="label.wise"/>
						</th>
					</s:if>
					<s:else>
					<th colspan="4" align="center">
							<s:text name="label.draw.data"/> <s:text name="State"></s:text> <s:text name="label.wise"/>
						</th>
					</s:else>
						
					</tr>
					<tr>
						<th ><s:text name="State"></s:text></th>
					<s:if test="%{repType=='DT'}"><th><s:text name="label.intake"/></th>
						
						<th><s:text name="label.sale.amt"/></th>
						</s:if>
						<s:else>
						<th><s:text name="label.sale.amt"/></th>
						<th>
							<s:text name="label.prize.amt"/>
						 </th>						
						<th>
							% <s:text name="label.prize.on.sale"/>
						</th>
						
						</s:else>
						 
						
					</tr>
					
					
					<!-- Initialize Variales For Table Data Total -->
					<s:if test="%{repType=='DT'}">
					<s:set name="totalIntake" value="0" />
					<s:set name="totalSale" value="0" />
					</s:if>
					<s:else>
					<s:set name="totalSale" value="0" />
					<s:set name="totalPwt" value="0" />
					</s:else>
					
					
				
				
					<s:set name="dataBean" id="dataBean" value="#dataBeanMap" />
							<s:iterator id="beanCart" value="%{#dataBean}">
						
								<tr class="startSortable">
							
							
									<td align="left">
								<div id="href_m" name="href_m" >
									<s:a
							href="bo_rep_areaWiseReport.action?gameId=%{gameId}&repType=%{repType}&stateCode=%{key}&drawName=%{drawName}&startDate=%{startDate}&endDate=%{endDate}"
							theme="ajax" targets="result" name="href">
							<s:property value="value.stateName" />
							
						</s:a>
							</div>
							<div id="href_n"  style="display: none;">
								<s:property value="value.stateName" />
							</div>			
									</td>
						<s:if test="%{repType=='DT'}">

									<td align="right">

										<s:set name="totalCashAmt" value="value.totalCashAmt" />
										<%=FormatNumber.formatNumberForJSP(pageContext
								.getAttribute("totalCashAmt"))%>
										<s:set name="totalIntake" value="%{#totalIntake+value.totalCashAmt}" />
									</td>

									
								<td align="right">

										<s:set name="saleAmt" value="value.saleAmt" />
										<%=FormatNumber.formatNumberForJSP(pageContext
								.getAttribute("saleAmt"))%>
										<s:set name="totalSale" value="%{#totalSale +value.saleAmt}" />
									</td>

						</s:if>
						<s:else>
														<td align="right">

										<s:set name="saleAmt" value="value.saleAmt" />
										<%=FormatNumber.formatNumberForJSP(pageContext
								.getAttribute("saleAmt"))%>
										<s:set name="totalSale" value="%{#totalSale +value.saleAmt}" />
									</td>
									
									<td align="right">
												<s:set name="pwtAmt" value="value.pwtAmt" />
										<%=FormatNumber.formatNumberForJSP(pageContext
							.getAttribute("pwtAmt"))%>
											 <s:set name="totalPwt" value="%{#totalPwt +value.pwtAmt}" />
									</td>
									<td align="right">
										<s:set name="ppratio" value="%{(value.pwtAmt*100)/#saleAmt}" />
										<%=FormatNumber.formatNumberForJSP(pageContext
							.getAttribute("ppratio"))%>
										
									</td>
									
									
									
						</s:else>
									
									
									
									
								</tr>
						
						</s:iterator>
						<tr>
						
								<td colspan="1"  align="center">
										<s:text name="label.total"/>
									</td>	
									
				<s:if test="%{repType=='DT'}">
					
					<td align="right">
										<%=FormatNumber.formatNumberForJSP(pageContext
						.getAttribute("totalIntake"))%>
									</td>
					<td align="right">
									<%=FormatNumber.formatNumberForJSP(pageContext
						.getAttribute("totalSale"))%>
									</td>
									
							
									
									
									
					
					</s:if>
					<s:else>
					<td align="right">
									<%=FormatNumber.formatNumberForJSP(pageContext
						.getAttribute("totalSale"))%>
									</td>
									<td align="right">
										<%=FormatNumber.formatNumberForJSP(pageContext
						.getAttribute("totalPwt"))%>
									</td>
							
									
									<td align="right">
									<s:set name="tppratio" value="%{(#totalPwt*100)/#totalSale}" />
										<%=FormatNumber.formatNumberForJSP(pageContext
						.getAttribute("tppratio"))%>
									</td>
									
					
					</s:else>				
									
									
									
									
						</tr>
					
				
				</table>
			</s:if>
				<s:else>
						<tr>
							<td colspan="6" align="center">
								<s:text name="msg.no.record.process"/>
							</td>
						</tr>
					</s:else>
			</div>



			</div>
	



	</body>
	
</html>
<code id="headId" style="visibility: hidden">
$RCSfile: bo_rep_regionWiseReportResult.jsp,v $
$Revision: 1.3 $
</code>
