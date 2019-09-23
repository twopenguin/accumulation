package com.zcd.accumulation.framework.springboot.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description TODO
 * @Date 2019/5/20 16:40
 * @Created by  zhanglian
 */
@Configuration
public class TaxiDriverConfigurerAdapter implements WebMvcConfigurer {

    @Bean
    DriverMobileInterceptor driverMobileInterceptor(){
        return new DriverMobileInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(driverMobileInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/swagger-ui.html/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/error",
                "/",
                "/csrf",
                "/tianfuPay/**",
                "/driverAppVersion/**",
                "/driverLogin/loginValidateCode",
                "/driverLogin/login",
                "/driverLogin/refreshLogin",
                "/driverReg/**",
                "/myWallet/**"
        );
    }
}
