package com.kaibutsusama.stock.system.common.exception;


import com.kaibutsusama.stock.system.common.exception.constants.IErrorCodeEnum;

/**
 * 自定义业务异常
 */
public class BusinessException extends AbstractException {

	/**
	 *
	 */
	private static final long serialVersionUID = -1L;

	/**
	 * 错误码
	 */
	private IErrorCodeEnum errorCodeEnum;


	public BusinessException(IErrorCodeEnum errorCodeEnum) {
		super(errorCodeEnum.getCode() + ":" + errorCodeEnum.getMessage());
		this.errorCodeEnum = errorCodeEnum;
	}

	public IErrorCodeEnum geterrorCodeEnum() {
		return errorCodeEnum;
	}

}
