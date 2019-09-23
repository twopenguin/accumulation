package com.zcd.accumulation.framework.springboot.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.smzc.taxi.boot.response.HttpResponseEnum;
import com.smzc.taxi.boot.response.Response;
import com.smzc.taxi.common.utils.AesUtil;
import com.smzc.taxi.driver.web.controllers.LoginController;
import com.smzc.taxi.service.driver.bean.DriverTokenCheckRespVo;
import com.smzc.taxi.service.driver.bean.DriverTokenCheckVo;
import com.smzc.taxi.service.driver.service.IDriverLoginFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description  移动端拦截器 - token校验
 * @Date 2019/5/20 16:36
 * @Created by  zhanglian
 */
@Slf4j
public class DriverMobileInterceptor implements HandlerInterceptor {

    @Resource
    private LoginController loginController;

    @Reference
    IDriverLoginFacade driverLoginFacade;

    public static final String MOBILEAUTHORIZATION = "MobileAuthorization";
    public static final String DEVICEOSTYPE = "deviceOsType";//1-ios,0-android
    public static final String DRIVERID = "driverId";
    public static final String DRIVERID_PHONE = "driverPhone";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 过滤跨域OPTIONS请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }
        String deviceOsType = request.getHeader(DEVICEOSTYPE);
        //获取token - 非空校验
        String token = request.getHeader(MOBILEAUTHORIZATION);
        try {
            Assert.isTrue(token != null && !"".equals(token), "uri-->" + request.getServletPath() +",token can not be null");
            String[] mobileUser = AesUtil.aesDecrypt(token).split(":");
            request.setAttribute(DRIVERID, mobileUser[0]);//driverId
            request.setAttribute(DRIVERID_PHONE, mobileUser[1]);//driverPhone
        } catch (Exception e) {
            log.error("uri-->" + request.getServletPath() + ",deviceOsType(1-ios,0-android)-->" + deviceOsType + " ,验证用户登录失败,token->" + token + ",异常->" + e.getMessage(), e);
            Response<Object> dto = Response.instance().code(HttpResponseEnum.DRIVER_MOBILE_LOGIN_101.code).message("验证失败，请重新登陆！").build();
            writeResponse(response, dto);
            return false;
        }

        //token验证: token与driverId是否对应有效
        DriverTokenCheckVo tokenCheckVo = new DriverTokenCheckVo();
        tokenCheckVo.setDriverId(Long.valueOf((String) request.getAttribute(DRIVERID)));
        tokenCheckVo.setToken(token);
        /*Response checkTokenResponse = loginController.checkToken(tokenCheckVo);
        if (checkTokenResponse.getCode().intValue() != HttpResponseEnum.SUCCESS.code){
            //校验失败
            logger.error("验证用户登录失败");
            Response<Object> dto = Response.instance().code(checkTokenResponse.getCode()).message(checkTokenResponse.getMessage()).build();
            writeResponse(response, dto);
            return false;
        }*/
        DriverTokenCheckRespVo tokenCheckRespVo = driverLoginFacade.checkToken(tokenCheckVo);
        if (tokenCheckRespVo.getCode().intValue() != HttpResponseEnum.SUCCESS.code){
            //校验失败
            log.error("uri-->" + request.getServletPath() + ",deviceOsType(1-ios,0-android)-->" + deviceOsType + " ,验证用户登录失败,token->" + token + ",redis的错误->" + tokenCheckRespVo.getMsg());
            Response<Object> dto = Response.instance().code(tokenCheckRespVo.getCode()).message(tokenCheckRespVo.getMsg()).build();
            writeResponse(response, dto);
            return false;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }


    private void writeResponse(HttpServletResponse response, Response<Object> dto) {
        PrintWriter writer  = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            writer = response.getWriter();
            writer.write(JSON.toJSONString(dto, SerializerFeature.WriteMapNullValue));
            writer.flush();
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }

}
