package com.target.myretail.exception;

public enum ErrorDetail {

	REDSKY_CLIENT_DOWN("101", "Redsky Client Exception, please retry"), DATABASE_NOT_FOUND("102","Product does not exist in the database"), 
	INVALID_PRODUCT_ID("103","ProductId Invalid, retry with a Valid Integer"),REDSKY_CLIENT_NO_DATA("104","Product Not found in Redsky"),
	INVALID_PRICE_VALUE("105","Please enter a valid Price value"),INVALID_CURRENCY_CODE("106","Please enter a valid Currency value");

	private final String code;
	private final String description;

	ErrorDetail(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}
}
