package com.target.retail.dao;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.target.myretail.RetailServiceApplication;
import com.target.myretail.dao.IProductPriceDao;
import com.target.myretail.dao.object.Product;
import com.target.myretail.exception.RetailServiceException;

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

	
}
