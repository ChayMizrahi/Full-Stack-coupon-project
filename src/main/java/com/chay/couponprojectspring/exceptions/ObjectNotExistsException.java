package com.chay.couponprojectspring.exceptions;

/**
 * The object is an exception type because it is an heir to
 * CouponSystemException inherited itself from an exception. The object will
 * display an exception when a client tries to perform an action on an object
 * that does not exist in the system.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class ObjectNotExistsException extends CouponSystemException {

	public ObjectNotExistsException(String objectType, Integer objectId) {
		super("Could not find the object from type " + objectType + " with the id " + objectId);
	}

}
