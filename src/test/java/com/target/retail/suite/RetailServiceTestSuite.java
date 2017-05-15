package com.target.retail.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.target.retail.ProductDetailsHandlerTest;
import com.target.retail.RetailServiceTest;
import com.target.retail.dao.ProductPriceDaoImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({RetailServiceTest.class,ProductDetailsHandlerTest.class,ProductPriceDaoImplTest.class})
public class RetailServiceTestSuite {

}
