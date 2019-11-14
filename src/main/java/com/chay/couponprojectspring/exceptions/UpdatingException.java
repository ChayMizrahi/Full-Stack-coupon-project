package com.chay.couponprojectspring.exceptions;

/**
 * The object is an exception type because it is an heir to
 * CouponSystemException inherited itself from an exception. The object will
 * show an exception when a client tries to update an invalid value in an
 * object.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class UpdatingException extends CouponSystemException {

	public UpdatingException(String objectType, String reason, Integer id) {
		super("Can not update object of type " + objectType + " with id " + id + ". Reason: " + reason);
	}

}
