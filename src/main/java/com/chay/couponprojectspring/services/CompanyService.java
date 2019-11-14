package com.chay.couponprojectspring.services;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.chay.couponprojectspring.entities.Company;
import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.exceptions.CouponBelongToAnotherCompanyException;
import com.chay.couponprojectspring.exceptions.CouponSystemException;
import com.chay.couponprojectspring.exceptions.InvalidLoginException;
import com.chay.couponprojectspring.exceptions.ObjectAlreadyExistsException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.exceptions.UpdatingException;

/**
 * The interface contains all functions that will eventually be able to run the
 * company system.
 * 
 * @author Chay Mizrahi
 *
 */
public interface CompanyService {

	/**
	 * The function receives from the user password and user name the return the
	 * company with this specific password and user name.
	 * 
	 * @param name
	 * @param password
	 * @return the specific company.
	 * @throws InvalidLoginException if the user name or the password is incorrect.
	 */
	boolean performLogin(String name, String password);

	/**
	 * The function Saves a given Coupon in the DB and the Coupon location of the
	 * coupon. Before adding, we check whether there is already a coupon with the
	 * title of the new coupon if there is the exception
	 * ObjectAlreadyExistsException will be throw and set the company with the
	 * specific id that received as argument as the onwer of the coupon.
	 * 
	 * 
	 * @param coupon    the coupon that will inserted to the DB
	 * @param companyId must be positive.
	 * @throws ObjectAlreadyExistsException if there is already coupon with the same
	 *                                      title.
	 * @throws ObjectNotExistsException     if not exists company with the id that
	 *                                      received as argument.
	 */
	Coupon insertCoupon(@Valid Coupon coupon, @Positive int companyId)
			throws ObjectAlreadyExistsException, ObjectNotExistsException;

	/**
	 * The function remove from the DB the specific whose ID was received in the
	 * argument.
	 * 
	 * @param couponId can not be null.
	 * @throws ObjectNotExistsException              if the there is not coupon with
	 *                                               this id.
	 * @throws CouponBelongToAnotherCompanyException if the coupon not belong to the
	 *                                               company that try to remove him.
	 */
	void removeCoupon(@Positive int couponId, @Positive int companyId)
			throws ObjectNotExistsException, CouponBelongToAnotherCompanyException;

	/**
	 * The function will updated the detail of the specific coupon. Before the
	 * updating it will check if the coupon exists, if the coupon belong to the
	 * company that try to update him and if the coupon's name don't has a change.
	 * 
	 * @param coupon
	 * @return
	 * @throws ObjectNotExistsException              if the coupon not exists.
	 * @throws UpdatingException                     if the title of the coupon has
	 *                                               change.
	 * @throws CouponBelongToAnotherCompanyException if the coupon dint belong to
	 *                                               the company that try to update
	 *                                               him.
	 * @throws CouponSystemException
	 */
	Coupon updateCoupon(@Valid Coupon coupon, @Positive int companyId) throws ObjectNotExistsException,
			UpdatingException, CouponBelongToAnotherCompanyException, CouponSystemException;

	/**
	 * The function will return the specifies coupon that received as argument.
	 * Before the returning the function will check if the coupon exists and if the
	 * coupon belong to the company that try to get him.
	 * 
	 * @param couponId must be integer.
	 * @return coupon.
	 * @throws ObjectNotExistsException              if the coupon not exists.
	 * @throws CouponBelongToAnotherCompanyException if the coupon belong to another
	 *                                               company.
	 */
	Coupon getCoupon(@Positive int couponId, @Positive int companyId)
			throws ObjectNotExistsException, CouponBelongToAnotherCompanyException;

	/**
	 * The function will return the company that in the facade.
	 * 
	 * @return company
	 * @throws ObjectNotExistsException if the company equal to null.
	 */
	Company getCompany(@Positive int companyId) throws ObjectNotExistsException;

	/**
	 * The function return Company with the specific name that received as argument.
	 * 
	 * @param name must to be not null, and string.
	 * @return Company
	 * @throws ObjectNotExistsException if there is no object with this specific
	 *                                  name.
	 */
	Company getComapnyByName(@NotNull String name) throws ObjectNotExistsException;

	/**
	 * The function returns all coupons belonging to the company that id is accepted
	 * as an argument.
	 * 
	 * @return must be positive.
	 * @throws ObjectNotExistsException If there is no company with id as it
	 *                                  received as an argument.
	 */
	Collection<Coupon> getCompanyCoupons(@Positive int companyId) throws ObjectNotExistsException;

	/**
	 * The function will check whether there is a coupon with the same name passed
	 * as an argument and returns true if there is, and false if not present.
	 * 
	 * @param couponTitle must be string and not null.
	 * @return true if there is, and false if not present.
	 */
	boolean checkIfCouponTitleAlreadyExists(@NotNull String couponTitle);

}
