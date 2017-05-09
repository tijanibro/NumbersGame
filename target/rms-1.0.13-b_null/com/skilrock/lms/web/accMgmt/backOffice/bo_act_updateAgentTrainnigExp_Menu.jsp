<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/LMSImages/css/styles.css" type="text/css" />
		<title><%=application.getAttribute("JSP_PAGE_TITLE")%></title>
		<s:head theme="ajax" debug="false" />
		<script>var path = "<%=request.getContextPath()%>";</script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/com/skilrock/lms/web/accMgmt/backOffice/js/updateTrainingData.js" ></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/com/skilrock/lms/web/common/globalJs/jquery-1.10.1.min.js"></script>
		<script>
			$(document).ready(function() {
						var serviceBeanData = $('#serviceBeanData').val();
						var obj = jQuery.parseJSON(serviceBeanData);
						$.each(obj, function(key, value) {
							$('#serviceId').append($('<option></option>').val(value.serviceId).html(value.serviceDisplayName));
						});

						$('#gameId').find('option').remove().end().append($('<option></option>').val(-1).html('--Please Select--'));

						$('#serviceId').change(function() {
							$('#gameId').find('option').remove().end().append($('<option></option>').val(-1).html('--Please Select--'));
							var serviceId = $('#serviceId').val();
							$.each(obj, function(index, value) {
								// alert(value.countryName);
								if (serviceId == value.serviceId) {
									// alert("equals");
									if (value.gameBeans != undefined) {
										$.each(value.gameBeans, function(index1, value1) {
											//alert(value1.stateName);
											$('#gameId').append($('<option></option>').val(value1.gameId).html(value1.gameName));
										});
									}
								}
							});
						});
					});
			
		</script>
	</head>
	<body>
		<%@include file="/com/skilrock/lms/web/loginMgmt/menu.jsp"%>
		<div id="wrap">
			<div id="top_form">
				<h3> Training Expense </h3>
				<s:form name="trainingExp" id="trainingExp" method="POST">
					<s:hidden id="serviceBeanData" name="serviceBeanData"></s:hidden>
					<table border="0" width="100%" cellpadding="2" cellspacing="0">
						<tr>
							<td align="right"><i>Select Service :</i></td>
							<td nowrap="nowrap" colspan="2">
								<s:select name="serviceId" id="serviceId" list="{}" headerKey="-1" headerValue="--Please Select--" theme="simple" cssClass="option"></s:select>
								<div id="orgLoading" style="position: absolute;"></div> 
							</td>
						</tr>
						<tr>
							<td align="right"> <i>Game Name :</i> </td> 
							<td nowrap="nowrap" colspan="2">
								<%-- <s:select id="gameId" name="gameId" list="%{}" cssClass="option" onchange="changeTrainType();" theme="simple"></s:select> --%>
								<s:select id="gameId" name="gameId" list="{}" cssClass="option" theme="simple"></s:select>
								<div id="orgLoading" style="position: absolute;"></div> 
							</td>
						</tr>
						<tr>
							<td align="right"> <i>Select Type :</i> </td> 
							<td nowrap="nowrap" colspan="2"> <s:select theme="simple" id="trainingType" cssClass="option" list="{'DAILY','WEEKLY'}" name="trainingType" emptyOption="false" headerKey="-1" headerValue="--Please Select--" onchange="fetchActData()" /> <div id="orgLoading" style="position: absolute;"></div></td>
							<%-- <td nowrap="nowrap" colspan="2"> <s:select theme="simple" id="trainingType" cssClass="option" list="{'DAILY','WEEKLY'}" name="trainingType" emptyOption="false" headerKey="-1" headerValue="--Please Select--" /> <div id="orgLoading" style="position: absolute;"></div></td> --%>
						</tr>
					</table>
					<div id="completeDiv" style="position: absolute; background-color: #CCCCCC; opacity: 1; filter: alpha(opacity = 60); display: none; width: 830px">
						<center> <b><font size="3">Please Wait Data is Being Processed......</font> </b> </center>
					</div>
					<div id="resultDiv" style="display: none"> </div>
					<div id="reportDiv"> </div>
				</s:form>
			</div>
		</div>
	</body>
</html>

<code id="headId" style="visibility: hidden"> $RCSfile:
	bo_act_updateAgentTrainnigExp_Menu.jsp,v $ $Revision: 1.3 $
</code>