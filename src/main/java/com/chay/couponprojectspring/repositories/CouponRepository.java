package com.chay.couponprojectspring.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.entities.CouponCaregory;

/**
 * The interface inherits from JpaRepository and contains the functions
 * necessary to manage the Coupon object in the database. The functions will be
 * exercised by Spring behind the scenes.
 * 
 * @author Chay Mizrahi
 *
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	Coupon findByTitle(String title);

	List<Coupon> findByCompanyId(int companyId);

	@Query("select c from Coupon c where c.id = ?1  AND  c.company.id = ?2")
	Coupon getCouponCompany(int couponId, int companyId);

	Collection<Coupon> findByCategory(CouponCaregory category);

	Collection<Coupon> findByPriceLessThan(double price);

	Collection<Coupon> findByEndDateBefore(Date date);

	@Query("SELECT DISTINCT c FROM Coupon c INNER JOIN c.customers t where t.id = ?1")
	Collection<Coupon> couponsCustomerByCustomerId(int customerId);
	
	@Query("SELECT DISTINCT c FROM Coupon c INNER JOIN c.customers t where t.id = ?1 and c.id = ?2")
	Coupon couponByCustomerIdAndCouponId(int customerId, int couponId);
	
	@Query("SELECT DISTINCT c FROM Coupon c INNER JOIN c.customers t where t.id <> ?1")
	Collection<Coupon> getCouponCustomerNotPurchased(int customerId);
	
	
}
