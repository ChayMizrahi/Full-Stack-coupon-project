package com.chay.couponprojectspring.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.chay.couponprojectspring.entities.ApiError;

/**
 * The class will handle the errors that will be thrown when the system fails
 * and will display a customized message.
 * 
 * @author Chay Mizrahi
 *
 */
@ControllerAdvice
public class CustomExceptionHandler {

	@Value("${coupon.project.api.error.code.coupon.system.excption}")
	private String couponSystemExceptionCode;

	@Value("${coupon.project.api.error.code.invalid.value}")
	private String invalidValueError;

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<Object> handleThrowable(Throwable e) {
		ApiError apiError = new ApiError("SERVER_ERROR",
				"We sorry but someting wrong happend. Plesae content the admin.");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CouponSystemException.class)
	public ResponseEntity<Object> handleCouponSystemException(CouponSystemException exception) {
		ApiError apiError = new ApiError(couponSystemExceptionCode, exception.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleValidationException(ConstraintViolationException e) {

		List<String> validationMessage = new ArrayList<>();
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		for (ConstraintViolation<?> constraintViolation : violations) {
			String message = "Invalid value :" + constraintViolation.getInvalidValue() + ". message: "
					+ constraintViolation.getMessage();
			validationMessage.add(message);
		}

		return new ResponseEntity<Object>(new ApiError(invalidValueError, validationMessage), HttpStatus.BAD_REQUEST);
	}
}
