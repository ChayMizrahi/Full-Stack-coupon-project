package com.chay.couponprojectspring.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chay.couponprojectspring.entities.Company;
import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.entities.Customer;
import com.chay.couponprojectspring.entities.Log;
import com.chay.couponprojectspring.exceptions.CouponSystemException;
import com.chay.couponprojectspring.exceptions.InvalidLoginException;
import com.chay.couponprojectspring.exceptions.ObjectAlreadyExistsException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.exceptions.UpdatingException;
import com.chay.couponprojectspring.repositories.CompanyRepository;
import com.chay.couponprojectspring.repositories.CouponRepository;
import com.chay.couponprojectspring.repositories.CustomerRepository;
import com.chay.couponprojectspring.repositories.LogRepository;

/**
 * The class implements all existing functions in the adminService
 * interface
 * 
 * @author Chay Mizrahi
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private CouponRepository couponRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private LogRepository logRepository;

	@Value("${coupon.project.admin.name}")
	private String adminName;
	@Value("${coupon.project.admin.password}")
	private String adminPassword;

	@Autowired
	public AdminServiceImpl() {
	}

	@Override
	public boolean performLogin(String name, String password) throws InvalidLoginException {
		if (adminName.equals(name) && adminPassword.equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Company insertCompany(Company company) throws ObjectAlreadyExistsException {
		company.setName(company.getName().trim());
		if (companyRepository.findByName(company.getName()) == null) {
			companyRepository.save(company);
			return company;
		} else {
			throw new ObjectAlreadyExistsException(
					"Company with the name " + company.getName() + " already exists. Plaease try other name.");
		}
	}

	@Transactional
	@Override
	public void removeCompany(int companyId) throws ObjectNotExistsException {
		if (companyRepository.findById(companyId).isPresent()) {
			this.companyRepository.deleteCompanyById(companyId);
		} else {
			throw new ObjectNotExistsException("Company", companyId);
		}
	}

	@Override
	public Company updateCompany(Company company) throws ObjectNotExistsException, UpdatingException {
		if (companyRepository.findById(company.getId()).isPresent()) {
			Company origenCompany = companyRepository.findById(company.getId()).get();
			if (origenCompany.getName().equals(company.getName())) {
				companyRepository.save(company);
				return company;
			} else {
				throw new UpdatingException("Company", "Company name can not be changed", company.getId());
			}
		} else {
			throw new ObjectNotExistsException("Company", company.getId());
		}
	}

	@Override
	public Company getCompanyById(int companyId) throws ObjectNotExistsException {
		if (companyRepository.findById(companyId).isPresent()) {
			return companyRepository.findById(companyId).get();
		} else {
			throw new ObjectNotExistsException("Company", companyId);
		}
	}

	@Override
	public Collection<Company> getAllComapnies() {
		return companyRepository.findAll();
	}

	@Override
	public List<Coupon> getCompanyCoupons(int companyId) throws ObjectNotExistsException {
		if (companyRepository.findById(companyId).isPresent()) {
			return couponRepository.findByCompanyId(companyId);
		} else {
			throw new ObjectNotExistsException("Company", companyId);
		}
	}

	@Override
	public Customer insertCustomer(Customer customer) throws ObjectAlreadyExistsException {
		customer.setName(customer.getName().trim());
		if (customerRepository.findByName(customer.getName()) == null) {
			customerRepository.save(customer);
			return customer;
		} else {
			throw new ObjectAlreadyExistsException(
					"Customer with the name " + customer.getName() + " already exists. Plaease try other name.");
		}
	}

	@Override
	public void removeCustomer(int customerId) throws ObjectNotExistsException {
		if (customerRepository.findById(customerId).isPresent()) {
			customerRepository.delete(customerRepository.findById(customerId).get());
		} else {
			throw new ObjectNotExistsException("Customer", customerId);
		}
	}

	@Transactional
	@Override
	public Customer updateCustomer(Customer customer) throws CouponSystemException {
		if (customerRepository.findById(customer.getId()).isPresent()) {
			Customer origanCustomer = customerRepository.findById(customer.getId()).get();
			if (!origanCustomer.getName().equals(customer.getName())) {
				throw new UpdatingException("Customer", "Customer name can not be changed", customer.getId());
			} else {
				customerRepository.save(customer);
				return customer;
			}
		}
		throw new ObjectNotExistsException("Customer", customer.getId());
	}

	@Override
	public Customer getCustomerById(int customerId) throws ObjectNotExistsException {
		if (customerRepository.findById(customerId).isPresent()) {
			return customerRepository.findById(customerId).get();
		} else {
			throw new ObjectNotExistsException("Customer", customerId);
		}
	}

	@Override
	public Collection<Customer> getAllCustomer()  {
		return customerRepository.findAll();
	}

	@Override
	public List<Coupon> getCustomerCoupons(int customerId) throws ObjectNotExistsException {
		if (customerRepository.findById(customerId).isPresent()) {
			return customerRepository.findById(customerId).get().getCoupons();
		} else {
			throw new ObjectNotExistsException("Customer", customerId);
		}
	}

	@Override
	public Collection<Log> getAllLogs() {
		return logRepository.findAll();
	}

	@Override
	public Collection<Log> getLosBetweenDate(Date startDate, Date endDate) {
		return logRepository.findFrist20ByDateBetween(startDate, endDate);
	}

	@Override
	public void removeLog(int logId) throws ObjectNotExistsException {
		if (logRepository.findById(logId).isPresent()) {
			logRepository.deleteById(logId);
		} else {
			throw new ObjectNotExistsException("Log", logId);
		}
	}

	@Override
	public boolean checkIfCompanyNameAlreadyExists(String companyName) {
		if (companyRepository.findByName(companyName.trim()) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkIfCustomerNameAlreadyExists(String customerName) {
		if (customerRepository.findByName(customerName.trim()) != null) {
			return true;
		} else {
			return false;
		}
	}

}
