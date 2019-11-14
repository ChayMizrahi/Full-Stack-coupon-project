package com.chay.couponprojectspring.rest;

import java.util.Collection;

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

import com.chay.couponprojectspring.entities.Company;
import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.entities.CustomLogin;
import com.chay.couponprojectspring.exceptions.CouponBelongToAnotherCompanyException;
import com.chay.couponprojectspring.exceptions.CouponSystemException;
import com.chay.couponprojectspring.exceptions.ObjectAlreadyExistsException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.exceptions.UpdatingException;
import com.chay.couponprojectspring.services.CompanyServiceImpl;

/**
 * The class transforms all the functions associated with the company actions to
 * http requests.
 * 
 * @author Chay Mizrahi
 *
 */
@RestController
@RequestMapping("couponProject/company")
public class CompanyRest {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CompanyServiceImpl companyServiceImpl;

	@Value("${coupon.project.session.attribute.name}")
	private String loggedDetails;

	/**
	 * 
	 * The function returns the specific customLogin that is currently connected to
	 * the session and lets you know who the company that triggered the functions in
	 * the class.
	 * 
	 * @return CustomLogin
	 */
	private CustomLogin getService() {
		HttpSession session = request.getSession(false);
		CustomLogin customLogin = (CustomLogin) session.getAttribute(loggedDetails);
		return customLogin;

	}

	/**
	 * The function accepts Coupon as an argument, defines that the company obtained
	 * from the getService function owns the coupon and inserts it into DB.
	 * 
	 * @param coupon JSON in the request body
	 * @return JSON of the final object inserted into DB
	 * @throws ObjectAlreadyExistsException If there is already a coupon with the
	 *                                      titles like that of the new coupon
	 * @throws ObjectNotExistsException     If DB does not have the company defined
	 *                                      as the owner of the coupon.
	 * @path http://localhost:8080/couponProject/company/coupon
	 * @method POST
	 * @example of what to send in the requset body: { "title": "example",
	 *          "startDate": "2019-06-09T00:00:00.000+0000", "endDate":
	 *          "2019-08-06T00:00:00.000+0000", "amount": 100, "category": "FOOD",
	 *          "message": "message", "price": 1, "image": "https://picture.png" }
	 */
	@RequestMapping(path = "coupon", method = RequestMethod.POST)
	public Coupon insertCoupon(@RequestBody Coupon coupon)
			throws ObjectAlreadyExistsException, ObjectNotExistsException {
		CustomLogin customLogin = getService();
		companyServiceImpl.insertCoupon(coupon, customLogin.getTypeId());
		return coupon;
	}

	/**
	 * The session receives an id of a coupon, checks whether it is owned by the
	 * company that is returned in customLogin, and if so, the coupon is removed
	 * from the DB.
	 * 
	 * @param id      Through the path as path variable
	 * @param request
	 * @throws ObjectNotExistsException              If there is no coupon in the DB
	 *                                               with the ID obtained in the
	 *                                               argument
	 * @throws CouponBelongToAnotherCompanyException If the coupon does not belong
	 *                                               to the company received from
	 *                                               the session
	 * @path http://localhost:8080/couponProject/company/coupon/{id}
	 * @method DELETE
	 * @example http://localhost:8080/couponProject/company/coupon/8
	 */
	@RequestMapping(path = "coupon/{id}", method = RequestMethod.DELETE)
	public void removeCoupon(@PathVariable int id, HttpServletRequest request)
			throws ObjectNotExistsException, CouponBelongToAnotherCompanyException {
		companyServiceImpl.removeCoupon(id, getService().getTypeId());
	}

	/**
	 * Coupon function receives as an argument already exists in the DB with updated
	 * information. And updates it in DB
	 * 
	 * @param coupon  JSON in the request body
	 * @param id      Through the path as path variable
	 * @param request JSON in the request body
	 * @return The updated coupon after the changes, as JSON
	 * @throws CouponSystemException Can appear for several reasons: 1. If there was
	 *                               an attempt to change the coupon title. 2. If
	 *                               the ID received in the path does not match the
	 *                               one received in the body of an application 3.
	 *                               If the coupon does not belong to the company
	 *                               received from the session
	 * @path http://localhost:8080/couponProject/company/coupon/{id}
	 * @method PUT
	 * @example path : http://localhost:8080/couponProject/company/coupon/30 , Body
	 *          request : { "id": 30, "title": "example12", "startDate":
	 *          "2019-06-09T00:00:00.000+0000", "endDate":
	 *          "2019-08-06T00:00:00.000+0000", "amount": 100, "category": "FOOD",
	 *          "message": "message for example", "price": 1, "image":
	 *          "https://picture.png" }
	 */
	@RequestMapping(path = "coupon/{id}", method = RequestMethod.PUT)
	public Coupon updateCoupon(@RequestBody Coupon coupon, @PathVariable int id, HttpServletRequest request)
			throws CouponSystemException {
		if (id == coupon.getId()) {
			companyServiceImpl.updateCoupon(coupon, getService().getTypeId());
			return coupon;
		} else {
			throw new UpdatingException("Coupon", "The id of the coupon and the id in the path incorrect",
					coupon.getId());
		}
	}

	/**
	 * The function returns all the coupons that belong to the company that was
	 * received from the session
	 * 
	 * @param request
	 * @return JSON of all company coupons
	 * @throws ObjectNotExistsException If DB does not have the company defined as
	 *                                  the owner of the coupon.
	 * @path http://localhost:8080/couponProject/company/coupon
	 * @method GET
	 * 
	 */
	@RequestMapping(path = "coupon", method = RequestMethod.GET)
	public Collection<Coupon> getCompanyCoupon(HttpServletRequest request) throws ObjectNotExistsException {
		return companyServiceImpl.getCompanyCoupons(getService().getTypeId());
	}

	/**
	 * The function returns the details about the company that connects to the session
	 * @param request
	 * @return json of company
	 * @throws ObjectNotExistsException  If DB does not have the company defined as
	 *                                  the owner of the coupon.
	 * @path http://localhost:8080/couponProject/company
	 * @method GET
	 */
	@GetMapping
	public Company getThisCompany(HttpServletRequest request) throws ObjectNotExistsException {
		return companyServiceImpl.getCompany(getService().getTypeId());
	}

	/**
	 * The function accepts a title as an argument and returns true if there is already a coupon with a title like this or false if there is no.
On the client side: it will be possible to check if there is a coupon if the same title before adding a new coupon and thus prevent the server from returning an error.
	 * @param title Through the path as a parameter
	 * @return True or false as text plain
	 * @pathhttp://localhost:8080/couponProject/company/validation/couponTitle?title=title
	 * @method GET
	 * @example http://localhost:8080/couponProject/company/validation/couponTitle?title=Weekend in Amsterdam
	 */
	@RequestMapping(path = "validation/couponTitle", method = RequestMethod.GET)
	public boolean checkIfCouponTitleAlreadyExists(@RequestParam String title) {
		return companyServiceImpl.checkIfCouponTitleAlreadyExists(title);
	}

}
