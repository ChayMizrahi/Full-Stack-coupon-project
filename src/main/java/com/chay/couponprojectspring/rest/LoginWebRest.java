package com.chay.couponprojectspring.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chay.couponprojectspring.entities.CustomLogin;
import com.chay.couponprojectspring.entities.LoginType;
import com.chay.couponprojectspring.exceptions.InvalidLoginException;
import com.chay.couponprojectspring.exceptions.ObjectNotExistsException;
import com.chay.couponprojectspring.services.SystemService;

/**
 * A class that makes functions associated with connecting to the system to
 * request http.
 * 
 * @author Chay Mizrahi
 *
 */
@RestController
@RequestMapping("login")
public class LoginWebRest {

	@Autowired
	private SystemService loginService;

	@Value("${coupon.project.session.attribute.name}")
	private String loggedDetails;

	/**
	 * A function makes a system login and generates a session if it succeeds.
	 * Returns true if the connection is successful, false if not.
	 * 
	 * @path http://localhost:8080/login?password=1234&loginType=ADMIN&name=Example
	 * @param name      Through the application path, required.
	 * @param password  Through the application path, required.
	 * @param loginType Through the application path, required.
	 * @param response
	 * @param request
	 * @return text plain
	 */
	@PostMapping
	public boolean login(@RequestParam() String name, @RequestParam String password, @RequestParam LoginType loginType,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			CustomLogin customLogin = loginService.login(name, password, loginType);
			session.setAttribute(loggedDetails, customLogin);
		} catch (InvalidLoginException | ObjectNotExistsException e) {
			return false;
		}
		return true;
	}

	/**
	 * Function performs a logout and deletes the current session. Returns true if
	 * the logout succeeded, false or not.
	 * 
	 * @path http://localhost:8080/login/logout
	 * @param request
	 * @param response
	 * @return text plain
	 */
	@RequestMapping(path = "logout")
	public boolean logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			session.invalidate();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
