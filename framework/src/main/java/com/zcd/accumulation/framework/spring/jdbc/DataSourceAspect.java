package com.zcd.accumulation.framework.spring.jdbc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @description
 */
@Aspect
@Component
public class DataSourceAspect {
    private static final Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * mall slave库dao层point cut
     */
    @Pointcut("execution(* com.smzc.report.provider.dao.mall.*.select*(..))")
    public void mallSlaveDaoPointCut() {
    }

    /**
     * mall slave库dao层around
     */
    @Around("mallSlaveDaoPointCut()")
    public Object mallSlaveDaoAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            DataSourceContext.setCurrentDataSource(DataSourceAddr.MALL_SLAVE);
            log.info("the current thread datasource: {}", DataSourceAddr.MALL_SLAVE);
            return joinPoint.proceed();
        } finally {
            DataSourceContext.removeDataSource();
            log.info("remove current thread datasource: {} ", DataSourceAddr.MALL_SLAVE);
        }
    }
}
