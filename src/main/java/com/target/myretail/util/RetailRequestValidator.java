package com.target.myretail.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.target.myretail.binder.CurrentPrice;
import com.target.myretail.exception.ErrorDetail;
import com.target.myretail.exception.RetailServiceException;

@Component
public class RetailRequestValidator {

	private static final Logger LOG = Logger.getLogger(RetailRequestValidator.class);

	private Set<String> currencyCodes;

	@PostConstruct
	public void init() {
		currencyCodes = new HashSet<String>(Arrays.asList("EUR", "USD", "AUD", "CAD", "JPY"));
	}

	public void validateCurrentPrice(CurrentPrice currentPrice) throws RetailServiceException {

		String regex = "^[0-9]{1,13}(\\.[0-9]*)?";

		if (StringUtils.isEmpty(currentPrice.getValue())) {
			throw new RetailServiceException(ErrorDetail.INVALID_PRICE_VALUE);
		}

		if (!Pattern.matches(regex, currentPrice.getValue())) {
			throw new RetailServiceException(ErrorDetail.INVALID_PRICE_VALUE);
		}

		if (Double.parseDouble(currentPrice.getValue()) < 0
				|| !Double.isFinite(Double.parseDouble(currentPrice.getValue()))) {
			throw new RetailServiceException(ErrorDetail.INVALID_PRICE_VALUE);
		}

		if (!currencyCodes.contains((currentPrice.getCurrency_code()))
				|| StringUtils.isEmpty(currentPrice.getCurrency_code())) {
			throw new RetailServiceException(ErrorDetail.INVALID_CURRENCY_CODE);
		}

	}

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
