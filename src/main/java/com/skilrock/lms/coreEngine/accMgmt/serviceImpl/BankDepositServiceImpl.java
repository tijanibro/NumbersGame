package com.skilrock.lms.coreEngine.accMgmt.serviceImpl;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.skilrock.lms.beans.UserInfoBean;
import com.skilrock.lms.common.db.DBConnect;
import com.skilrock.lms.common.exception.LMSErrors;
import com.skilrock.lms.common.exception.LMSException;
import com.skilrock.lms.coreEngine.accMgmt.common.AgentBankDepositHelper;
import com.skilrock.lms.coreEngine.accMgmt.daoImpl.BankDepositDaoImpl;
import com.skilrock.lms.coreEngine.accMgmt.javaBeans.BankDepositBean;
import com.skilrock.lms.web.bankMgmt.beans.BankDetailsBean;

public class BankDepositServiceImpl {
	private static Log logger = LogFactory.getLog(BankDepositServiceImpl.class);
	private static BankDepositServiceImpl instance = null;

	private BankDepositServiceImpl() {
	}

	public static BankDepositServiceImpl getInstance() {
		if (instance == null) {
			instance = new BankDepositServiceImpl();
			logger.info("BankDepositServiceImpl Instance Created.");
		}
		return instance;
	}

	public List<BankDetailsBean> getBankDetails() throws LMSException {
		Connection connection = null;
		List<BankDetailsBean> bankDetailList = null;
		try {
			connection = DBConnect.getConnection();
			bankDetailList = BankDepositDaoImpl.getInstance().getBankDetails(connection);
		} catch (LMSException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			throw new LMSException(LMSErrors.GENERAL_EXCEPTION_ERROR_CODE, LMSErrors.GENERAL_EXCEPTION_ERROR_MESSAGE);
		} finally {
			DBConnect.closeCon(connection);
		}

		return bankDetailList;
	}

	public boolean notifyBankDeposit(BankDepositBean depositBean) throws LMSException {
		boolean status = false;
		Connection connection = null;
		try {
			connection = DBConnect.getConnection();
			connection.setAutoCommit(false);

			status = BankDepositDaoImpl.getInstance().notifyBankDeposit(depositBean, connection);

			connection.commit();
		} catch (LMSException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			throw new LMSException(LMSErrors.GENERAL_EXCEPTION_ERROR_CODE, LMSErrors.GENERAL_EXCEPTION_ERROR_MESSAGE);
		} finally {
			DBConnect.closeCon(connection);
		}

		return status;
	}

	public List<BankDepositBean> getLastRecords(int userId, int noOfRecords) throws LMSException {
		Connection connection = null;
		List<BankDepositBean> depositList = null;
		try {
			connection = DBConnect.getConnection();
			depositList = BankDepositDaoImpl.getInstance().getLastRecords(userId, noOfRecords, connection);
		} catch (LMSException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			throw new LMSException(LMSErrors.GENERAL_EXCEPTION_ERROR_CODE, LMSErrors.GENERAL_EXCEPTION_ERROR_MESSAGE);
		} finally {
			DBConnect.closeCon(connection);
		}

		return depositList;
	}

	public List<BankDepositBean> processBankDepositRequestSearch(String retName, String receiptNo, String startDate, String endDate, String status) throws LMSException {
		Connection connection = null;
		List<BankDepositBean> depositList = null;
		try {
			connection = DBConnect.getConnection();
			depositList = BankDepositDaoImpl.getInstance().processBankDepositRequestSearch(retName, receiptNo, startDate, endDate, status, connection);
		} catch (LMSException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			throw new LMSException(LMSErrors.GENERAL_EXCEPTION_ERROR_CODE, LMSErrors.GENERAL_EXCEPTION_ERROR_MESSAGE);
		} finally {
			DBConnect.closeCon(connection);
		}

		return depositList;
	}

	public boolean processBankDepositRequest(UserInfoBean userBean, String status, List<Integer> idList) throws LMSException {
		Connection connection = null;
		boolean updateStatus = false;
		try {
			connection = DBConnect.getConnection();
			connection.setAutoCommit(false);

			if("APPROVED".equals(status)) {
				AgentBankDepositHelper helper = new AgentBankDepositHelper();
				BankDepositBean depositBean = null;
				for(Integer id : idList) {
					depositBean = BankDepositDaoImpl.getInstance().getBankDepositRequestById(id, connection);

					String autoGeneratedNo = helper.submitBankDepositAmt(depositBean.getParentOrgId(), "AGENT", depositBean.getAmount(), depositBean.getReceiptNo(), depositBean.getBankName(), depositBean.getBranchName(), depositBean.getDate(), userBean, connection);
					logger.info("autoGeneratedNo Agent - "+autoGeneratedNo);
					autoGeneratedNo = helper.submitBankDepositAmtForRetailer(depositBean.getOrganizationId(), depositBean.getParentOrgId(), depositBean.getParentUserId(), "RETAILER", depositBean.getAmount(), depositBean.getReceiptNo(), depositBean.getBankName(), depositBean.getBranchName(), depositBean.getDate(), connection);
					logger.info("autoGeneratedNo Retailer - "+autoGeneratedNo);
				}
			}

			BankDepositDaoImpl.getInstance().updateBankDepositDetails(userBean.getUserId(), status, idList, connection);

			connection.commit();
			updateStatus = true;
		} catch (LMSException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			throw new LMSException(LMSErrors.GENERAL_EXCEPTION_ERROR_CODE, LMSErrors.GENERAL_EXCEPTION_ERROR_MESSAGE);
		} finally {
			DBConnect.closeCon(connection);
		}

		return updateStatus;
	}
}