package com.wqzeng.springbtgradle.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLockHandler {

	/**
	 * 锁定值
	 * <p>
	 * <b>Title</b>: value
	 * </p>
	 *
	 * @return 锁定主键
	 */
	String value() default "REDIS::KEY";

	/**
	 * 锁定时间【毫秒】：不超过0使用默认值：1000
	 * <p>
	 * <b>Title</b>: lockTime
	 * </p>
	 *
	 * @return 锁定时间
	 */
	long lockTime() default 1000;

	/**
	 * 重试次数：不超过1，不重试
	 * <p>
	 * <b>Title</b>: tryTime
	 * </p>
	 *
	 * @return 重试次数
	 */
	int tryTime() default 0;

	/**
	 * 重试等待时间【毫秒】：不超过0，不等待
	 * <p>
	 * <b>Title</b>: tryWaitTime
	 * </p>
	 *
	 * @return 重试等待时间
	 */
	long tryWaitTime() default 0;


	/**
	 * 默认获取锁结果:未获取锁
	 *
	 * @return 默认是否获取锁
	 */
	boolean defaultLock() default false;
}
