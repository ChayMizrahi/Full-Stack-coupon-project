package com.chay.couponprojectspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chay.couponprojectspring.entities.Customer;

/**
 * The interface inherits from JpaRepository and contains the functions
 * necessary to manage the Customer object in the database. The functions will be
 * exercised by Spring behind the scenes.
 * @author Chay Mizrahi
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	void deleteCustomerById(int customerId);

	Customer findByNameAndPassword(String name, String password);

	Customer findByName(String name);

}
