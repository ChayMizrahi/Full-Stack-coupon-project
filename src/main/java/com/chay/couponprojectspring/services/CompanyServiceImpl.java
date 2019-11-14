package com.chay.couponprojectspring.services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chay.couponprojectspring.entities.Company;
import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.exceptions.CouponBelongToAnotherCompanyException;
import com.chay.couponprojectspring.exceptions.CouponSystemException;
import com.chay.couponprojectspring.exceptions.ObjectAlreadyExistsException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.exceptions.UpdatingException;
import com.chay.couponprojectspring.repositories.CompanyRepository;
import com.chay.couponprojectspring.repositories.CouponRepository;

/**
 * The class implements all existing functions in the comanyService interface
 * 
 * @author Chay Mizrahi
 *
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CouponRepository couponRepository;

	public CompanyServiceImpl() {
	}

	public boolean performLogin(String name, String password) {
		Company company = companyRepository.findByNameAndPassword(name, password);
		if (company == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	@Transactional
	public Coupon insertCoupon(Coupon coupon, int companyId)
			throws ObjectAlreadyExistsException, ObjectNotExistsException {
		if (companyRepository.findById(companyId).isPresent()) {
			if (couponRepository.findByTitle(coupon.getTitle()) == null) {
				Company company = companyRepository.findById(companyId).get();
				coupon.setCompany(company);
				couponRepository.save(coupon);
				return coupon;
			} else {
				throw new ObjectAlreadyExistsException("There is already coupon with the title " + coupon.getTitle());
			}
		} else {
			throw new ObjectNotExistsException("Company", companyId);
		}
	}

	@Override
	public void removeCoupon(int couponId, int companyId)
			throws ObjectNotExistsException, CouponBelongToAnotherCompanyException {
		if (companyRepository.findById(companyId).isPresent() && couponRepository.findById(couponId).isPresent()) {
			Coupon coupon = couponRepository.getCouponCompany(couponId, companyId);
			if (coupon == null) {
				throw new CouponBelongToAnotherCompanyException();
			} else {
				couponRepository.delete(coupon);
			}
		} else {
			throw new ObjectNotExistsException("Company", companyId);
		}
	}

	@Override
	public Coupon updateCoupon(Coupon coupon, int companyId) throws CouponSystemException {

		if ((companyRepository.findById(companyId).isPresent())&& (couponRepository.findById(coupon.getId()).isPresent())) {
			if (couponRepository.findById(coupon.getId()).get().getTitle().toString().equals( coupon.getTitle().toString())) {
				if (couponRepository.getCouponCompany(coupon.getId(), companyId) != null) {
					System.out.println(couponRepository.getCouponCompany(coupon.getId(), companyId));
					coupon.setCompany(companyRepository.findById(companyId).get());
					couponRepository.save(coupon);
					return coupon;
				} else {
					System.out.println(couponRepository.getCouponCompany(coupon.getId(), companyId));
					throw new CouponBelongToAnotherCompanyException();
				}
			} else {
				throw new UpdatingException("Coupon", "Unable to change coupon title", coupon.getId());
			}
		} else {
			throw new ObjectNotExistsException("Coupon", coupon.getId());
		}
	}

	@Override
	public Coupon getCoupon(int couponId, int companyId)
			throws ObjectNotExistsException, CouponBelongToAnotherCompanyException {
		if (couponRepository.findById(couponId).isPresent()) {
			Collection<Coupon> companyCoupons = couponRepository.findByCompanyId(companyId);
			for (Coupon coupon : companyCoupons) {
				if (coupon.getId() == couponId) {
					return coupon;
				}
			}
			throw new CouponBelongToAnotherCompanyException();
		}
		throw new ObjectNotExistsException("Coupon", couponId);
	}

	@Override
	public Company getCompany(int companyId) throws ObjectNotExistsException {
		if (!companyRepository.findById(companyId).isPresent()) {
			throw new ObjectNotExistsException("Company", companyId);
		} else {
			return companyRepository.findById(companyId).get();
		}
	}

	@Override
	public Collection<Coupon> getCompanyCoupons(int companyId) throws ObjectNotExistsException {

		return couponRepository.findByCompanyId(companyId);
	}

	@Override
	public Company getComapnyByName(String name) throws ObjectNotExistsException {
		Company company = companyRepository.findByName(name);
		if (company == null) {
			throw new ObjectNotExistsException("Company", -1);
		}
		return company;
	}

	@Override
	public boolean checkIfCouponTitleAlreadyExists(@NotNull String couponTitle) {
		
		if (couponRepository.findByTitle(couponTitle.toString().trim()) != null) {
			return true;
		} else {
			return false;
		}
	}

}
