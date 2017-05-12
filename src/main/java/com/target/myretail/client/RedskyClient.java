package com.target.myretail.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.target.myretail.constants.RetailConstants;
import com.target.myretail.domain.ProductDetails;
import com.target.myretail.exception.ErrorDetail;
import com.target.myretail.exception.RetailServiceException;


@Component
public class RedskyClient {
	
	private static final Logger LOG = Logger.getLogger(RedskyClient.class);
	
	@Value("${target.product.details.api}")
	private String endpoint;
	
	@Value("${target.product.details.excludes}")
	private String exclusion;
	
	@Autowired
	RestTemplate restTemplate;
	
	public String getProductName(String productId) throws RetailServiceException {

		String productName = null;
		try {
			StringBuffer productUrl = new StringBuffer();
			productUrl.append(endpoint).append(productId);

			Map<String, String> params = new HashMap<String, String>();
			params.put(RetailConstants.EXCLUDES, exclusion);

			ProductDetails productDetails = restTemplate.getForObject(productUrl.toString(), ProductDetails.class,
					params);

			productName = productDetails.getProduct().getItem().getProductDescription().getTitle();
			LOG.info("productName" + productName);
			if (StringUtils.isEmpty(productName)) {
				throw new RetailServiceException(ErrorDetail.REDSKY_CLIENT_NO_DATA);
			}
		} catch (RestClientException restClientException) {
			throw new RetailServiceException(ErrorDetail.REDSKY_CLIENT_DOWN);
		}

		return productName;

	}

}
