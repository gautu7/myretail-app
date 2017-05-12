package com.target.myretail.dao;

import org.springframework.stereotype.Component;

import com.target.myretail.binder.ProductDetails;
import com.target.myretail.dao.object.Product;
import com.target.myretail.exception.RetailServiceException;

@Component
public interface IProductPriceDao {

	/**
	 * Get Current Price from MongoDB for a product Id
	 * @Param productId
	 */
	public Product getCurrentPrice(String productId) throws RetailServiceException;
	
	/**
	 * Update Current Price for a product Id
	 * @Param ProductDetails
	 */
	public String updateCurrentPrice(ProductDetails productDetails) throws RetailServiceException;


}
