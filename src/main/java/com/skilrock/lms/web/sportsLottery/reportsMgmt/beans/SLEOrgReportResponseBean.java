package com.skilrock.lms.web.sportsLottery.reportsMgmt.beans;

import java.io.Serializable;

public class SLEOrgReportResponseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int orgId;
	private double saleAmt;
	private double cancelAmt;
	private double pwtAmt;
	private double pwtDirAmt;

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public double getSaleAmt() {
		return saleAmt;
	}

	public void setSaleAmt(double saleAmt) {
		this.saleAmt = saleAmt;
	}

	public double getCancelAmt() {
		return cancelAmt;
	}

	public void setCancelAmt(double cancelAmt) {
		this.cancelAmt = cancelAmt;
	}

	public double getPwtAmt() {
		return pwtAmt;
	}

	public void setPwtAmt(double pwtAmt) {
		this.pwtAmt = pwtAmt;
	}

	public double getPwtDirAmt() {
		return pwtDirAmt;
	}

	public void setPwtDirAmt(double pwtDirAmt) {
		this.pwtDirAmt = pwtDirAmt;
	}

	@Override
	public String toString() {
		return "SLEOrgReportResponseBean [cancelAmt=" + cancelAmt + ", orgId="
				+ orgId + ", pwtAmt=" + pwtAmt + ", pwtDirAmt=" + pwtDirAmt
				+ ", saleAmt=" + saleAmt + "]";
	}

}