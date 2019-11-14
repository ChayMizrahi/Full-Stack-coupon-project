package com.chay.couponprojectspring.exceptions;

/**
 * The object is of the Exception type because it inherits from
 * CouponSystemException that inherits itself from Exception. The object will
 * display an exception when a user of the customer type tries to purchase a
 * coupon that has already been purchased.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class CouponAlreadyPurchasedException extends CouponSystemException {
	
	public CouponAlreadyPurchasedException() {
		super("You can not buy a coupon that you have already purchased.");
	}
}
