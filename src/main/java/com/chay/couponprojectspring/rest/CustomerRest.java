package com.chay.couponprojectspring.rest;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.entities.CouponCaregory;
import com.chay.couponprojectspring.entities.CustomLogin;
import com.chay.couponprojectspring.entities.Customer;
import com.chay.couponprojectspring.exceptions.CouponAlreadyPurchasedException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.exceptions.ZeroCouponAmountException;
import com.chay.couponprojectspring.services.CustomerServiceImpl;

/**
 * The class transforms all the functions associated with the customer actions
 * to http requests.
 * 
 * @author Chay Mizrahi
 *
 */
@RestController
@RequestMapping("couponProject/customer")
public class CustomerRest {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	@Value("${coupon.project.session.attribute.name}")
	private String loggedDetails;
	@Value("${coupon.project.session.attribute.cart}")
	private String cart;

	/**
	 * The function returns the specific customLogin that is currently connected to
	 * the session and lets you know who the customer that triggered the functions
	 * in the class.
	 * 
	 * @return CustomLogin
	 */
	private CustomLogin getService() {
		HttpSession session = request.getSession(false);
		CustomLogin customLogin = (CustomLogin) session.getAttribute(loggedDetails);
		return customLogin;
	}

	/**
	 * The function received a coupon id as an argument and a purchase order by the
	 * customer received from the session.
	 * 
	 * @param id      Through the path as path variable
	 * @param request
	 * @throws CouponAlreadyPurchasedException If the customer has already purchased
	 *                                         the coupon
	 * @throws ObjectNotExistsException        If the coupon does not exist in DB
	 * @throws ZeroCouponAmountException       If the coupon amount is equal to 0
	 * @path http://localhost:8080/couponProject/customer/purchase/{id}
	 * @method PUT
	 * @example http://localhost:8080/couponProject/customer/purchase/8
	 */
	@RequestMapping(path = "purchase/{id}", method = RequestMethod.PUT)
	public void purchaseCoupon(@PathVariable int id, HttpServletRequest request)
			throws CouponAlreadyPurchasedException, ObjectNotExistsException, ZeroCouponAmountException {
		customerServiceImpl.purchaseCoupon(id, getService().getTypeId());
	}

	/**
	 * The function returns all existing coupons in DB
	 * 
	 * @return JSOM of coupons
	 * @path http://localhost:8080/couponProject/customer/coupon
	 * @method GET
	 */
	@RequestMapping(path = "coupon", method = RequestMethod.GET)
	public Collection<Coupon> getAllCoupons() {
		return customerServiceImpl.getAllCoupons();
	}

	/**
	 * The function returns a specific coupon according to the id received in the
	 * argument
	 * 
	 * @param id      Through the path as path variable
	 * @param request
	 * @return JSON of coupon
	 * @throws ObjectNotExistsException If the coupon does not exist in DB
	 * @path http://localhost:8080/couponProject/customer/coupon/{id}
	 * @method GET
	 * @example http://localhost:8080/couponProject/customer/coupon/9
	 */
	@RequestMapping(path = "coupon/{id}", method = RequestMethod.GET)
	public Coupon getCouponById(@PathVariable int id, HttpServletRequest request) throws ObjectNotExistsException {
		return customerServiceImpl.getCouponById(id);
	}

	/**
	 * The function returns all the coupons the customer has already purchased.
	 * 
	 * @param request
	 * @return JSON of coupons
	 * @throws ObjectNotExistsException If no DB exists on the customer that was
	 *                                  searched.
	 * @path http://localhost:8080/couponProject/customer/customerCoupons
	 * @method GET
	 */
	@RequestMapping(path = "customerCoupons", method = RequestMethod.GET)
	public Collection<Coupon> getAllCustomerCoupons(HttpServletRequest request) throws ObjectNotExistsException {
		return customerServiceImpl.getAllCustomerCoupons(getService().getTypeId());
	}

	/**
	 * The function returns the entire coupon that the customer has not purchased.
	 * 
	 * @param request
	 * @return JSON of coupons
	 * @throws ObjectNotExistsExceptionIf no DB exists on the customer that was
	 *                                    searched.
	 * @path http://localhost:8080/couponProject/customer/couponNotPurchased
	 * @method GET
	 */
	@RequestMapping(path = "couponNotPurchased", method = RequestMethod.GET)
	public Collection<Coupon> getCouponNotPurchased(HttpServletRequest request) throws ObjectNotExistsException {
		return customerServiceImpl.getCouponNotPurchased(getService().getTypeId());
	}

	/**
	 * The function returns the customer received what session
	 * 
	 * @param request
	 * @return JSON of customer
	 * @throws ObjectNotExistsException If no DB exists on the customer that was
	 *                                  searched.
	 * @path http://localhost:8080/couponProject/customer
	 * @method GET
	 */
	@GetMapping
	public Customer getCustomer(HttpServletRequest request) throws ObjectNotExistsException {
		return customerServiceImpl.getCustomer(getService().getTypeId());
	}

