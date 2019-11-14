package com.chay.couponprojectspring.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.chay.couponprojectspring.entities.Log;
import com.chay.couponprojectspring.repositories.LogRepository;

@Aspect
@Configuration
public class MyAspect {

	@Autowired
	private LogRepository logRepository;

	/**
	 * All the function in the package rest.
	 */
	@Pointcut("execution(* com.chay.couponprojectspring.rest.*.*(..))")
	public void allFunctionInRest() {
	};

	/**
	 * All the get function in the package rest that do get.
	 */
	@Pointcut("execution(* com.chay.couponprojectspring.rest.*.get*(..))")
	public void allGetFunctionInRest() {
	};

	/**
	 * All the log function in the package rest.
	 */
	@Pointcut("execution(* com.chay.couponprojectspring.rest.*.*Log(..))")
	public void allLogFunctionInRest() {
	};

	/**
	 * The point cut will run all the functions in the rest package besides the
	 * functions that start with get (for example: getAllCompanies) or the function
	 * performs an action related to entity log
	 */
	@Pointcut("allFunctionInRest() && !allGetFunctionInRest() && !allLogFunctionInRest()")
	public void whenAlwaysCreateLog() {

	}

	/**
	 * The function will be executed before all the function in the rest package
	 * except for the functions that begin with get or perform a function related to
	 * the log. The function will check if the function on which it was run is
	 * successful and will create a new log accordingly.
	 * 
	 * @param joinPoint Function activated
	 * @return What the original function returns
	 * @throws Throwable In case the original function threw an exception
	 */
	@Around("whenAlwaysCreateLog()")
	public Object createLog(ProceedingJoinPoint joinPoint) throws Throwable {

		Object result = null;
		Log log = new Log();
		log.setMessage("The function " + joinPoint.getSignature().getName() + " was triggered. ");

		Object[] objects = joinPoint.getArgs();
		for (Object object : objects) {
			if (object instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) object;
				log.setBrowser(request.getHeader("User-Agent"));
				log.setIp(request.getRemoteAddr());
			}
		}

		try {
			result = joinPoint.proceed();
		} catch (Exception e) {
			log.setSuccess(false);
			log.setMessage(log.getMessage() + "The function failed for a reason: " + e.getMessage());
			logRepository.save(log);
			throw e;
		}
		log.setSuccess(true);
		logRepository.save(log);
		return result;
	}

}
