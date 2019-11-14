package com.chay.couponprojectspring.rest;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chay.couponprojectspring.entities.Company;
import com.chay.couponprojectspring.entities.Customer;
import com.chay.couponprojectspring.entities.Log;
import com.chay.couponprojectspring.exceptions.CouponSystemException;
import com.chay.couponprojectspring.exceptions.ObjectAlreadyExistsException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.exceptions.UpdatingException;
import com.chay.couponprojectspring.services.AdminServiceImpl;

/**
 * The class transforms all the functions associated with the system
 * administration actions to http requests.
 * 
 * @author Chay Mizrahi
 *
 */
@RestController
@RequestMapping("couponProject/admin")
public class AdminRest {

	@Autowired
	private AdminServiceImpl adminServiceImpl;

	/**
	 * 
	 * @param customer Through request body as application JSON.
	 * @param request
	 * @return The new object that has been assigned to the DB as JSON.
	 * @throws ObjectAlreadyExistsException If there is already a customer with the
	 *                                      same name.
	 * @method POST
	 * @path http://localhost:8080/couponProject/admin/customer
	 * @example Sending: { "name": "Moshe", "password": "1234" }
	 * 
	 */
	@RequestMapping(path = "customer", method = RequestMethod.POST)
	public Customer createCustomer(@RequestBody Customer customer, HttpServletRequest request)
			throws ObjectAlreadyExistsException {
		adminServiceImpl.insertCustomer(customer);
		return customer;
	}

	/**
	 * A function removes the customer from the DB whose ID has been accepted as an
	 * argument.
	 * 
	 * @param id      Through the path
	 * @param request
	 * @throws ObjectNotExistsException If there is no customer with the id that was
	 *                                  received
	 * 
	 * @method DELETE
	 * @path http://localhost:8080/couponProject/admin/customer/?
	 */
	@RequestMapping(path = "customer/{id}", method = RequestMethod.DELETE)
	public void removeCustomer(@PathVariable int id, HttpServletRequest request) throws ObjectNotExistsException {
		adminServiceImpl.removeCustomer(id);
	}

	/**
	 * 
	 * @param customer An existing customer, as JSON, through the request body.
	 * @param id       Through the path. The id of the customer we want to update
	 *                 through the path.
	 * @param request
	 * @return customer as a JSON with its updated details.
	 * @throws CouponSystemException A number of exceptions can be thrown: 1) If
	 *                               there was an attempt to change the name of the
	 *                               customer. 2) If there was an attempt to update
	 *                               a customer that does not exist. 3) If the id in
	 *                               the path and id in the body of the request do
	 *                               not match.
	 * @method PUT
	 * @path http://localhost:8080/couponProject/admin/customer/9
	 * @example In request body: { "id": 5, "name": "Keren", "password": "123465",
	 *          "coupons": [] }
	 */
	@RequestMapping(path = "customer/{id}", method = RequestMethod.PUT)
	public Customer updateCustomer(@RequestBody Customer customer, @PathVariable int id, HttpServletRequest request)
			throws CouponSystemException {
		if (customer.getId() == id) {
			return adminServiceImpl.updateCustomer(customer);
		} else {
			throw new UpdatingException("Customer", "The custumer id and the id in the path incorrect",
					customer.getId());
		}
	}

	/**
	 * The function returns all the existing customers in DB.
	 * 
	 * @param request
	 * @return JSON of customer
	 * @method GET
	 * @path http://localhost:8080/couponProject/admin/customer
	 */
	@RequestMapping(path = "customer", method = RequestMethod.GET)
	public Collection<Customer> getAllCustomers(HttpServletRequest request) {
		return adminServiceImpl.getAllCustomer();
	}

	/**
	 * The function returns the customer whose ID was received as an argument.
	 * 
	 * @param id      Through the path.
	 * @param request
	 * @return customer as JSON
	 * @throws ObjectNotExistsException if not exists customer with this id.
	 * @method GET
	 * @path http://localhost:8080/couponProject/admin/customer/?
	 */
	@RequestMapping(path = "customer/{id}", method = RequestMethod.GET)
	public Customer getCustomerById(@PathVariable int id, HttpServletRequest request) throws ObjectNotExistsException {
		return adminServiceImpl.getCustomerById(id);
	}

	/**
	 * The function accepts a company and inserts it into a DB.
	 * 
	 * @param company Through the body request in JSON format
	 * @param request
	 * @return JSON of the object inserted into the DB
	 * @throws ObjectAlreadyExistsException If there is already a company with a
	 *                                      similar name
	 * @path http://localhost:8080/couponProject/admin/company
	 * @method POST
	 * @example { "name": "Oshem", "email": "choupon@gmail.com", "password": "1234"
	 *          }
	 */
	@RequestMapping(path = "company", method = RequestMethod.POST)
	public Company createCompany(@RequestBody Company company, HttpServletRequest request)
			throws ObjectAlreadyExistsException {
		adminServiceImpl.insertCompany(company);
		return company;
	}

	/**
	 * A function deletes the object that the id receives as an argument
	 * 
	 * @param id Through the path as path variable
	 * @throws ObjectNotExistsException If there is an object with the Id of
	 *                                  received
	 * @path http://localhost:8080/couponProject/admin/company/{id}
	 * @method DELETE
	 * @example http://localhost:8080/couponProject/admin/company/12
	 */
	@RequestMapping(path = "company/{id}", method = RequestMethod.DELETE)
	public void removeCompany(@PathVariable int id) throws ObjectNotExistsException {
		adminServiceImpl.removeCompany(id);
	}

