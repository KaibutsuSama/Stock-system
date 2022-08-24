package com.kaibutsusama.stock.system.common.exception;

import com.kaibutsusama.stock.system.common.exception.constants.IErrorCodeEnum;

/**
 * 自定义组件异常
 */
public class ComponentException extends AbstractException {

	/**
	 *
	 */
	private static final long serialVersionUID = 2333790764399190094L;

	/**
	 * 错误码枚举信息
	 */
	private IErrorCodeEnum errorCodeEnum;

	/**
	 * 扩展的错误信息
	 */
	private String extendErrorMessage;


	public ComponentException(IErrorCodeEnum errorCodeEnum) {
		super(errorCodeEnum.getCode() + ":" + errorCodeEnum.getMessage());
		this.errorCodeEnum = errorCodeEnum;
	}


	public ComponentException(IErrorCodeEnum errorCodeEnum, String extendErrorMessage) {
		super(errorCodeEnum.getCode() + ":" + errorCodeEnum.getMessage() + "["
				+ extendErrorMessage + "]");
		this.errorCodeEnum = errorCodeEnum;
		this.extendErrorMessage = extendErrorMessage;
	}


	public IErrorCodeEnum getErrorCodeEnum() {
		return errorCodeEnum;
	}

	public void setErrorCodeEnum(IErrorCodeEnum errorCodeEnum) {
		this.errorCodeEnum = errorCodeEnum;
	}

	public String getExtendErrorMessage() {
		return extendErrorMessage;
	}

	public void setExtendErrorMessage(String extendErrorMessage) {
		this.extendErrorMessage = extendErrorMessage;
	}

}

