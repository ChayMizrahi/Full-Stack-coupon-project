package com.chay.couponprojectspring.exceptions;

/**
 * The object is of the Exception type because it inherits from
 * CouponSystemException that inherits itself from Exception. The object will
 * display an exception when any company tries to perform an action on a coupon
 * that does not belong to it.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class CouponBelongToAnotherCompanyException extends CouponSystemException {

	public CouponBelongToAnotherCompanyException() {
		super(" You can not see or perform actions on coupons that do not belong to your company.");
	}

}