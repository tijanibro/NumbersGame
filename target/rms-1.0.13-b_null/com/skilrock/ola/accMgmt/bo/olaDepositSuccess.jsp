<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%
	response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	
	<head>
	<script type="text/javascript" src="<%=request.getContextPath()%>/com/skilrock/lms/web/drawGames/playMgmt/js/common.js"></script>
		<s:head theme="ajax" debug="false" />
		<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
		<meta http-equiv="Content-Type"
			content="text/html; charset=utf-8" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/LMSImages/css/styles.css"
			type="text/css" />
		<title><%=application.getAttribute("JSP_PAGE_TITLE")%></title>


	</head>

	<body >
		<%@include file="/com/skilrock/lms/web/loginMgmt/menu.jsp"%>
		<h1>
			<s:text name="label.you.successfully.deposited.money"/>
		</h1>
			 <s:if test="#session.cashCardList!=null">
				<s:set name="cashCardBean" id="cashCardBean"
				value="#session.cashCardList" />
			<s:iterator id="beanCart" value="%{#cashCardBean}">
				<table align="center" border="0">
				<tr><td colspan="2" align="center"><h1><font size="6px"><s:text name="label.khel.play.rummy"/></font></h1></td></tr>
				<tr><td align="left" nowrap="nowrap"><h2><s:text name="label.purchase.date"/></>:</h2></td><td align="right"><h2><s:property value="purchaseDate" /></h2></td></tr>
					<tr>
						<td align="left" nowrap="nowrap">
							<h2>
								<s:text name="label.deposit.ref.code"/> :
								
							</h2>

						</td>
						<td align="right">
							<h2><s:property value="serialNumber" /></h2>
							</td>
						
						</tr>
						<tr>
						<td align="left" nowrap="nowrap">

							<h2>
								<s:text name="label.depo.amt"/> :
								
							</h2>
						</td>
						<td align="right">
							<h2><s:property value="amount" /></h2>
							</td>


					</tr>
					<tr>
						<td align="left" nowrap="nowrap">

							<h2>
								<s:text name="label.uname"/>:
								
							</h2>
						
						</td>
						<td align="right">
							<h2><s:property value="playerName" /></h2>
							</td>


					</tr>
				</table>
		
</s:iterator>
	
</s:if>
<script>updBalIframe();</script>
	
	</body>
	
</html>

<code id="headId" style="visibility: hidden">
	$RCSfile: olaDepositSuccess.jsp,v $ $Revision: 1.3 $
</code>