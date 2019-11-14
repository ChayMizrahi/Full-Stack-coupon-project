package com.chay.couponprojectspring;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.chay.couponprojectspring.entities.Coupon;
import com.chay.couponprojectspring.exceptions.InvalidLoginException;
import com.chay.couponprojectspring.exceptions.ObjectAlreadyExistsException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.exceptions.UpdatingException;
import com.chay.couponprojectspring.repositories.CouponRepository;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class CouponProjectSpringApplication {

	public static void main(String[] args)
			throws InvalidLoginException, ObjectAlreadyExistsException, ObjectNotExistsException, UpdatingException {
		SpringApplication.run(CouponProjectSpringApplication.class, args);
	}
}
