package com.target.myretail.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.target.myretail.binder.ProductDetails;
import com.target.myretail.constants.RetailConstants;
import com.target.myretail.exception.RetailServiceException;

@Service
@RestController
@RequestMapping(RetailConstants.MYRETAIL_BASE_URL)
public interface IRetailService {
	
	@GetMapping(RetailConstants.PING_URL)
	public String ping();

	@GetMapping(value = RetailConstants.GET_PRODUCT_DETAILS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDetails getProductDetails(@PathVariable(RetailConstants.PRODUCT_ID) String productId) throws RetailServiceException;
	
	@PutMapping(value = RetailConstants.GET_PRODUCT_DETAILS_URI, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String updateProductDetails(@PathVariable(RetailConstants.PRODUCT_ID) String productId, @RequestBody ProductDetails productDetails) throws RetailServiceException;

}
