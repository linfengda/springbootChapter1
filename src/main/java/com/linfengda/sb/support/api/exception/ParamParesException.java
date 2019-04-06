package com.linfengda.sb.support.api.exception;

import com.linfengda.sb.chapter1.common.entity.ErrorCode;

/**
 * 描述: paramPares exception
 *
 * @author linfengda
 * @create 2018-08-17 16:40
 */
public class ParamParesException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ParamParesException(String msg) {
		super(msg);
		this.msg = msg;
	}

	private int code = ErrorCode.COMMON_PARAM_ERROR_CODE;
	
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
