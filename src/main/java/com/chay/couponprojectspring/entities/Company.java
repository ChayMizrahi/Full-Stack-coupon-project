package com.chay.couponprojectspring.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * The object will represent a company. The company will be able to create
 * coupons represented by the Coupon object
 * 
 * @author Chay Mizrahi
 *
 */
@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min = 2, max = 50, message = "Company name must contain at least 2 characters and a maximum of 50 characters")
	private String name;
	@Size(min = 4, max = 8, message = "Company password munt be minimum 4 charachters and maximuum of 8 chaeacters")
	private String password;
	@Email
	private String email;
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	@Valid
	private List<Coupon> coupons;

	public Company() {
		coupons = new ArrayList<>();
	}

	public Company(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void addCoupon(Coupon coupon) {
		this.coupons.add(coupon);
	}

	@Override
	public String toString() {
		return "Company: ID - " + id + ", name - " + name + ", password- " + password + ", email- " + email
				+ " coupons:" + getCoupons() + ".\n";
	}

}