	/**
	 * The function returns all nested coupons to the received category in the path
	 * 
	 * @param category Through the path as path variable
	 * @param request
	 * @return JSON of coupons
	 * @path http://localhost:8080/couponProject/customer/couponByCategory/{category}
	 * @method GET
	 * @example http://localhost:8080/couponProject/customer/couponByCategory/TRAVELING
	 */
	@RequestMapping(path = "couponByCategory/{category}", method = RequestMethod.GET)
	public Collection<Coupon> getCouponByCategory(@PathVariable CouponCaregory category, HttpServletRequest request) {
		return customerServiceImpl.getCouponByCategory(category);
	}

	/**
	 * The function returns all coupons whose price is lower than that obtained as
	 * an argument
	 * 
	 * @param price   Through the path as path variable
	 * @param request
	 * @return JSON of coupons
	 * @path http://localhost:8080/couponProject/customer/couponByPrice/{price}
	 * @method GET
	 * @example http://localhost:8080/couponProject/customer/couponByPrice/200
	 */
	@RequestMapping(path = "couponByPrice/{price}", method = RequestMethod.GET)
	public Collection<Coupon> getCouponLowerThanPrice(@PathVariable double price, HttpServletRequest request) {
		return customerServiceImpl.getCouponLowerThanPrice(price);
	}

	/**
	 * The function returns true if the customer has already purchased the coupon
	 * received, or false if not.
	 * 
	 * @param couponId Through the path as a parameter
	 * @return text plain
	 * @path http://localhost:8080/couponProject/customer/validation/ifPurchased?couponId=?
	 * @method GET
	 * @example http://localhost:8080/couponProject/customer/validation/ifPurchased?couponId=10
	 */
	@RequestMapping(path = "validation/ifPurchased", method = RequestMethod.GET)
	public boolean checkIfCoupon(@RequestParam int couponId) {
		return customerServiceImpl.checkIfCustomerAlraedyPurchasedCoupon(getService().getTypeId(), couponId);
	}

	/**
	 * The function checks whether the attribute cart session exists and adds the
	 * resulting coupon as an argument. If the attribute does not exist, it will
	 * create it.
	 * 
	 * @param coupon  Through the request body as JSON
	 * @param request
	 * @return text plain if the adding success
	 * @path http://localhost:8080/couponProject/customer/cart
	 * @method POST
	 * @example The request body : { "id": 9, "title": " tent for 4 people ",
	 *          "startDate": "2026-02-02T00:00:00.000+0000", "endDate":
	 *          "2027-02-02T00:00:00.000+0000", "amount": 997, "category":
	 *          "CAMPING", "message": "A tent for 4 people of Holland-camp company",
	 *          "price": 3, "image":
	 *          "https://d3m9l0v76dty0.cloudfront.net/system/photos/1936543/large/a4a57cdecd0e943e97fef393751ae0aa.jpg"
	 *          }
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "cart", method = RequestMethod.POST)
	public boolean addCouponCart(@RequestBody Coupon coupon, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Set<Coupon> cartSet = null;
		if (session.getAttribute(cart) != null) {
			cartSet = (Set<Coupon>) session.getAttribute(cart);
		} else {
			cartSet = new HashSet<>();
		}
		for (Coupon c : cartSet) {
			if (c.getId() == coupon.getId()) {
				return false;
			}
		}
		cartSet.add(coupon);
		session.setAttribute(cart, cartSet);
		return true;
	}

	/**
	 * Returns all existing coupons in the attribute cart
	 * 
	 * @param request
	 * @return JSON of coupons
	 * @path http://localhost:8080/couponProject/customer/cart
	 * @method GET
	 */
	@RequestMapping(path = "cart", method = RequestMethod.GET)
	public Collection<Coupon> getCouponsCart(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute(cart) == null) {
			return Collections.emptySet();
		} else {
			@SuppressWarnings("unchecked")
			Set<Coupon> cartSet = (Set<Coupon>) session.getAttribute(cart);
			return cartSet;
		}
	}

	/**
	 * The function deletes the coupon cart that the ID has been received as an argument
	 * @param id      Through the path as path variable
	 * @param request
	 * @return text plain
	 * @path http://localhost:8080/couponProject/customer/cart/{id}
	 * @method DELETE
	 * @example http://localhost:8080/couponProject/customer/cart/9
	 */
	@RequestMapping(path = "cart/{id}", method = RequestMethod.DELETE)
	public boolean removeCouponCart(@PathVariable int id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute(cart) != null) {
			@SuppressWarnings("unchecked")
			Set<Coupon> cartSet = (Set<Coupon>) session.getAttribute(cart);
			for (Coupon c : cartSet) {
				if (c.getId() == id) {
					cartSet.remove(c);
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

}
