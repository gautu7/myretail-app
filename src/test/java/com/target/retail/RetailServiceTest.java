package com.target.retail;

import static org.junit.Assert.assertEquals;

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
import com.target.retail.constants.RetailTestConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RetailServiceApplication.class, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(locations = "classpath:application.properties")
public class RetailServiceTest {

	@Autowired
	IRetailService retailService;

	@Test
	public void testGetProductDetails_ValidInput() throws RetailServiceException {
		ProductDetails productDetails = retailService.getProductDetails("13860428");
		assertEquals("The Big Lebowski (Blu-ray)", productDetails.getName());
	}
	
	@Test(expected = RetailServiceException.class)
	public void testGetProductDetails_InValid_ProductNotFound() throws RetailServiceException {
		retailService.getProductDetails("123456");
	}
	
	@Test(expected = RetailServiceException.class)
	public void testGetProductDetails_InValid_Integer_Input() throws RetailServiceException {
		retailService.getProductDetails("S12345");
	}
	
	@Test(expected = RetailServiceException.class)
	public void testGetProductDetails_Special_Char_Input() throws RetailServiceException {
		retailService.getProductDetails("@^*&!13860428");
	}
	
	@Test
	public void testUpdateProductDetails_ValidInput() throws RetailServiceException {
		CurrentPrice currPrice = new CurrentPrice();
		currPrice.setValue("20.00");
		currPrice.setCurrency_code("USD");
		ProductDetails productDetails = setProductDetailsRequest("13860428",currPrice);
		productDetails = retailService.updateProductDetails("13860428", productDetails);
		assertEquals(RetailTestConstants.UPDATE_RESPONSE, productDetails.getUpdateStatus());
	}
	
	@Test
	public void testUpdateProductDetails_Valid_Decimal_Input() throws RetailServiceException {
		CurrentPrice currPrice = new CurrentPrice();
		currPrice.setValue("1234.16");
		currPrice.setCurrency_code("USD");
		ProductDetails productDetails = setProductDetailsRequest("13860428",currPrice);
		productDetails = retailService.updateProductDetails("13860428", productDetails);
		assertEquals(RetailTestConstants.UPDATE_RESPONSE, productDetails.getUpdateStatus());
	}
	
	@Test(expected = RetailServiceException.class)
	public void testUpdateProductDetails_InValid_Integer_Input() throws RetailServiceException {
		CurrentPrice currPrice = new CurrentPrice();
		currPrice.setValue("20.00");
		currPrice.setCurrency_code("USD");
		ProductDetails productDetails = setProductDetailsRequest("S1DR60428",currPrice);
		retailService.updateProductDetails("S1DR60428", productDetails);
	}
	
	@Test(expected = RetailServiceException.class)
	public void testUpdateProductDetails_Negative_Integer_Input() throws RetailServiceException {
		CurrentPrice currPrice = new CurrentPrice();
		currPrice.setValue("-10.00");
		currPrice.setCurrency_code("USD");
		ProductDetails productDetails = setProductDetailsRequest("13860428",currPrice);
		retailService.updateProductDetails("13860428", productDetails);
	}
	
	@Test(expected = RetailServiceException.class)
	public void testUpdateProductDetails_InValid_ProductNotFound() throws RetailServiceException {
		CurrentPrice currPrice = new CurrentPrice();
		currPrice.setValue("20.00");
		currPrice.setCurrency_code("USD");
		ProductDetails productDetails = setProductDetailsRequest("15643793",currPrice);
		retailService.updateProductDetails("56789873", productDetails);
	}
	
	@Test(expected = RetailServiceException.class)
	public void testUpdateProductDetails_InValid_CurrencyCode() throws RetailServiceException {
		CurrentPrice currPrice = new CurrentPrice();
		currPrice.setValue("20.00");
		currPrice.setCurrency_code("IND");
		ProductDetails productDetails = setProductDetailsRequest("15643793",currPrice);
		retailService.updateProductDetails("56789873", productDetails);
	}
	
	public ProductDetails setProductDetailsRequest(String productId,CurrentPrice currentPrice){
		
		ProductDetails productDetails = new ProductDetails();
		productDetails.setId(productId);
		productDetails.setName("The Big Lebowski (Blu-ray)");
		productDetails.setCurrent_price(currentPrice);
		
		return productDetails;
		
	}

}
