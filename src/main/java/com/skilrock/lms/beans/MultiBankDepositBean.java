/*
 * � copyright 2007, SkilRock Technologies, A division of Sugal & Damani Lottery Agency Pvt. Ltd.
 * All Rights Reserved
 * The contents of this file are the property of Sugal & Damani Lottery Agency Pvt. Ltd.
 * and are subject to a License agreement with Sugal & Damani Lottery Agency Pvt. Ltd.; you may
 * not use this file except in compliance with that License.  You may obtain a
 * copy of that license from:
 * Legal Department
 * Sugal & Damani Lottery Agency Pvt. Ltd.
 * 6/35,WEA, Karol Bagh,
 * New Delhi
 * India - 110005
 * This software is distributed under the License and is provided on an �AS IS�
 * basis, without warranty of any kind, either express or implied, unless
 * otherwise provided in the License.  See the License for governing rights and
 * limitations under the License.
 */

package com.skilrock.lms.beans;

import java.io.Serializable;
import java.util.List;

public class MultiBankDepositBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int agentOrgId;
	private String orgType;
	private double totalAmt;
	private String receiptNo;
	private String bankName;
	private String branchName;
	private String depositDate;
	private String autoGeneratedReceiptId;
	private String refNo;
	private String agtName;
	
	public int getAgentOrgId() {
		return agentOrgId;
	}
	public void setAgentOrgId(int agentOrgId) {
		this.agentOrgId = agentOrgId;
	}
	public double getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getDepositDate() {
		return depositDate;
	}
	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setAutoGeneratedReceiptId(String autoGeneratedReceiptId) {
		this.autoGeneratedReceiptId = autoGeneratedReceiptId;
	}
	public String getAutoGeneratedReceiptId() {
		return autoGeneratedReceiptId;
	}
	@Override
	public String toString() {
		return "MultiBankDepositBean [agentOrgId=" + agentOrgId
				+ ", autoGeneratedReceiptId=" + autoGeneratedReceiptId
				+ ", bankName=" + bankName + ", branchName=" + branchName
				+ ", depositDate=" + depositDate + ", orgType=" + orgType
				+ ", receiptNo=" + receiptNo + ", totalAmt=" + totalAmt + "]";
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setAgtName(String agtName) {
		this.agtName = agtName;
	}
	public String getAgtName() {
		return agtName;
	}	
	
}
