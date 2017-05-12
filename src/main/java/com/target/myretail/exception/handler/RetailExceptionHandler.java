package com.target.myretail.exception.handler;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.target.myretail.binder.ProductDetails;
import com.target.myretail.binder.Error;
import com.target.myretail.exception.ErrorDetail;
import com.target.myretail.exception.RetailServiceException;

@ControllerAdvice
public class RetailExceptionHandler {
	
	private static final Logger LOG = Logger.getLogger(RetailExceptionHandler.class);
	
	@ExceptionHandler(RetailServiceException.class)
	@ResponseBody
	public ProductDetails handleNoDataFound(RetailServiceException retailServiceException) {

		LOG.info("RetailServiceException caused by" + retailServiceException.getMessage());
		ProductDetails productDetails = new ProductDetails();
		ErrorDetail errorDetail = retailServiceException.getErrorCode();

		Error error = new Error();
		error.setErrorCode(errorDetail.getCode());
		error.setErrorDescription(errorDetail.getDescription());
		productDetails.setErrors(error);

		return productDetails;

	}

}
