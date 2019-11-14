package com.chay.couponprojectspring.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * An object representing a coupon. The coupon will be managed by the Company
 * object and can be acquired by the Customer object
 * 
 * @author Chay Mizrahi
 *
 */
@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message = "The coupon title must contain a minimum of 5 characters and a maximum of 50 characters")
	@Size(min = 5, max = 50, message = "The coupon title must contain a minimum of 5 characters and a maximum of 50 characters")
	private String title;
	private Date startDate;
	@Future(message = "Coupon end date must be  future.")
	private Date endDate;
	@Max(value = 1000, message = "Coupon amount must be maximum 1000")
	private int amount;
	private CouponCaregory category;
	@Size(min = 20, max = 200, message = "The message must contain at least 20 characters and a maximum of 200 characters")
	private String message;
	@Positive(message = "The coupon price must be positive")
	private double price;
	@URL(message = "The image must be a URL")
	private String image;
	@ManyToOne
	@JsonIgnore
	@Valid
	private Company company;
	@ManyToMany(mappedBy = "coupons")
	@Valid
	private List<Customer> customers;

	public Coupon() {
		customers = new ArrayList<>();
	}

	public Coupon(String title, Date startDate, Date endDate, int amount, CouponCaregory category, String message,
			double price, String image, Company company) {

		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.category = category;
		this.message = message;
		this.price = price;
		this.image = image;
		this.company = company;
		this.customers = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponCaregory getCategory() {
		return category;
	}

	public void setCategory(CouponCaregory category) {
		this.category = category;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", category=" + category + ", message=" + message + ", price=" + price
				+ ", image=" + image + " ]";
	}

}
