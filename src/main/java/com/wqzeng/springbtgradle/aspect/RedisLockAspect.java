package com.wqzeng.springbtgradle.aspect;

import com.wqzeng.springbtgradle.annotation.RedisLockHandler;
import com.wqzeng.springbtgradle.constant.RedisLockExpressionEvaluator;
import com.wqzeng.springbtgradle.exception.BizException;
import com.wqzeng.springbtgradle.service.RedisLockService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * service 请求拦截
 * <p>
 * <b>ClassName</b>: ServiceLogAspect
 * </p>
 * <p>
 * <b>Date</b>: 2019年10月14日 下午7:29:32
 * </p>
 * 
 * @author zhhek
 * @version : 1.0
 */
@Aspect
@Component
@Slf4j
public class RedisLockAspect {

	@Autowired
	private RedisLockService redisLockService;

	/**
	 * service 请求入口作为切点,
	 */
	@Pointcut("@annotation(com.wqzeng.springbtgradle.annotation.RedisLockHandler)")
	public void cutPoint() {
	}

	/**
	 * service 请求拦截日志处理主方法
	 * <p>
	 * <b>Title</b>: doAround
	 * </p>
	 * 
	 * @param pjp 切入点
	 * @return 处理结果
	 * @throws Throwable 抛出异常信息
	 */
//	@Around(value = "cutPoint()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		RedisLockHandler lockHandler = AnnotationUtils.findAnnotation(((MethodSignature) pjp.getSignature()).getMethod(), RedisLockHandler.class);
		boolean lock = false;
		if (ObjectUtils.isEmpty(lockHandler)) {
			return pjp.proceed();
		}
		String lockKey = String.format("KMCGC::LOCK::%s", RedisLockExpressionEvaluator.getRedisKeyElValue(pjp, lockHandler, String.class));
		long lockTime = lockHandler.lockTime() > 0 ? lockHandler.lockTime() : 1000;
		String lockValue = String.valueOf(System.currentTimeMillis() + lockTime);
		log.info("【任务上锁】任务获取锁【{}】锁定时间【{}】：{}", lockKey, lockTime, lockValue);
		try {
			// 尝试上锁
			log.info("【任务上锁】任务获取锁【{}】：{}", lockKey, lockValue);
			Assert.isTrue(lock = redisLockService.lock(lockKey, lockValue, lockHandler.tryTime(), lockHandler.tryWaitTime()), "获取锁失败");
			// 上锁成功
			log.info("【任务上锁】任务上锁【{}】成功：{}", lockKey, lockValue);
			return pjp.proceed();
		} catch (Exception e) {
			log.error("【任务上锁】任务上锁【{}】(执行)失败：{}", lockKey, e.getMessage());
			throw new BizException("任务上锁】任务上锁(执行)失败", e);
		} finally {
			log.info("【任务上锁】任务完成解锁【{}】：{}", lockKey, lockValue);
			try {
				// 已上锁进行解锁
				if (lock) {
					redisLockService.unLock(lockKey, lockValue);
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * service 请求拦截日志处理主方法
	 * <p>
	 * <b>Title</b>: doAround
	 * </p>
	 * 
	 * @param pjp 切入点
	 * @return 处理结果
	 * @throws Throwable 抛出异常信息
	 */
	@Around(value = "cutPoint()")
	public Object doAround2(ProceedingJoinPoint pjp) throws Throwable {
		RedisLockHandler lockHandler = AnnotationUtils.findAnnotation(((MethodSignature) pjp.getSignature()).getMethod(), RedisLockHandler.class);
		if (ObjectUtils.isEmpty(lockHandler)) {
			return pjp.proceed();
		}
		String lockKey = String.format("KMCGC::LOCK::%s", RedisLockExpressionEvaluator.getRedisKeyElValue(pjp, lockHandler, String.class));
		long lockTime = lockHandler.lockTime() > 0 ? lockHandler.lockTime() : 1000;
		String lockValue = String.valueOf(System.currentTimeMillis() + lockTime);
		log.info("【任务上锁】任务获取锁【{}】锁定时间【{}】：{}", lockKey, lockTime, lockValue);
		return redisLockService.lock(lockKey, lockValue, () -> {
			try {
				return pjp.proceed();
			} catch (Throwable e) {
				log.error("【任务上锁】任务上锁【{}】执行失败：{}", lockKey, e.getMessage());
				throw new BizException(e);
			}
		}, lockTime, lockHandler.tryWaitTime(), TimeUnit.MILLISECONDS, lockHandler.tryTime(), lockHandler.defaultLock());
	}

}
