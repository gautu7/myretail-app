package com.target.myretail.exception;


public class RetailServiceException extends Exception {

	private static final long serialVersionUID = 6742403998893281931L;

	private ErrorDetail errorCode;
	
	public RetailServiceException(ErrorDetail errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
	}

	public ErrorDetail getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorDetail errorCode) {
		this.errorCode = errorCode;
	}

}