	/**
	 * The session receives an existing company with updated details and updates it
	 * in DB
	 * 
	 * @param company Through request body as JSON, must be present in DB
	 * @param id      Through the path as path variable, must match the object
	 *                entered through the request body
	 * @param request
	 * @return JSON of the object after the update
	 * @throws ObjectNotExistsException If no object exists with the same ID as the
	 *                                  one obtained in the argument
	 * @throws UpdatingException        If the new object name is different from the
	 *                                  original object name
	 * @path http://localhost:8080/couponProject/admin/company/{id}
	 * @method PUT
	 * @example path: http://localhost:8080/couponProject/admin/company/11 request
	 *          body: { "id": 11, "name": "coupon", "password": "1239894", "email":
	 *          "choupon@gmail.com", "coupons": [] }
	 */
	@RequestMapping(path = "company/{id}", method = RequestMethod.PUT)
	public Company updateCompany(@RequestBody Company company, @PathVariable int id, HttpServletRequest request)
			throws ObjectNotExistsException, UpdatingException {
		if (company.getId() == id) {
			return adminServiceImpl.updateCompany(company);
		} else {
			throw new UpdatingException("Company", "The company id and the id in the path incorrect", company.getId());
		}
	}

	/**
	 * The function returns all the existing companies in DB
	 * 
	 * @param request
	 * @return JSON of all companies
	 * @path http://localhost:8080/couponProject/admin/company
	 * @method POST
	 */
	@RequestMapping(path = "company", method = RequestMethod.GET)
	public Collection<Company> getAllCompanies(HttpServletRequest request) {
		return adminServiceImpl.getAllComapnies();
	}

	/**
	 * The function returns the id that her company received as an argument.
	 * 
	 * @param id      Through the path path variable
	 * @param request
	 * @return JSON of the company with the id received
	 * @throws ObjectNotExistsException If there is no company with the ID received
	 * @path http://localhost:8080/couponProject/admin/company/{id}
	 * @method POST
	 * @example http://localhost:8080/couponProject/admin/company/3
	 */
	@RequestMapping(path = "company/{id}", method = RequestMethod.GET)
	public Company getCompanyById(@PathVariable int id, HttpServletRequest request) throws ObjectNotExistsException {
		return adminServiceImpl.getCompanyById(id);
	}

	/**
	 * The function returns all existing logs in DB
	 * 
	 * @param request
	 * @return JSON of all logs in the DB.
	 * @path http://localhost:8080/couponProject/admin/log
	 * @method POST
	 */
	@RequestMapping(path = "log", method = RequestMethod.GET)
	public Collection<Log> getAllLog(HttpServletRequest request) {
		return adminServiceImpl.getAllLogs();
	}

	/**
	 * The function returns all logs between the received dates as arguments.
	 * 
	 * @param startDate Through the path as Request Param
	 * @param endDate   Through the path as Request Param
	 * @param request
	 * @return JSON of all logs
	 * @pathhttp://localhost:8080/couponProject/admin/log/byDates?startDate=?&endDate=?
	 * @method POST
	 * @example http://localhost:8080/couponProject/admin/log/byDates?startDate=2019/06/17&endDate=2019/06/17
	 */
	@RequestMapping(path = "log/byDates", method = RequestMethod.GET)
	public Collection<Log> getLogsBetweenDate(@RequestParam Date startDate, @RequestParam Date endDate,
			HttpServletRequest request) {
		return adminServiceImpl.getLosBetweenDate(startDate, endDate);
	}

	/**
	 * The function deletes the log that the ID was received as an argument from the
	 * DB.
	 * 
	 * @param id      Through the path as path variable
	 * @param request
	 * @throws ObjectNotExistsException
	 * @path http://localhost:8080/couponProject/admin/log/{id}
	 * @method DELETE
	 * @example http://localhost:8080/couponProject/admin/log/3
	 */
	@RequestMapping(path = "log/{id}", method = RequestMethod.DELETE)
	public void removeLog(@PathVariable int id, HttpServletRequest request) throws ObjectNotExistsException {
		adminServiceImpl.removeLog(id);
	}

	/**
	 * The function receives a name as an argument and checks whether there is a
	 * customer with the same name in DB.
	 * 
	 * @param name Through the path as Request Param
	 * @return True or false as plain text
	 * @path http://localhost:8080/couponProject/admin/validation/companyName?name=?
	 * @method GET
	 * @example http://localhost:8080/couponProject/admin/validation/companyName?name=intellll
	 */
	@RequestMapping(path = "validation/companyName", method = RequestMethod.GET)
	public boolean checkIfCompanyNameAlreadyExists(@RequestParam String name) {
		return adminServiceImpl.checkIfCompanyNameAlreadyExists(name);
	}

	/**
	 *
	 * The function accepts a name as an argument and checks to see if there is a
	 * company with the same name in the DB.
	 * 
	 * @param name Through the path as Request Param
	 * @return True or false as plain text
	 * @path http://localhost:8080/couponProject/admin/validation/customerName?name=?
	 * @method GET
	 * @example http://localhost:8080/couponProject/admin/validation/customerName?name=keren
	 */
	@RequestMapping(path = "validation/customerName", method = RequestMethod.GET)
	public boolean checkIfCustomerNameAlreadyExists(@RequestParam String name) {
		return adminServiceImpl.checkIfCustomerNameAlreadyExists(name);
	}
}
