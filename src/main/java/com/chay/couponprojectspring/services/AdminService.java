package com.chay.couponprojectspring.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.chay.couponprojectspring.entities.Company;
import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.entities.Customer;
import com.chay.couponprojectspring.entities.Log;
import com.chay.couponprojectspring.exceptions.CouponSystemException;
import com.chay.couponprojectspring.exceptions.InvalidLoginException;
import com.chay.couponprojectspring.exceptions.ObjectAlreadyExistsException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.exceptions.UpdatingException;

/**
 * The interface contains all functions that will eventually be able to run the
 * system administrator
 * 
 * @author Chay Mizrahi
 *
 */
public interface AdminService {

	/**
	 * The function receives a name and password and checks whether they match the
	 * name and password set in the system. They are compatible is the instance
	 * returns of the class.
	 * 
	 * @param userName name's admin.
	 * @param password password's admin.
	 * @return
	 * @throws InvalidLoginException if the name and the password incompatible.
	 */
	boolean performLogin(@NotNull String name, @NotNull String password) throws InvalidLoginException;

	/**
	 * The function Saves a given Company in the DB. Before adding, we check whether
	 * there is already a company with the name of the new company if there is the
	 * exception ObjectAlreadyExistsException will be throw.
	 * 
	 * @param company the specific company that will inserted.
	 * @throws ObjectAlreadyExistsException if already exists company with the same
	 *                                      name like the new company.
	 */
	Company insertCompany(@Valid Company company) throws ObjectAlreadyExistsException;

	/**
	 * The function deletes a given company from the DB. Before the updating it
	 * checks if the company exists and if is so the function deletes all the
	 * coupons of the company and then the company.
	 * 
	 * @param companyId the id of the company will be removed.
	 * @throws ObjectNotExistsException if the company not exists.
	 */
	void removeCompany(@Positive int companyId) throws ObjectNotExistsException;

	/**
	 * The function receives the company with new data and update the detail of the
	 * existing company. Before the updating, several tests are performed: 1. If the
	 * company exists. 2. If the name of the company has change.
	 * 
	 * @param company
	 * @return
	 * @throws ObjectNotExistsException if the company not exists.
	 * @throws UpdatingException        if the client try to update the name of the
	 *                                  company.
	 */
	Company updateCompany(@Valid Company company) throws ObjectNotExistsException, UpdatingException;

	/**
	 * The function receives specific id and returns the company with this id. It
	 * gets the company and the coupon of the company and set and the coupons.
	 * 
	 * @param companyId the id of the company.
	 * @return the company with the specific id.
	 * @throws ObjectNotExistsException if the company not exists.
	 */
	Company getCompanyById(@Positive int companyId) throws ObjectNotExistsException;

	/**
	 * The function returns all the companies that listed in the DB
	 * 
	 * @return collection of all the companies.
	 * @throws ObjectNotExistsException if the company not exists.
	 */
	Collection<Company> getAllComapnies();

	/**
	 * The function receives specific company id and return all the coupons of the
	 * specific company.
	 * 
	 * @param companyId - the id of the company.
	 * @return all the coupon of the specific company.
	 * @throws ObjectNotExistsException if the company not exists.
	 */
	List<Coupon> getCompanyCoupons(@Positive int companyId) throws ObjectNotExistsException;

	/**
	 * The function Saves a given Customer in the DB. Before adding, we check
	 * whether there is already a customer with the name of the new customer if
	 * there is the exception ObjectAlreadyExistsException will be throw.
	 * 
	 * @param customer the specific company that will inserted.
	 * @throws ObjectAlreadyExistsException if already exists customer with the same
	 *                                      name like the new customer.
	 * @throws GoogelMapsException
	 */
	Customer insertCustomer(@Valid Customer customer) throws ObjectAlreadyExistsException;

	/**
	 * The function deletes a given customer from the DB. Before the deleting it
	 * checks if the customer exists and then delete the customer.
	 * 
	 * @param customerId the id of the customer will be removed.
	 * @throws ObjectNotExistsException if the customer not exists.
	 */
	void removeCustomer(@Positive int customerId) throws ObjectNotExistsException;

	/**
	 * The function receives the customer with new data and update the detail of the
	 * existing customer. Before the updating, several tests are performed: 1. If
	 * the customer exists. 2. If the name of the customer has change.
	 * 
	 * @param customer must contain id
	 * @return
	 * @throws ObjectNotExistsException if the customer not exists.
	 * @throws UpdatingException        if the client try to update the name of the
	 *                                  customer.
	 * @throws CouponSystemException
	 */
	Customer updateCustomer(@Valid Customer customer)
			throws ObjectNotExistsException, UpdatingException, CouponSystemException;

	/**
	 * The function receives specific id and returns the customer with this id.
	 * 
	 * @param customerId the id of the customer.
	 * @return the customer with the specific id.
	 * @throws ObjectNotExistsException if the customer not exists.
	 */
	Customer getCustomerById(@Positive int customerId) throws ObjectNotExistsException;

	/**
	 * The function returns all the customers that listed in the DB
	 * 
	 * @return collection of all the customers.
	 * 
	 */
	Collection<Customer> getAllCustomer();

	/**
	 * The function receives specific customer id and return all the coupons of the
	 * specific customer.
	 * 
	 * @param customerId - the id of the customer.
	 * @return all the coupon of the specific customer.
	 * @throws ObjectNotExistsException if the customer not exists.
	 */
	List<Coupon> getCustomerCoupons(@Positive int customerId) throws ObjectNotExistsException;

	/**
	 * The function return all the logs from the DB.
	 * 
	 * @return collection of logs
	 */
	Collection<Log> getAllLogs();

	/**
	 * The function return the 20 tops logs that his date between the dates received
	 * as arguments.
	 * 
	 * @param startDate in format (YYYY/MM/DD) and not null.
	 * @param endDate   in format (YYYY/MM/DD) and not null.
	 * @return collection of logs
	 */
	Collection<Log> getLosBetweenDate(@NotNull Date startDate, @NotNull Date endDate);

	/**
	 * The function removes from the DB the log with the id that was received as
	 * argument.
	 * 
	 * @param logId must be int.
	 * @throws ObjectNotExistsException if not exists log with the id that was
	 *                                  received as argument.
	 */
	void removeLog(@Positive int logId) throws ObjectNotExistsException;

	/**
	 * The function accepts a company name as an argument and returns the true if
	 * there is a company with the same name as the one received and a false if it
	 * does not exist.
	 * 
	 * @param companyName must be string.
	 * @return true if there is a company with the same name as the one received and
	 *         a false if it does not exist
	 */
	boolean checkIfCompanyNameAlreadyExists(@NotNull String companyName);

	/**
	 * The function accepts a customer name as an argument and returns the true if
	 * there is a customer with the same name as the one received and a false if it
	 * does not exist.
	 * 
	 * @param customerName must be string
	 * @return true if there is a customer with the same name as the one received
	 *         and a false if it does not exist
	 */
	boolean checkIfCustomerNameAlreadyExists(@NotNull String customerName);

}
