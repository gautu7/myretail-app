package com.target.retail;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


import com.target.myretail.binder.ProductDetails;
import com.target.myretail.client.RedskyClient;
import com.target.myretail.dao.ProductPriceDaoImpl;
import com.target.myretail.dao.object.CurrentPrice;
import com.target.myretail.dao.object.Product;
import com.target.myretail.exception.RetailServiceException;
import com.target.myretail.product.handler.ProductDetailsHandler;
import com.target.myretail.util.RetailServiceUtil;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsHandlerTest {

	@Mock
	private RetailServiceUtil retailServiceUtil;

	@Mock
	private ProductPriceDaoImpl productPriceDaoImpl;

	@Mock
	private RedskyClient redskyClient;
	
	@InjectMocks
	private ProductDetailsHandler productDetailsHandler;

	@Before
	public void setUp() throws RetailServiceException {
		
		Product product = new Product();
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setValue("20.00");
		currentPrice.setCurrency_code("USD");
		product.setCurrentPrice(currentPrice);
		
		Mockito.when(retailServiceUtil.isValid(Matchers.anyString())).thenReturn(true);
		Mockito.when(redskyClient.getProductName(Matchers.anyString())).thenReturn(new String());
		Mockito.when(productPriceDaoImpl.getCurrentPrice(Matchers.anyString())).thenReturn(product);
		Mockito.when(productPriceDaoImpl.updateCurrentPrice(Matchers.any(ProductDetails.class))).thenReturn(new String());
	}

	@Test
	public void testGetProductDetails() throws RetailServiceException {
		assertNotNull(productDetailsHandler.getProductDetails("13860428"));
	}
	
	@Test
	public void testUpdateProductDetails() throws RetailServiceException {
		assertNotNull(productDetailsHandler.updateProductDetails("13860428", new ProductDetails()));
	}
	
	@Test(expected = RetailServiceException.class)
	public void testUpdateProductDetails_RetailServiceException() throws RetailServiceException{
		Mockito.when(retailServiceUtil.isValid(Matchers.anyString())).thenReturn(false);
		productDetailsHandler.updateProductDetails("13860428", new ProductDetails());
	}
	
	@Test(expected = RetailServiceException.class)
	public void testGetProductDetails_RetailServiceException() throws RetailServiceException {
		Mockito.when(retailServiceUtil.isValid(Matchers.anyString())).thenReturn(false);
		productDetailsHandler.getProductDetails("13860428");
	}

}
