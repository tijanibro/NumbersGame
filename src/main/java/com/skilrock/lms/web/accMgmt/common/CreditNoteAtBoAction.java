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

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.skilrock.lms.beans.UserInfoBean;
import com.skilrock.lms.common.db.DBConnect;
import com.skilrock.lms.common.db.QueryManager;
import com.skilrock.lms.common.db.TableConstants;
import com.skilrock.lms.common.exception.LMSException;
import com.skilrock.lms.common.utility.CommonMethods;
import com.skilrock.lms.common.utility.GenerateRecieptNo;
import com.skilrock.lms.common.utility.OrgCreditUpdation;
import com.skilrock.lms.coreEngine.accMgmt.common.CreditNoteAgentHelper;
import com.skilrock.lms.coreEngine.reportsMgmt.common.GraphReportHelper;
import com.skilrock.lms.web.drawGames.common.Util;

public class CreditNoteAtBoAction extends ActionSupport implements
		ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List agentList;
	private String agentName;
	private double amount;
	private String partyType;
	private String remarks;
	private HttpServletRequest request;
	private String reason;
	private String gameName;
	private String agentNameValue;
	private String orgType;
    private String retOrgName;
    private String retNameValue;
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String creditNoteBo() throws LMSException {

		// get the agents list
		//Connection con = DBConnect.getConnection();
		//Statement stmt = null;
		try {
			/*agentList = new ArrayList();
			stmt = con.createStatement();
			String agentListQuery = "select name from st_lms_organization_master where organization_type='AGENT' order by name";
			ResultSet rs = stmt.executeQuery(agentListQuery);
			while (rs.next()) {
				String orgName = rs.getString(TableConstants.SOM_ORG_NAME);
				agentList.add(orgName);
			}*/
			
			List<String> reasonList = new ArrayList<String>();
			reasonList.add("OTHERS");
			
			boolean isScratch = "YES"
					.equalsIgnoreCase((String) ServletActionContext
							.getServletContext().getAttribute("IS_SCRATCH"));
			if (isScratch) {
				reasonList.add("AGAINST_LOOSE_BOOKS_RETURN");
			}
			boolean isCS = "YES"
				.equalsIgnoreCase((String) ServletActionContext
						.getServletContext().getAttribute("IS_CS"));
			if (isCS) {
				reasonList.add("AGAINST_FAULTY_RECHARGE_VOUCHERS");
			}
			
			request.getSession().setAttribute("REASON_LIST", reasonList);
			
			return SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			throw new LMSException(e);
		} finally {
			try {
				//con.close();
			} catch (Exception ee) {
				System.out.println("Error in closing the Connection");
				ee.printStackTrace();
				throw new LMSException(ee);
			}
		}
	}

	public String creditNoteBoDesciption() {

		return SUCCESS;
	}

	public String doCreditNoteBo() throws LMSException {
		
		Connection con = null;
		UserInfoBean userBean = null,agentInfoBean=null;		
		 //UserInfoBean userBean=null;
		String parentOrgName = null;
		int userOrgID = 0;
		// userBean= (UserInfoBean) session.getAttribute("USER_INFO");
		int agentOrgId = 0;
		//Statement stmt = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		String autoGeneratedReceiptNoAndId=null;
		try {
			HttpSession session = getRequest().getSession();
			userBean = (UserInfoBean) session.getAttribute("USER_INFO");
			parentOrgName = userBean.getOrgName();
			userOrgID = userBean.getUserOrgId();
			con=DBConnect.getConnection();
			con.setAutoCommit(false);

			//stmt = con.createStatement();

		/*	String updateBoRecieptGenMapping = QueryManager
					.updateST5BOReceiptGenMapping();*/

		/*	String getAgentOrgId = "select organization_id from st_lms_organization_master where name='"
					+ agentName + "'";
			ResultSet rs = stmt.executeQuery(getAgentOrgId);
			while (rs.next()) {
				agentOrgId = rs.getInt(TableConstants.SOM_ORG_ID);
			}*/
			agentOrgId = Integer.parseInt(agentName);
			// insert into LMS transaction master
			String queryLMSTrans = QueryManager.insertInLMSTransactionMaster();
			pstmt1 = con.prepareStatement(queryLMSTrans);
			pstmt1.setString(1, "BO");
			pstmt1.executeUpdate();
			ResultSet rs1 = pstmt1.getGeneratedKeys();
			//int transaction_id = -1;
			long transaction_id = -1;
			if (rs1.next()) {
				transaction_id = rs1.getLong(1);
			} else {
				throw new LMSException("Transaction id is not generated ");
			}

			String updateBoMaster = QueryManager.insertInBOTransactionMaster();
			pstmt1 = con.prepareStatement(updateBoMaster);
			pstmt1.setLong(1, transaction_id);
			pstmt1.setInt(2, userBean.getUserId());
			pstmt1.setInt(3, userOrgID);
			pstmt1.setString(4, "AGENT");
			pstmt1.setInt(5, agentOrgId);
			pstmt1.setTimestamp(6, new java.sql.Timestamp(new java.util.Date()
					.getTime()));
			pstmt1.setString(7, "CR_NOTE_CASH");
			pstmt1.executeUpdate();
			System.out.println(pstmt1);
			
			int gameNo = 0;
			if(gameName != null && !"-1".equalsIgnoreCase(gameName)){
				gameNo = Integer.parseInt(gameName.split("-")[0]);
			}

			String updateCreditNote = "insert into st_lms_bo_credit_note(transaction_id,agent_org_id,amount,transaction_type,remarks,reason,ref_id) values(?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(updateCreditNote);
			pstmt.setLong(1, transaction_id);
			pstmt.setInt(2, agentOrgId);
			pstmt.setDouble(3, amount);
			pstmt.setString(4, "CR_NOTE_CASH");
			pstmt.setString(5, remarks);
			pstmt.setString(6, reason);
			pstmt.setInt(7, gameNo);
			pstmt.executeUpdate();

			String fetchCreditNoteLastEntryQuery = "SELECT * from st_lms_bo_receipts where receipt_type=? or receipt_type=? ORDER BY generated_id DESC LIMIT 1";
			pstmt4 = con.prepareStatement(fetchCreditNoteLastEntryQuery);
			pstmt4.setString(1, "CR_NOTE_CASH");
			pstmt4.setString(2, "CR_NOTE");
			ResultSet recieptRs = pstmt4.executeQuery();
			String lastRecieptNoGenerated = null;
			if (recieptRs.next()) {
				lastRecieptNoGenerated = recieptRs.getString("generated_id");
			}
			// create receipt no.
			String autoGeneRecieptNo = GenerateRecieptNo.getRecieptNo(
					"CR_NOTE_CASH", lastRecieptNoGenerated, userBean
							.getUserType());

			// insert in receipt master table
			pstmt2 = con.prepareStatement(QueryManager.insertInReceiptMaster());
			pstmt2.setString(1, "BO");
			pstmt2.executeUpdate();
			ResultSet rs2 = pstmt2.getGeneratedKeys();
			int id = -1;
			if (rs2.next()) {
				id = rs2.getInt(1);
			} else {
				throw new LMSException("Error in reciept generation");
			}

			// insert into BO receipt table
			pstmt2 = con.prepareStatement(QueryManager.insertInBOReceipts());
			pstmt2.setInt(1, id);
			pstmt2.setString(2, "CR_NOTE_CASH");
			pstmt2.setInt(3, agentOrgId);
			pstmt2.setString(4, "AGENT");
			pstmt2.setString(5, autoGeneRecieptNo);
			pstmt2.setTimestamp(6, Util.getCurrentTimeStamp());
			pstmt2.executeUpdate();

			// insert into receipt transaction mapping
			pstmt3 = con.prepareStatement(QueryManager
					.insertBOReceiptTrnMapping());
			pstmt3.setInt(1, id);
			pstmt3.setLong(2, transaction_id);
			pstmt3.executeUpdate();

			// update organization account details
			//Only One Method use for Org Balanace Update
			boolean isValid=OrgCreditUpdation.updateOrganizationBalWithValidate(amount, "TRANSACTION", "CR_NOTE_CASH", agentOrgId,
					0, "AGENT", 0, con);
			if("RETAILER".equalsIgnoreCase(orgType)){
				int retOrgId=Integer.parseInt(retOrgName);
				agentInfoBean=CommonMethods.fetchUserData(agentOrgId);
				int agtId=agentInfoBean.getUserId();
				CreditNoteAgentHelper helper=new CreditNoteAgentHelper();
				autoGeneratedReceiptNoAndId=helper.doCreditNoteAgt(retOrgId, agentOrgId, agtId, amount, remarks, "AGENT", con);	
			}
			if(!isValid){
				throw new LMSException();
			}
				con.commit();
			
			/*
			OrgCreditUpdation.updateCreditLimitForAgent(agentOrgId,
					"CR_NOTE_CASH", amount, con);*/

			session.setAttribute("amount", amount);
			GraphReportHelper graphReportHelper = new GraphReportHelper();
			if("AGENT".equalsIgnoreCase(orgType)){			
			graphReportHelper.createTextReportBO(id, parentOrgName, userOrgID,
					(String) session.getAttribute("ROOT_PATH"));
			}else{
				graphReportHelper.createTextReportAgent(Integer.parseInt(autoGeneratedReceiptNoAndId.split("#")[1]), (String) session
						.getAttribute("ROOT_PATH"), agentOrgId, agentInfoBean.getOrgName());
			}
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

	public List getAgentList() {
		return agentList;
	}

	public String getAgentName() {
		return agentName;
	}

	public double getAmount() {
		return amount;
	}

	public String getPartyType() {
		return partyType;
	}

	public String getRemarks() {
		return remarks;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setAgentList(List agentList) {
		this.agentList = agentList;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getAgentNameValue() {
		return agentNameValue;
	}

	public void setAgentNameValue(String agentNameValue) {
		this.agentNameValue = agentNameValue;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setRetOrgName(String retOrgName) {
		this.retOrgName = retOrgName;
	}

	public String getRetOrgName() {
		return retOrgName;
	}

	public void setRetNameValue(String retNameValue) {
		this.retNameValue = retNameValue;
	}

	public String getRetNameValue() {
		return retNameValue;
	}

}
