package com.wqzeng.springbtgradle.service;

import com.wqzeng.springbtgradle.annotation.AquiredLockWorker;
import com.wqzeng.springbtgradle.model.dto.ImportLineRecord;
import com.wqzeng.springbtgradle.model.entity.Goods;
import com.wqzeng.springbtgradle.model.entity.UserInfo;

import java.util.concurrent.TimeUnit;

/**
 * Redis锁机制
 */
public interface RedisLockService {

	/**
	 * 上锁
	 * <p>
	 * <b>Title</b>: lock
	 * </p>
	 * @param lockKey 锁名称
	 * @param lockValue 锁定值【当前时间+锁定时间(毫秒数)】
	 * @return 上锁结果
	 */
	boolean lock(String lockKey, String lockValue);

	/**
	 * 尝试解锁
	 * <p>
	 * <b>Title</b>: unLock
	 * </p>
	 * @param lockKey 锁名称
	 * @param lockValue 锁定值【当前时间+锁定时间(毫秒数)】
	 */
	void unLock(String lockKey, String lockValue);

	/**
	 * 上锁
	 * <p>
	 * <b>Title</b>: lock
	 * </p>
	 * @param lockKey 锁名称
	 * @param lockValue 锁定值【当前时间+锁定时间(毫秒数)】
	 * @param tryTime 尝试次数
	 * @param sleepTime 等待尝试时间
	 * @return 上锁结果
	 */
	boolean lock(String lockKey, String lockValue, int tryTime, long sleepTime);
	<T> T lock(String lockKey, String lockValue, AquiredLockWorker<T> worker, long lockTime, long waitTime, TimeUnit unit, int retryTime, boolean defaultLock);
	/**
	 * 任务锁测试【单参】
	 * <p>
	 * <b>Title</b>: paramLock
	 * </p>
	 * @param param 所参数
	 */
	void paramLock(String param);

	/**
	 * 任务锁测试【无参】
	 * <p>
	 * <b>Title</b>: notParamLock 
	 * </p>
	 */
	void notParamLock();

	/**
	 * 任务锁测试【复参】
	 * <p>
	 * <b>Title</b>: notParamLock
	 * </p>
	 */
	ImportLineRecord twoParamLock(UserInfo loginUser, Goods goods);

	/**
	 * 纳秒级参数锁
	 * <p>
	 * <b>Title</b>: lockNano
	 * </p>
	 * @param lockKey 锁主键
	 * @param lockValue 锁值
	 * @param tryTime 重试次数
	 * @param sleepTime 等待时间
	 * @return 锁定结果
	 */
	boolean lockNano(String lockKey, String lockValue, int tryTime, long sleepTime);

}
