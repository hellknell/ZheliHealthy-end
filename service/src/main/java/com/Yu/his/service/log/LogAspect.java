package com.Yu.his.service.log;

import com.Yu.his.core.util.IpUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/21 16:13
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    public LogAspect() {
        log.info("LogAspect init..");
    }

    @Pointcut("execution(public * com.Yu..*Controller.*(..))")
    public void controllerPointcut() {
    }
    @Before("controllerPointcut()")
    public void doBefore(JoinPoint jointPoint) {
//        MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Signature signature = jointPoint.getSignature();
        String name = signature.getName();
        log.info("请求地址:{} {}", request.getRequestURL().toString(), request.getMethod());
        log.info("类型方法:{} {}", signature.getDeclaringTypeName(), name);
        log.info("远程地址:{} {}", IpUtil.getIpAddr(request));
        Object[] args = jointPoint.getArgs();
        Object[] argments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            argments[i] = args[i];
        }
        String[] excludeProperties = {"password"};
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = new PropertyPreFilters().addFilter();
        excludefilter.addExcludes(excludeProperties);
        log.info("请求参数: {}", JSONObject.toJSONString(argments, excludefilter));
    }

    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
        String[] excludeProperties = {"mobile"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        log.info("返回结果: {}", JSONObject.toJSONString(result, excludefilter));
        log.info("------------- 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
        return result;
    }
}
