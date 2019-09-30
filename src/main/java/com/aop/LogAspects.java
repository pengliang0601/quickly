package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspects {

	@Pointcut("execution(public * com.core.CommandExecutant.*(..))")
	public void pointCut(){};


	@AfterReturning(value = "pointCut()", returning = "result")
	public void logReturn(JoinPoint joinPoint,String result){
	}

	@Before(value = "pointCut()")
	public void logStart(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		System.out.println("方法:" + joinPoint.getSignature().getName() + " 参数：" + Arrays.toString(args));

	}

	@After("pointCut()")
	public void logEnd(JoinPoint joinPoint){
	}


	@AfterThrowing(value="pointCut()",throwing="exception")
	public void logException(JoinPoint joinPoint,Exception exception){

	}



}
