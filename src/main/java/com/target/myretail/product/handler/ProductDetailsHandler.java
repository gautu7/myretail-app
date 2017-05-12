package com.target.myretail.product.handler;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.target.myretail.binder.CurrentPrice;
import com.target.myretail.binder.ProductDetails;
import com.target.myretail.client.RedskyClient;
import com.target.myretail.dao.IProductPriceDao;
import com.target.myretail.dao.object.Product;
import com.target.myretail.exception.ErrorDetail;
import com.target.myretail.exception.RetailServiceException;
import com.target.myretail.util.RetailServiceUtil;

@Component
public class ProductDetailsHandler {

	private static final Logger LOG = Logger.getLogger(ProductDetailsHandler.class);

	@Autowired
	private RetailServiceUtil retailServiceUtil;
	
	@Autowired
	private IProductPriceDao iProductPriceDao;
	
	@Autowired
	private RedskyClient redskyClient;

	/*
	 * Fetch Product Details and create the ProductDetailsResponse
	 */
	public ProductDetails getProductDetails(String productId) throws RetailServiceException {

		LOG.info("Get Product Details for productId" + productId);

		ProductDetails productDetailsResponse = null;
		if (validProductId(productId)) {
			productDetailsResponse = new ProductDetails();
			// Get Product Name from External API
			String productName = redskyClient.getProductName(productId);
			// Fetch price details from DB
			Product product = iProductPriceDao.getCurrentPrice(productId);
			// Form JSON response
			productDetailsResponse.setId(productId);
			productDetailsResponse.setName(productName);

			CurrentPrice currentPrice = new CurrentPrice();
			currentPrice.setValue(product.getCurrentPrice().getValue());
			currentPrice.setCurrency_code(product.getCurrentPrice().getCurrency_code());
			productDetailsResponse.setCurrent_price(currentPrice);
		}
		return productDetailsResponse;
	}

	/*
	 * Update Product Details
	 */
	public String updateProductDetails(String productId, ProductDetails productDetails) throws RetailServiceException {

		LOG.info("Price to be updated for Product" + productId);
		String response = null;
		if (validProductId(productId)) {
			response = iProductPriceDao.updateCurrentPrice(productDetails);
		}
		return response;
	}
	
	/*
	 * This method validates the ProductID
	 */
	private boolean validProductId(String productId) throws RetailServiceException {
		if (retailServiceUtil.isValid(productId)) {
			return true;
		} else {
			throw new RetailServiceException(ErrorDetail.INVALID_PRODUCT_ID);
		}
	}

}
