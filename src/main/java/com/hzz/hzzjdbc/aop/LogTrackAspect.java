//package com.hzz.hzzjdbc.aop;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
///**
// * @author ：hzz
// * @description：TODO
// * @date ：2021/4/28 11:02
// */
//@Component
//@Aspect
//@Order(2)
//public class LogTrackAspect {
//
//    /**
//     * 定义切入点，切入点为com.example.aop下的所有函数
//     */
//    @Pointcut("execution(public * com.hzz.hzzjdbc.controller..*.*(..))")
//    public void webLog(){}
//
//
//    @Around("webLog()")
//    public Object round(ProceedingJoinPoint pjp){
//        System.out.println("aop注解生效:"+pjp.getKind());
//        System.out.println(pjp);
//        try {
//            Object proceed = pjp.proceed();
//            System.out.println("aop环绕之后:"+pjp);
//            return proceed;
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            return null;
//        }
//
//    }
//}
