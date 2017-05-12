package com.target.myretail.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.target.myretail.binder.ProductDetails;
import com.target.myretail.constants.RetailConstants;
import com.target.myretail.exception.RetailServiceException;
import com.target.myretail.product.handler.ProductDetailsHandler;

@Component
public class RetailServiceImpl implements IRetailService {

	private static final Logger LOG = Logger.getLogger(RetailServiceImpl.class);

	@Autowired
	private ProductDetailsHandler productDetailsHandler;

	@Override
	public String ping() {
		return RetailConstants.RETAIL_SERVICE_APPLICATION_MESSAGE;
	}

	@Override
	public ProductDetails getProductDetails(@PathVariable(RetailConstants.PRODUCT_ID) String productId) throws RetailServiceException {
		LOG.info("getProductDetails API");
		return productDetailsHandler.getProductDetails(productId);
	}

	@Override
	public String updateProductDetails(@PathVariable(RetailConstants.PRODUCT_ID) String productId, @RequestBody ProductDetails productDetails) throws RetailServiceException {
		LOG.info("updateProductDetails API");
		return productDetailsHandler.updateProductDetails(productId, productDetails);
	}

}
