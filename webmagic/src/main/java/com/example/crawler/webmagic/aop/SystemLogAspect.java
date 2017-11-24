package com.example.crawler.webmagic.aop;

import com.alibaba.fastjson.JSON;
import com.example.crawler.webmagic.annotation.ApiLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class SystemLogAspect {

    public SystemLogAspect() {
        System.err.println("LogService 实例化...");
    }

    /**
     * 在Spring2.0中，Pointcut的定义包括两个部分：Pointcut表示式(expression)和Pointcut签名(signature)。
     * 让我们先看看execution表示式的格式：
     * 括号中各个pattern分别表示修饰符匹配（modifier-pattern?）、返回值匹配（ret-type-pattern）、
     * 类路径匹配（declaring-type-pattern?）、方法名匹配（name-pattern）、参数匹配（(param-pattern)）、
     * 异常类型匹配（throws-pattern?），其中后面跟着“?”的是可选项。
     */
    // 定义切入点
    @Pointcut("@annotation(com.example.crawler.webmagic.annotation.ApiLog)")
    public void apiLogPointcut() {

    }

    // 前置通知
    @Before("execution(* com.example.crawler.webmagic.controller.*.*(..))")
    public void before(JoinPoint point) throws Throwable {
        System.err.println("前置通知...");
    }

    // 后置通知
    @After("execution(* com.example.crawler.webmagic.controller.*.*(..))")
    public void after(JoinPoint point) {
        System.err.println("后置通知...");
    }

    // 环绕通知
    @Around("apiLogPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.err.println(request.getRequestURL());
        System.err.println(JSON.toJSONString(request.getParameterMap(), true));

        String apiRemark = getApiRemark(point);
        System.err.println(apiRemark);
        return point.proceed();
    }

    // 方法运行出现异常时调用
    @AfterThrowing(pointcut = "execution(* com.example.crawler.webmagic.controller.*.*(..))", throwing = "ex")
    public void afterThrowing(Exception ex) {
        System.err.println("afterThrowing");
        System.err.println(ex);
    }

    // 获取注解中的中文备注____用于记录用户的操作日志描述
    public static String getApiRemark(ProceedingJoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) { // 找到当前调用的方法
                ApiLog apiLog = method.getAnnotation(ApiLog.class);
                return (apiLog != null) ? apiLog.remark() : null;
            }
        }
        return null;
    }

}
