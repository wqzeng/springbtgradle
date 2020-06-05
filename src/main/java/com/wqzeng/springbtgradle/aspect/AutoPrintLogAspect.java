package com.wqzeng.springbtgradle.aspect;

import com.alibaba.fastjson.JSON;
import com.wqzeng.springbtgradle.annotation.AutoPrintLog;
import org.aspectj.apache.bcel.classfile.Signature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AutoPrintLogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置切入点，该方法无方法体，主要为方便同类中其他方法使用此处配置的切入点
     */
    @Pointcut("@annotation(com.wqzeng.springbtgradle.annotation.AutoPrintLog)")
    public void aspect() {
    }

    /**
     * 配置抛出异常后通知，使用在方法aspect()上注册的切入点
     * @param joinPoint
     * @param e 异常
     */
    @AfterThrowing(pointcut = "aspect()", throwing = "e")
    public void afterThrow(JoinPoint joinPoint, Exception e) {
        final Object[] args = joinPoint.getArgs();
        String name=joinPoint.getSignature().getDeclaringTypeName();
        logger.error("{} error , args={}", name, JSON.toJSONString(args), e);
    }
    //配置后置返回通知,使用在方法aspect()上注册的切入点
    @Before("aspect()")
    public void printParamLog(JoinPoint joinPoint){
        final Object[] args = joinPoint.getArgs() ;
        String name=joinPoint.getSignature().getDeclaringTypeName();
        try {
            logger.info("method={} request, args={}", joinPoint.getSignature().getName() , JSON.toJSONString(args));
        } catch (Exception e) {
            logger.error("ExceptionInterceptor.print log error method={} , args={}", joinPoint.getSignature().getName(),
                    JSON.toJSONString(args) , e);
        }
    }

    @AfterReturning(returning="rvt" , pointcut="aspect()")
    public void printResultLog(JoinPoint joinPoint , Object rvt){
        try {
            if(!isPrintLog(joinPoint)){
                return ;
            }
            logger.info("method={} response, result={}", joinPoint.getSignature().getName() , JSON.toJSONString(rvt));
        } catch (Exception e) {
            logger.error("ExceptionInterceptor.print log error method={}", joinPoint.getSignature().getName(), e);
        }
    }

    private boolean isPrintLog(JoinPoint joinPoint){
        MethodSignature sign = (MethodSignature) joinPoint.getSignature() ;
        final AutoPrintLog annotation = sign.getMethod().getAnnotation(AutoPrintLog.class) ;
        if(annotation == null){
            return false ;
        }
        return annotation.isPrintLog() ;
    }
}
