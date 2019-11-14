package com.chay.couponprojectspring.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The object will describe an error that occurred while executing an action
 * that occurred on the system.
 * 
 * @author Chay Mizrahi
 *
 */
public class ApiError {

	private String code;
	private List<String> massages;

	// In case you want to add some messages in the constructor
	public ApiError(String code, String... massages) {
		this.code = code;
		this.massages = Arrays.asList(massages);
	}

	// In case you want to add some list of messages in the constructor
	public ApiError(String code, List<String> validationMessage) {
		this.code = code;
		this.massages = new ArrayList<>(validationMessage);
	}

	public String getCode() {
		return code;
	}

	public List<String> getMassages() {
		return massages;
	}

	@Override
	public String toString() {
		return "ApiError [code=" + code + ", massages=" + massages + "]";
	}

}
