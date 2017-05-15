package com.target.retail.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.target.myretail.RetailServiceApplication;
import com.target.myretail.binder.CurrentPrice;
import com.target.myretail.binder.ProductDetails;
import com.target.myretail.dao.IProductPriceDao;
import com.target.myretail.dao.object.Product;
import com.target.myretail.exception.RetailServiceException;
import com.target.retail.constants.RetailTestConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RetailServiceApplication.class, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(locations = "classpath:application.properties")
public class ProductPriceDaoImplTest {
	
	@Autowired
	IProductPriceDao productPriceDao;
	
	@Test
	public void testGetCurrentPrice() throws RetailServiceException {

		Product product = productPriceDao.getCurrentPrice("13860428");
		assertNotNull(product.getCurrentPrice().getValue());

	}
	
	@Test(expected=RetailServiceException.class)
	public void testGetCurrentPrice_For_Null() throws RetailServiceException {
		productPriceDao.getCurrentPrice("-");
		productPriceDao.getCurrentPrice("-1");
		productPriceDao.getCurrentPrice("");
	}
	
	@Test
	public void testUpdateCurrentPrice_For_ValidValues() throws RetailServiceException{
		ProductDetails productDetails = setProductDetails("13860428");
		String response = productPriceDao.updateCurrentPrice(productDetails);
		assertEquals(RetailTestConstants.UPDATE_RESPONSE, response);
	}
	
	@Test(expected = RetailServiceException.class)
	public void testUpdateCurrentPrice_For_InvalidValues() throws RetailServiceException {
		ProductDetails productDetails = new ProductDetails();
		productDetails = setProductDetails("");
		productPriceDao.updateCurrentPrice(productDetails);

		productDetails = setProductDetails("S12345");
		productPriceDao.updateCurrentPrice(productDetails);

	}
	
	private ProductDetails setProductDetails(String productId) {
		ProductDetails productDetails = new ProductDetails();
		productDetails.setId(productId);
		CurrentPrice currentPrice = new CurrentPrice();

		currentPrice.setValue("20.00");
		currentPrice.setCurrency_code("USD");
		productDetails.setCurrent_price(currentPrice);

		return productDetails;

	}

	
}
