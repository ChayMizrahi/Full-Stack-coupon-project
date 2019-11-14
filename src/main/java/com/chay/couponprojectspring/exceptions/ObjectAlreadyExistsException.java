package com.chay.couponprojectspring.exceptions;

/**
 * The object is an exception type because it is an heir to
 * CouponSystemException inherited itself from an exception. The object will
 * display an exception when a client tries to insert an object with a name that
 * already exists in the system.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class ObjectAlreadyExistsException extends CouponSystemException {
	public ObjectAlreadyExistsException(String message) {
		super(message);
	}

}
