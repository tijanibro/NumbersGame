package com.skilrock.lms.web.accMgmt.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.skilrock.lms.beans.UserInfoBean;
import com.skilrock.lms.common.db.DBConnect;
import com.skilrock.lms.common.db.QueryManager;
import com.skilrock.lms.common.db.TableConstants;
import com.skilrock.lms.common.exception.LMSException;
import com.skilrock.lms.common.utility.GenerateRecieptNo;
import com.skilrock.lms.common.utility.OrgCreditUpdation;
import com.skilrock.lms.coreEngine.accMgmt.common.CreditNoteAgentHelper;
import com.skilrock.lms.coreEngine.drawGames.playMgmt.DrawGameOfflineHelper;
import com.skilrock.lms.coreEngine.reportsMgmt.common.GraphReportHelper;
import com.skilrock.lms.web.drawGames.common.Util;

public class CreditNoteAgentAction extends ActionSupport implements
		ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private String partyType;
	private double amount;
	private String remarks;
	//private String retName;
	private int  orgName;// Org Id
	private String orgNameValue;


	private List retList;

	public String creditNoteAgt() throws LMSException {
		return SUCCESS;

		// get the agents list
/*		HttpSession session = getRequest().getSession();
		UserInfoBean userBean = (UserInfoBean) session
				.getAttribute("USER_INFO");
		int agtOrgId = userBean.getUserOrgId();
		Connection con = DBConnect.getConnection();
		Statement stmt = null;
		try {
			retList = new ArrayList();
			stmt = con.createStatement();
			String retListQuery = "select name from st_lms_organization_master where organization_type='RETAILER' and parent_id="
					+ agtOrgId + " order by name";
			ResultSet rs = stmt.executeQuery(retListQuery);
			while (rs.next()) {

				String orgName = rs.getString(TableConstants.SOM_ORG_NAME);
				retList.add(orgName);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LMSException(e);
		}

		finally {
			try {
		//		con.close();
			} catch (Exception ee) {
				System.out.println("Error in closing the Connection");
				ee.printStackTrace();
				throw new LMSException(ee);

			}

		}
*/
	}
	
	// @ amit 
	public String doCreditNoteAgt(){
		
		Connection con =null;
		try {
			con= DBConnect.getConnection();
			con.setAutoCommit(false);
			HttpSession session = getRequest().getSession();
			UserInfoBean userBean = (UserInfoBean) session
					.getAttribute("USER_INFO");
			String parentOrgName = userBean.getOrgName();
			int agtOrgId = userBean.getUserOrgId();
			int agtId = userBean.getUserId();
			int retOrgId = 0;
			retOrgId = orgName;
			
			CreditNoteAgentHelper helper=new CreditNoteAgentHelper();
			String autoGeneratedReceiptNoAndId=helper.doCreditNoteAgt(retOrgId,agtOrgId,agtId,amount,remarks,userBean.getUserType(),con);
			con.commit();
			
			session.setAttribute("amount", amount);
			GraphReportHelper graphReportHelper = new GraphReportHelper();

			graphReportHelper.createTextReportAgent(Integer.parseInt(autoGeneratedReceiptNoAndId.split("#")[1]), (String) session
					.getAttribute("ROOT_PATH"), agtOrgId, parentOrgName);
			
			return SUCCESS;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			DBConnect.closeCon(con);
		}

		return null;

	}
	
	public String creditNoteAgtDesciption(){
		return SUCCESS;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
//
//	public void setRequest(HttpServletRequest request) {
//		this.request = request;
//	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getOrgName() {
		return orgName;
	}

	public void setOrgName(int orgName) {
		this.orgName = orgName;
	}
	public List getRetList() {
		return retList;
	}

	public void setRetList(List retList) {
		this.retList = retList;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getOrgNameValue() {
		return orgNameValue;
	}

	public void setOrgNameValue(String orgNameValue) {
		this.orgNameValue = orgNameValue;
	}
}