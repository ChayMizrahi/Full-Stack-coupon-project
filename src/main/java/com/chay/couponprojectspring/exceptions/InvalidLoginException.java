package com.chay.couponprojectspring.exceptions;

/**
 * The object is an exception type because it is an heir to
 * CouponSystemException inherited itself from an exception. The object will
 * display an exception when a client tries to log on to the system and will
 * fail because of incorrect details.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class InvalidLoginException extends CouponSystemException {
	public InvalidLoginException() {
		super("Password or user name is incorrect");
	}
}
