package com.xxyp.admin.common.common;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {

	private static final long serialVersionUID = -7228469805686712268L;
	private String errorMsg;
	private String successMsg;
	private String errorCode;
	private String errorDetailStack;
	private boolean successFlag = true;

	public boolean getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		this.successFlag = false;
	}
	
	//成功需要提示信息返回给页面
	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	
	public String getSuccessMsg() {
		return this.successMsg;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDetailStack() {
		return this.errorDetailStack;
	}

	public void setErrorDetailStack(String errorDetailStack) {
		this.errorDetailStack = errorDetailStack;
		this.successFlag = false;
	}


	public BaseResult<T> withError(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.successFlag = false;
		return this;
	}

	/*public BaseResult<T> withError(SinafenqiExceptionCode codeObj) {
		if (codeObj != null) {
			this.errorCode = codeObj.getCode();
			this.errorMsg = codeObj.getMsg();
		}
		this.isSuccess = false;
		return this;
	}*/
}