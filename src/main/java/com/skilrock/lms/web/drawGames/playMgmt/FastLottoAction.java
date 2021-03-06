package com.skilrock.lms.web.drawGames.playMgmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.skilrock.lms.beans.UserInfoBean;
import com.skilrock.lms.common.ConfigurationVariables;
import com.skilrock.lms.coreEngine.drawGames.common.DGErrorMsg;
import com.skilrock.lms.coreEngine.drawGames.playMgmt.DrawGameRPOSHelper;
import com.skilrock.lms.dge.beans.LottoPurchaseBean;
import com.skilrock.lms.rest.common.TransactionManager;
import com.skilrock.lms.web.drawGames.common.Util;

public class FastLottoAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int betAmountMultiple;
	private String[] drawIdArr;
	private String errMsg;
	private int gameNumber = Util.getGameId("Fastlotto");
	private int isAdvancedPlay;
	Log logger = LogFactory.getLog(FastLottoAction.class);
	private LottoPurchaseBean lottoPurchaseBean;
	private int noOfDraws;

	private String pickedNumbers;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String totalPurchaseAmt;
	private String plrMobileNumber;

	public String getPlrMobileNumber() {
		return plrMobileNumber;
	}

	public void setPlrMobileNumber(String plrMobileNumber) {
		this.plrMobileNumber = plrMobileNumber;
	}

	public int getBetAmountMultiple() {
		return betAmountMultiple;
	}

	public String[] getDrawIdArr() {
		return drawIdArr;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public int getGameNumber() {
		return gameNumber;
	}

	public int getIsAdvancedPlay() {
		return isAdvancedPlay;
	}

	public LottoPurchaseBean getLottoPurchaseBean() {
		return lottoPurchaseBean;
	}

	public int getNoOfDraws() {
		return noOfDraws;
	}

	public String getPickedNumbers() {
		return pickedNumbers;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getTotalPurchaseAmt() {
		return totalPurchaseAmt;
	}

	public String purchaseTicketProcess() throws Exception {
		logger.debug("Inside purchaseTicketProcess");
		HttpSession session = request.getSession();
		UserInfoBean userBean = (UserInfoBean) session
				.getAttribute("USER_INFO");
		List<String> playerPicked = new ArrayList<String>();
		ServletContext sc = ServletActionContext.getServletContext();
		String refMerchantId = (String) sc.getAttribute("REF_MERCHANT_ID");
		logger.debug("Merch id: " + refMerchantId);
		Map<Integer, Map<Integer, String>> drawIdTableMap = (Map<Integer, Map<Integer, String>>) sc
				.getAttribute("drawIdTableMap");
		logger.debug("Draw id Map.....: " + drawIdTableMap);
		/*
		 * String startRange=(String)session.getAttribute("START_RANGE"); String
		 * endRange=(String)session.getAttribute("END_RANGE"); String
		 * winNum=(String)session.getAttribute("WIN_NUMBERS");
		 */
		String purchaseChannel = "LMS_Web";
		String[] picknumbers = pickedNumbers.split("Nxt");
		LottoPurchaseBean lottoPurchaseBean = new LottoPurchaseBean();
		// lottoPurchaseBean.setBetAmountMultiple(betAmountMultiples);
		lottoPurchaseBean.setDrawIdTableMap(drawIdTableMap);
		lottoPurchaseBean.setGame_no(gameNumber);
		lottoPurchaseBean.setGameDispName(Util.getGameDisplayName(gameNumber));
		// lottoPurchaseBean.setIsQuickPick(isQuickPick);
		lottoPurchaseBean.setNoOfDraws(noOfDraws);
		lottoPurchaseBean.setPartyId(userBean.getUserOrgId());
		lottoPurchaseBean.setPartyType(userBean.getUserType());
		lottoPurchaseBean.setUserId(userBean.getUserId());
		lottoPurchaseBean.setRefMerchantId(refMerchantId);
		lottoPurchaseBean.setPurchaseChannel(purchaseChannel);
		lottoPurchaseBean.setIsAdvancedPlay(isAdvancedPlay);
		lottoPurchaseBean.setPlrMobileNumber(plrMobileNumber);
		if (drawIdArr != null) {
			lottoPurchaseBean.setDrawDateTime(Arrays.asList(drawIdArr));
		}
		// lottoPurchaseBean.setPanel_id(panel_id);
		// ottoPurchaseBean.setPlayerPicked(playerPicked);
		lottoPurchaseBean.setTotalPurchaseAmt(Double
				.parseDouble(totalPurchaseAmt));
		setLottoPurchaseBean(lottoPurchaseBean);
		lottoPurchaseBean.setPicknumbers(picknumbers);
		
		TransactionManager.setResponseData("true");

		if (picknumbers.length < 1) {
			lottoPurchaseBean.setSaleStatus("ERROR");
			return SUCCESS;
		}

		for (int i = 0; i < picknumbers.length; i++) {
			if (!"QP".equals(picknumbers[i])) {
				if (!Util.validateNumber(
						ConfigurationVariables.FASTLOTTO_START_RANGE,
						ConfigurationVariables.FASTLOTTO_END_RANGE,
						picknumbers[i], false)) {
					lottoPurchaseBean.setSaleStatus("ERROR");
					return SUCCESS;
				}
			}
		}

		DrawGameRPOSHelper helper = new DrawGameRPOSHelper();
		setLottoPurchaseBean(helper.fastLottoPurchaseTicket(userBean,
				lottoPurchaseBean));
		String saleStatus = getLottoPurchaseBean().getSaleStatus();
		if (!"SUCCESS".equalsIgnoreCase(saleStatus)) {
			setErrMsg(DGErrorMsg.buyErrMsg(saleStatus));
			return ERROR;
		}
		return SUCCESS;

	}

	public String reprintTicket() throws Exception {
		logger.debug("Inside reprintTicket");
		// logger.debug("inside reprint");
		HttpSession session = request.getSession();
		UserInfoBean userInfoBean = (UserInfoBean) session
				.getAttribute("USER_INFO");
		logger.debug("Before--" + new Date());
		// logger.debug("Before--"+new Date());
		DrawGameRPOSHelper helper = new DrawGameRPOSHelper();
		setLottoPurchaseBean((LottoPurchaseBean) helper
				.reprintTicket(userInfoBean));
		return SUCCESS;
	}

	public void setBetAmountMultiple(int betAmountMultiple) {
		this.betAmountMultiple = betAmountMultiple;
	}

	public void setDrawIdArr(String[] drawIdArr) {
		this.drawIdArr = drawIdArr;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public void setGameNumber(int gameNumber) {
		this.gameNumber = gameNumber;
	}

	public void setIsAdvancedPlay(int isAdvancedPlay) {
		this.isAdvancedPlay = isAdvancedPlay;
	}

	public void setLottoPurchaseBean(LottoPurchaseBean lottoPurchaseBean) {
		this.lottoPurchaseBean = lottoPurchaseBean;
	}

	public void setNoOfDraws(int noOfDraws) {
		this.noOfDraws = noOfDraws;
	}

	public void setPickedNumbers(String pickedNumbers) {
		this.pickedNumbers = pickedNumbers;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public void setTotalPurchaseAmt(String totalPurchaseAmt) {
		this.totalPurchaseAmt = totalPurchaseAmt;
	}

}
