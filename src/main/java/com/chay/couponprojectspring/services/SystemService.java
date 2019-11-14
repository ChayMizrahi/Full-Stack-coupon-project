package com.chay.couponprojectspring.services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.chay.couponprojectspring.entities.Company;
import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.entities.CustomLogin;
import com.chay.couponprojectspring.entities.Customer;
import com.chay.couponprojectspring.entities.Log;
import com.chay.couponprojectspring.entities.LoginType;
import com.chay.couponprojectspring.exceptions.InvalidLoginException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.repositories.CouponRepository;
import com.chay.couponprojectspring.repositories.LogRepository;

/**
 * The class will perform general actions related to the system. Log in, and
 * delete expired coupons.
 * 
 * @author Chay Mizrahi
 *
 */
@Service
public class SystemService {

	@Autowired
	private AdminService adminService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CouponRepository couponRepository;
	@Autowired
	private LogRepository logRepository;

	/**
	 * 
	 * The function performs a connection according to the received arguments. If
	 * the connection succeeds, the function returns CustomLogin with the logged-in
	 * user information. If no exception is thrown
	 * 
	 * @param name      must be string
	 * @param password  must be string
	 * @param loginType must to be from loginType
	 * @return CustomLogin with logged-in information
	 * @throws InvalidLoginException    If no matching user was found for arguments
	 *                                  received
	 * @throws ObjectNotExistsException
	 */
	public CustomLogin login(String name, String password, LoginType loginType)
			throws InvalidLoginException, ObjectNotExistsException {

		switch (loginType) {
			case ADMIN:
				if (adminService.performLogin(name, password)) {
					CustomLogin customLogin = new CustomLogin(LoginType.ADMIN, 1);
					return customLogin;
				} else {
					throw new InvalidLoginException();
				}
			case COMPANY:
				if (companyService.performLogin(name, password)) {
					Company company = companyService.getComapnyByName(name);
					return new CustomLogin(LoginType.COMPANY, company.getId());
				} else {
					throw new InvalidLoginException();
				}
			case CUSTOMER:
				if (customerService.performLogin(name, password)) {
					Customer customer = customerService.getCustomerByName(name);
					return new CustomLogin(LoginType.CUSTOMER, customer.getId());
				} else {
					throw new InvalidLoginException();
				}
			default:
				throw new InvalidLoginException();
		}
	}

	/**
	 * The function will be activated when the system is activated or every 24 hours
	 * in which the system operates. The function will get from the DB all expiring
	 * coupons and remove them from the DB.
	 */
	@Scheduled(fixedRateString = "${coupon.project.remove.daily.coupon.every.day}")
	@Transactional
	public void removeInvalidCoupon() {
		Collection<Coupon> allCoupons = couponRepository.findByEndDateBefore(new Date());
		for (Coupon coupon : allCoupons) {
			Log log = new Log(new Date(), "FROM  SYSTEM SERVER", "eclipse",
					"remove daily coupon remove the coupon " + coupon.getTitle(), true);
			logRepository.save(log);
			couponRepository.delete(coupon);
		}
	}
}
