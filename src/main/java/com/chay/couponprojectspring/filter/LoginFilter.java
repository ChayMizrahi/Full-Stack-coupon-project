package com.chay.couponprojectspring.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UrlPathHelper;

import com.chay.couponprojectspring.entities.CustomLogin;

/**
 * The class will serve as a filter to check every request sent to the. server
 * if there is a session.
 * 
 * @author Chay Mizrahi
 *
 */
@WebFilter("/couponProject/*")
public class LoginFilter implements Filter {

	@Value("${coupon.project.session.attribute.name}")
	private String loggedDetails;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Get the session.
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession(false);
		// Takes the path from the request that triggered the session.
		String path = new UrlPathHelper().getPathWithinApplication(httpServletRequest);

		// If the session == null send error to the client.
		if (session == null) {
			HttpServletResponse httpsServletResponse = (HttpServletResponse) response;
			httpsServletResponse.sendError(401, "You are not logged in");

			// Else check if the part of the path after "couponProject/" compatible with the
			// type of client that set up a session.
		} else {
			if (session.getAttribute(loggedDetails) != null) {
				String restPath = path.split("/")[2];
				CustomLogin customLogin = (CustomLogin) session.getAttribute(loggedDetails);

				if (restPath.toUpperCase().equals(customLogin.getLoginType().toString())) {
					chain.doFilter(request, response);

				} else {
					HttpServletResponse httpsServletResponse = (HttpServletResponse) response;
					httpsServletResponse.sendError(401, "You are not logged in");
				}
			}else {
				HttpServletResponse httpsServletResponse = (HttpServletResponse) response;
				httpsServletResponse.sendError(401, "You are not logged in");
			}
		}
	}

}
