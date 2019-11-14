package com.chay.couponprojectspring.services;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.entities.CouponCaregory;
import com.chay.couponprojectspring.entities.Customer;
import com.chay.couponprojectspring.exceptions.CouponAlreadyPurchasedException;
import com.chay.couponprojectspring.exceptions.InvalidLoginException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.exceptions.ZeroCouponAmountException;

/**
 * The interface contains all functions that will eventually be able to run the
 * customer system.
 * 
 * @author Chay Mizrahi
 *
 */
public interface CustomerService {

	/**
	 * The function receives from the user password and user name the return the
	 * true is there is customer with this details or false if not.
	 * 
	 * @param name string and not null
	 * @param password string and not null
	 * @return Company
	 * @throws InvalidLoginException if the user name or the password is incorrect.
	 */
	 boolean performLogin(@ NotNull String name,@NotNull String password);
		
	/**
	 * The function will be called when the customer will buy coupon.
	 * Before the purchased we will check if the customer already purchased this coupon, if the coupon exists and if the amount of the coupon bigger then one.
	 * After the purchased the amount of coupons will be reduced by one.
	 * @param couponId must to be int.
	 * @throws CouponAlreadyPurchasedException if the customer already purchased that coupon.
	 * @throws ObjectNotExistsException if the coupon not exists.
	 * @throws ZeroCouponAmountException if the amount of the coupon is 0.
	 */
	void purchaseCoupon(@Positive int couponId,@Positive int customerId) throws  CouponAlreadyPurchasedException, ObjectNotExistsException, ZeroCouponAmountException;

	/**
	 * The function return all the coupons in the DB.
	 * @return collection of coupons.
	 */
	Collection<Coupon> getAllCoupons();

	/**
	 * The function return the specific coupon that his id received as argument.
	 * @param id must to be int.
	 * @return coupon.
	 * @throws ObjectNotExistsException if the coupon not exists.
	 */
	Coupon getCouponById(@Positive int id) throws ObjectNotExistsException;

	/**
	 * The function return all the coupon that the customer already purchased.
	 * @return collection of coupons
	 * @throws ObjectNotExistsException if the customer not exists.
	 */
	Collection<Coupon> getAllCustomerCoupons(@Positive int customerId) throws  ObjectNotExistsException;

	/**
	 * The function return the customer that  login.
	 * @return customer 
	 * @throws ObjectNotExistsException if the customer not exists.
	 */
	Customer getCustomer(@Positive int customerId) throws  ObjectNotExistsException;

	/**
	 * The function return all the coupons that belong to the category that received as argument.
	 * @param category int that represent enum of category.
	 * @return collection of coupon
	 * @throws ObjectNotExistsException
	 */
	Collection<Coupon> getCouponByCategory(CouponCaregory category) ;

	/**
	 * The function received price and return all the coupon with lower price.
	 * @param price must be double.
	 * @return collection of coupons
	 * @throws ObjectNotExistsException
	 */
	Collection<Coupon> getCouponLowerThanPrice(@Positive double price) ;

	/**
	 * The function return Customer with the specific name that received as argument.
	 * @param name must to be not null, and string.
	 * @return Customer
	 * @throws ObjectNotExistsException if there is no object with this specific name.
	 */
	Customer getCustomerByName(String name) throws ObjectNotExistsException;
	
	/**
	 * The function will check whether there is a coupon with the same name passed
	 * as an argument and returns true if there is, and false if not present.
	 * 
	 * @param couponTitle must be string and not null.
	 * @return true if there is, and false if not present.
	 */
	boolean checkIfCustomerAlraedyPurchasedCoupon(int customerId, int couponId );
	
}
