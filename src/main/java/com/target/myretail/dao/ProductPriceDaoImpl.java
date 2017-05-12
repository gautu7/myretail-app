package com.target.myretail.dao;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.target.myretail.binder.ProductDetails;
import com.target.myretail.constants.RetailConstants;
import com.target.myretail.dao.object.CurrentPrice;
import com.target.myretail.dao.object.Product;
import com.target.myretail.exception.RetailServiceException;
import com.target.myretail.exception.ErrorDetail;


@Component
public class ProductPriceDaoImpl implements IProductPriceDao{
	
	private static final Logger LOG = Logger.getLogger(ProductPriceDaoImpl.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Product getCurrentPrice(String productId) throws RetailServiceException {
		Query findProductQuery = new Query();
		findProductQuery.addCriteria(Criteria.where(RetailConstants.PRODUCT_ID_DOCUMENT).is(productId));
		Product product = mongoTemplate.findOne(findProductQuery, Product.class, RetailConstants.PRODUCT_ID_COLLECTION);

		if (product == null) {
			throw new RetailServiceException(ErrorDetail.DATABASE_NOT_FOUND);
		}

		LOG.info("Currency Code for Product" + productId + "is" + product.getCurrentPrice().getCurrency_code());
		return product;
	}

	@Override
	public String updateCurrentPrice(ProductDetails productDetails) throws RetailServiceException {
		Query findProductQuery = new Query();
		findProductQuery.addCriteria(Criteria.where(RetailConstants.PRODUCT_ID_DOCUMENT).is(productDetails.getId()));
		Product product = mongoTemplate.findOne(findProductQuery, Product.class, RetailConstants.PRODUCT_ID_COLLECTION);
		
		if (product == null) {
			throw new RetailServiceException(ErrorDetail.DATABASE_NOT_FOUND);
		}
		
		CurrentPrice currentPrice = product.getCurrentPrice();
		currentPrice.setValue(productDetails.getCurrent_price().getValue());
		product.setCurrentPrice(currentPrice);
		mongoTemplate.save(product);
		
		return RetailConstants.UPDATE_SUCCESS_MESSAGE;
	}

}
