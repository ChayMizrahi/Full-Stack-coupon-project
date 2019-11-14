package com.chay.couponprojectspring.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date date;
	private String ip;
	private String browser;
	private String message;
	private boolean success;

	public Log() {
		this.date = new Date();
	}

	public Log(Date date, String ip, String browser, String message, boolean success) {
		super();
		this.date = date;
		this.ip = ip;
		this.browser = browser;
		this.message = message;
		this.success = success;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", date=" + date + ", ip=" + ip + ", browser=" + browser + ", message = " + message
				+ ", success=" + success + "]";
	}

}
