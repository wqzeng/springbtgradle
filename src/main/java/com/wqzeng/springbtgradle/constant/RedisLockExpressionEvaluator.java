package com.wqzeng.springbtgradle.constant;

import com.wqzeng.springbtgradle.annotation.RedisLockHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * EL表达式支持对象
 */
public class RedisLockExpressionEvaluator extends CachedExpressionEvaluator {
	
	private static final Map<Object, RedisLockExpressionEvaluator> EXPRESSION_EVALUATOR_MAP = new ConcurrentHashMap<>();
	
	public static final RedisLockExpressionEvaluator getEvaluator(Class<?> clazz) {
		if(!EXPRESSION_EVALUATOR_MAP.containsKey(clazz)) {
			EXPRESSION_EVALUATOR_MAP.put(clazz, new RedisLockExpressionEvaluator());
		}
		return EXPRESSION_EVALUATOR_MAP.get(clazz);
	}
	
	private RedisLockExpressionEvaluator() {}
	
	/**
	 * 获取锁KEY【解析EL表达式】
	 * <p>
	 * <b>Title</b>: getRedisKeyElValue
	 * </p>
	 * @param <T> 锁格式
	 * @param joinPoint 接入点
	 * @param lockHandler 锁注解
	 * @param clazz 锁格式类
	 * @return 锁值
	 */
	public static final <T> T getRedisKeyElValue(ProceedingJoinPoint joinPoint, RedisLockHandler lockHandler, Class<T> clazz) {
		RedisLockExpressionEvaluator evaluator = getEvaluator(clazz);
        EvaluationContext evaluationContext = evaluator.createEvaluationContext(joinPoint.getTarget(), joinPoint.getTarget().getClass(), ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
        AnnotatedElementKey methodKey = new AnnotatedElementKey(((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getTarget().getClass());
        return evaluator.condition(lockHandler.value(), methodKey, evaluationContext, clazz);
    }
	
    private final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();
    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);
    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    private EvaluationContext createEvaluationContext(Object object, Class<?> targetClass, Method method, Object[] args) {
        Method targetMethod = getTargetMethod(targetClass, method);
        ExpressionRootObject root = new ExpressionRootObject(object, args);
        return new MethodBasedEvaluationContext(root, targetMethod, args, this.paramNameDiscoverer);
    }


    private <T> T condition(String conditionExpression, AnnotatedElementKey elementKey, EvaluationContext evalContext, Class<T> clazz) {
        return getExpression(this.conditionCache, elementKey, conditionExpression).getValue(evalContext, clazz);
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            if (targetMethod == null) {
                targetMethod = method;
            }
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }
}
