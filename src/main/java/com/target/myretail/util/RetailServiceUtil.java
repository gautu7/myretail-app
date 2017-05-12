package com.target.myretail.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class RetailServiceUtil {
	
	private static final Logger LOG = Logger.getLogger(RetailServiceUtil.class);

	/*
	 * Validates the productId
	 */
	public boolean isValid(String productId) {
		try {
			Integer.parseInt(productId);
		} catch (NumberFormatException numberFormatException) {
			LOG.error("NumberFormatException" + numberFormatException.getMessage());
			return false;
		} catch (NullPointerException nullPointerException) {
			LOG.error("NullPointerException" + nullPointerException.getMessage());
			return false;
		}
		return true;
	}

}
