package com.chay.couponprojectspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chay.couponprojectspring.entities.Company;

/**
 * The interface inherits from JpaRepository and contains the functions
 * necessary to manage the Company object in the database. The functions will be
 * exercised by Spring behind the scenes.
 * 
 * @author Chay Mizrahi
 *
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company findByNameAndPassword(String name, String password);

	void deleteCompanyById(int companyId);

	Company findByName(String name);

}
