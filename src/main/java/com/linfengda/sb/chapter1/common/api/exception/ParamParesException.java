package com.linfengda.sb.chapter1.common.api.exception;

import com.linfengda.sb.chapter1.common.exception.entity.ErrorCode;
import lombok.Getter;

/**
 * 描述: paramPares exception
 *
 * @author linfengda
 * @create 2018-08-17 16:40
 */
@Getter
public class ParamParesException extends RuntimeException{

	public ParamParesException(String msg) {
		this.msg = msg;
	}

	public ParamParesException(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private int code = ErrorCode.COMMON_PARAM_ERROR_CODE;

	private String msg;
}
