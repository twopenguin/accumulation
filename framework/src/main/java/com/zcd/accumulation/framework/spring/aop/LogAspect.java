package com.zcd.accumulation.framework.spring.aop;

import com.zcd.accumulation.jdkbase.custom.CustomAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * 切入点
 * Created by yjj on 2016-12-29
 */
@Component
@Aspect
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义切点：如果有此注解的地方
     */
    @Pointcut("@annotation(com.zcd.accumulation.jdkbase.custom.CustomAnnotation)")
    public void serviceAspect() {
    }
    /**
     * 在方法正常执行通过之后执行的通知
     * @param joinPoint 切点
     */
    @AfterReturning(value = "serviceAspect()",returning = "retValue")
    public void AfterReturning(JoinPoint joinPoint,Object retValue) {
        Method method = getMethod(joinPoint);
        if(method != null && method.isAnnotationPresent(CustomAnnotation.class)) {
            CustomAnnotation annotation = method.getAnnotation(CustomAnnotation.class);
            String content = executeTemplate(annotation.content(), joinPoint);
            String result = annotation.result();
            StringBuffer sb=new StringBuffer();
            sb.append(content);
            if(retValue!=null && result !=null && result !=""){
                sb.append(result.replaceAll("#\\{result\\}",retValue.toString()));
            }
        }
    }
    /**
     * 在目标方法非正常执行完成 发生异常 抛出异常的时候会走此方法
     * 获得异常可以用throwing
     * @param joinPoint
     */
    @AfterThrowing(value="serviceAspect()",throwing="ex")
    public void readAfterThrowing(JoinPoint joinPoint,Exception ex ){
        Method method = getMethod(joinPoint);
        if(method != null && method.isAnnotationPresent(CustomAnnotation.class)) {
            CustomAnnotation annotation = method.getAnnotation(CustomAnnotation.class);
            String content = executeTemplate(annotation.content(), joinPoint);
        }
    }

    /**
     * 解析SPEL
     * @param template
     * @param joinPoint
     * @return
     */
    private String executeTemplate(String template, JoinPoint joinPoint){
        LocalVariableTableParameterNameDiscoverer parameterNameDiscovere = new LocalVariableTableParameterNameDiscoverer();
        Method method = getMethod(joinPoint);
        String[] parameterNames = parameterNameDiscovere.getParameterNames(method);

        EvaluationContext context = new StandardEvaluationContext();
        ExpressionParser parser = new SpelExpressionParser();
        Object[] args = joinPoint.getArgs();
        if(args.length == parameterNames.length){
            for (int i = 0,len = args.length; i < len; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        return String.valueOf(parser.parseExpression(template, new TemplateParserContext()).getValue(context, String.class));
    }

    private Method getMethod(JoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        try {
            method = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return method;
    }
    /**
     * 获取注解中对方法的描述信息 用于service层注解
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static Map<String, Object> getAnnotationParemeter(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        final Map<String, Object> map = new HashMap<>();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    final CustomAnnotation annotation = method.getAnnotation(CustomAnnotation.class);
                    String content = annotation.content();
                    if (arguments != null && arguments.length >0) {
                        for (int i = 0; i < arguments.length; i++) {
                            content = content.replaceAll(String.format("args%s",i), arguments[i] == null ? "null":arguments[i].toString());
                        }
                    }
                    break;
                }
            }
        }
        return map;
    }
}

