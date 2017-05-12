package com.target.retail.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.target.retail.ProductDetailsHandlerTest;
import com.target.retail.RetailServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({RetailServiceTest.class,ProductDetailsHandlerTest.class})
public class RetailServiceTestSuite {

}
