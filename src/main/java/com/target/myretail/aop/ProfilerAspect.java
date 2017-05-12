package com.target.myretail.aop;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.target.myretail.service.RetailServiceImpl;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
public class ProfilerAspect {

	private static final Logger LOG = Logger.getLogger(RetailServiceImpl.class);

	@Around("execution(* com.target.myretail.service.*.*(..))")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch();
		String name = pjp.getSignature().getName();
		try {
			sw.start();
			return pjp.proceed();
		} finally {
			sw.stop();
			LOG.info("TIME TAKEN FOR EXECUTION: " + sw.getTotalTimeMillis() + " - " + name);
		}
	}
}
