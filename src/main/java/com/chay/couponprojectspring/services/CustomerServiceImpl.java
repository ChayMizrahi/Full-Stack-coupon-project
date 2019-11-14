package com.chay.couponprojectspring.services;

import java.util.Collection;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.entities.CouponCaregory;
import com.chay.couponprojectspring.entities.Customer;
import com.chay.couponprojectspring.exceptions.CouponAlreadyPurchasedException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.exceptions.ZeroCouponAmountException;
import com.chay.couponprojectspring.repositories.CouponRepository;
import com.chay.couponprojectspring.repositories.CustomerRepository;

/**
 * The class implements all existing functions in the customerService interface
 * 
 * @author Chay Mizrahi
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	private CouponRepository couponRepository;
	private CustomerRepository customerRepository;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, CouponRepository couponRepository) {
		this.couponRepository = couponRepository;
		this.customerRepository = customerRepository;
	}

	public boolean performLogin(String name, String password) {
		Customer customer = customerRepository.findByNameAndPassword(name, password);
		if (customer == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void purchaseCoupon(int couponId, int customerId)
			throws CouponAlreadyPurchasedException, ObjectNotExistsException, ZeroCouponAmountException {
		// Check if the coupon exists.
		if (couponRepository.findById(couponId).isPresent()) {
			// Check if the customer already purchased this coupon.
			if (!checkIfCustomerAlraedyPurchasedCoupon(customerId, couponId)) {
				Coupon coupon = couponRepository.findById(couponId).get();
				Customer customer = customerRepository.findById(customerId).get();
				// Check if the amount of the coupon bigger then 0.
				if (coupon.getAmount() > 0) {
					// Reduces in 1 amount of coupons
					coupon.setAmount(coupon.getAmount() - 1);
					customer.addCoupon(coupon);
				} else {
					throw new ZeroCouponAmountException();
				}
			} else {
				throw new CouponAlreadyPurchasedException();
			}
		} else {
			throw new ObjectNotExistsException("Coupon", couponId);
		}

	}

	@Override
	public Collection<Coupon> getAllCoupons() {
		return couponRepository.findAll();
	}

	@Override
	public Coupon getCouponById(@Positive int id) throws ObjectNotExistsException {
		if (couponRepository.findById(id).isPresent()) {
			return couponRepository.findById(id).get();
		} else {
			throw new ObjectNotExistsException("Coupon", id);
		}
	}

	@Override
	public Collection<Coupon> getAllCustomerCoupons(@Positive int customerId) throws ObjectNotExistsException {
		return couponRepository.couponsCustomerByCustomerId(customerId);
	}

	@Override
	public Customer getCustomer(@Positive int customerId) throws ObjectNotExistsException {
		if (customerRepository.findById(customerId).isPresent()) {
			return customerRepository.findById(customerId).get();
		} else {
			throw new ObjectNotExistsException("Customer", customerId);
		}
	}

	public Collection<Coupon> getCouponNotPurchased(@Positive int customerId) throws ObjectNotExistsException {
		if (customerRepository.findById(customerId).isPresent()) {
			Collection<Coupon> customerCoupons = couponRepository.couponsCustomerByCustomerId(customerId);
			Collection<Coupon> coupons = couponRepository.findAll();
			coupons.removeAll(customerCoupons);
			return coupons;
		} else {
			throw new ObjectNotExistsException("Customer", customerId);
		}
	}

	@Override
	public Collection<Coupon> getCouponByCategory(@NotNull CouponCaregory category) {
		return couponRepository.findByCategory(category);
	}

	@Override
	public Collection<Coupon> getCouponLowerThanPrice(@Positive double price) {
		return couponRepository.findByPriceLessThan(price);

	}

	@Override
	public Customer getCustomerByName(@NotBlank String name) throws ObjectNotExistsException {
		Customer customer = customerRepository.findByName(name);
		if (customer == null) {
			throw new ObjectNotExistsException("Customer", -1);
		}
		return customer;
	}

	@Override
	public boolean checkIfCustomerAlraedyPurchasedCoupon(int customerId, int couponId) {
		Coupon coupon = couponRepository.couponByCustomerIdAndCouponId(customerId, couponId);
		if (coupon == null) {
			return false;
		} else {
			return true;
		}
	}

}
