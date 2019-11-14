package com.chay.couponprojectspring.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * The object represented a customer. The customer will be able to buy coupons
 * that will be represented by the Coupon object
 * 
 * @author Chay Mizrahi
 *
 */
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min = 2, max = 50, message = "Custmer name must contain at least 5 characters and a maximum of 50 characters")
	private String name;
	@Size(min = 4, max = 8, message = "Customer password munt be minimum 5 charachters and maximuum of 8 chaeacters")
	private String password;
	@ManyToMany
	@Valid
	private List<Coupon> coupons;

	public Customer() {
		coupons = new ArrayList<>();
	}

	public Customer(String name, String password, String country, String city, String address) {
		this.name = name;
		this.password = password;
		this.coupons = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> set) {
		this.coupons = set;
	}

	public void addCoupon(Coupon coupon) {
		this.coupons.add(coupon);
	}

	@Override
	public String toString() {
		return "Customer: id=" + id + ", name=" + name + ", password=" + password + " coupons: ";
	}

}
