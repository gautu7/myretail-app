package com.target.retail;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
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
import com.target.myretail.exception.RetailServiceException;
import com.target.myretail.service.IRetailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RetailServiceApplication.class, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(locations = "classpath:application.properties")
public class RetailServiceTest {

	@Autowired
	IRetailService retailService;

	@Before
	public void setUp() {
		
	}

	@Test
	public void testGetProductDetails_ValidInput() throws RetailServiceException {
		ProductDetails productDetails = retailService.getProductDetails("13860428");
		assertEquals("The Big Lebowski (Blu-ray)", productDetails.getName());
	}
	
	@Test(expected = RetailServiceException.class)
	public void testGetProductDetails_InValid_ProductNotFound() throws RetailServiceException {
		ProductDetails productDetails = retailService.getProductDetails("123456");
		assertEquals("102", productDetails.getErrors().getErrorCode());
	}
	
	@Test(expected = RetailServiceException.class)
	public void testGetProductDetails_InValid_IntegerInput() throws RetailServiceException {
		ProductDetails productDetails = retailService.getProductDetails("S12345");
		assertEquals("103",  productDetails.getErrors().getErrorCode());
	}
	
	@Test
	public void testUpdateProductDetails_ValidInput() throws RetailServiceException {
		ProductDetails productDetails = setProductDetailsRequest("13860428");
		String response = retailService.updateProductDetails("13860428", productDetails);
		assertEquals("PRODUCT PRICE UPDATED SUCCESSFULLY", response);
	}
	
	@Test(expected = RetailServiceException.class)
	public void testUpdateProductDetails_InValid_IntegerInput() throws RetailServiceException {
		ProductDetails productDetails = setProductDetailsRequest("S1DR60428");
		String response = retailService.updateProductDetails("S1DR60428", productDetails);
		
		assertEquals("PRODUCT PRICE UPDATED SUCCESSFULLY", response);
	}
	
	@Test(expected = RetailServiceException.class)
	public void testUpdateProductDetails_InValid_ProductNotFound() throws RetailServiceException {
		ProductDetails productDetails = setProductDetailsRequest("56789873");
		String response = retailService.updateProductDetails("56789873", productDetails);
		
		assertEquals("PRODUCT PRICE UPDATED SUCCESSFULLY", response);
	}
	
	public ProductDetails setProductDetailsRequest(String productId){
		
		ProductDetails productDetails = new ProductDetails();
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setValue("20.00");
		currentPrice.setCurrency_code("USD");
		
		productDetails.setId(productId);
		productDetails.setName("The Big Lebowski (Blu-ray)");
		productDetails.setCurrent_price(currentPrice);
		
		return productDetails;
		
	}

}
