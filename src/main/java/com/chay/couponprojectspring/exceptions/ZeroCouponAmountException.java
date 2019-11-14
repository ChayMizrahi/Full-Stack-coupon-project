package com.chay.couponprojectspring.exceptions;

/**
 * The object is an exception type because it is an heir to
 * CouponSystemException inherited itself from an exception. The object will
 * display an exception when a customer tries to purchase a coupon whose
 * amount is 0.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class ZeroCouponAmountException extends CouponSystemException {

	public ZeroCouponAmountException() {
		super("This coupon is out of stock.");
	}

}
