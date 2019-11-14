package com.chay.couponprojectspring.exceptions;

/**
 * An object is an exception type and will display any general problems
 * associated with the project itself.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class CouponSystemException extends Exception {

	public CouponSystemException(String message) {
		super(message);
	}

}
