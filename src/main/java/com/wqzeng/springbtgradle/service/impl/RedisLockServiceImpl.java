package com.wqzeng.springbtgradle.service.impl;

import com.wqzeng.springbtgradle.annotation.AquiredLockWorker;
import com.wqzeng.springbtgradle.annotation.RedisLockHandler;
import com.wqzeng.springbtgradle.exception.BizException;
import com.wqzeng.springbtgradle.model.dto.ImportLineRecord;
import com.wqzeng.springbtgradle.model.entity.Goods;
import com.wqzeng.springbtgradle.model.entity.UserInfo;
import com.wqzeng.springbtgradle.service.RedisLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisLockServiceImpl implements RedisLockService {
    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 加锁
     * <p>(非 Javadoc) </p>
     * <p>
     * <b>Title</b>: lock
     * </p> 
     */
    @Override
	public boolean lock(String lockKey, String lockValue, int tryTime, long sleepTime){

    	for (int tryIndex = 0, totalTime = Math.max(tryTime, 1); tryIndex < totalTime; tryIndex++) {
    		ValueOperations<String, String> opsValue = redisTemplate.opsForValue();
    		if(!ObjectUtils.isEmpty(opsValue)) {
    			Boolean lockResult = opsValue.setIfAbsent(lockKey, lockValue);
    			if (!ObjectUtils.isEmpty(lockResult) && lockResult) {
    				return true;
    			}
    			 String currentValue = opsValue.get(lockKey);
	            log.debug("【上锁】获取锁【{}】已存在：{}", lockKey, currentValue);
	            // 如果锁过期
	            if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue)
	                    < System.currentTimeMillis()) {
	                // 获取上一个锁的时间
	                String oldValue = opsValue.getAndSet(lockKey, lockValue);
	                log.debug("【上锁】获取锁【{}】已超时，替换旧值【{}】为：{}", lockKey, oldValue, lockValue);
	                if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
	                    return true;
	                }
	            }
	            if(totalTime > 1 && sleepTime > 0) {
	            	log.debug("【上锁】第【{}/{}】次上锁【{}】失败，等待【{}】毫秒后重试", tryIndex + 1, totalTime, lockKey, sleepTime);
	            	try {
	            		Thread.sleep(sleepTime);
					} catch (Exception e) {
					}
	            }
    		}else {
    			throw new BizException("redis服务异常");
    		}
		}
        return false;
    }

    @Override
	public boolean lockNano(String lockKey, String lockValue, int tryTime, long sleepTime){

    	for (int tryIndex = 0, totalTime = Math.max(tryTime, 1); tryIndex < totalTime; tryIndex++) {
    		ValueOperations<String, String> opsValue = redisTemplate.opsForValue();
    		if(!ObjectUtils.isEmpty(opsValue)) {
    			Boolean lockResult = opsValue.setIfAbsent(lockKey, lockValue);
    			if (!ObjectUtils.isEmpty(lockResult) && lockResult) {
    				return true;
    			}
                String currentValue = opsValue.get(lockKey);
                // 如果锁过期
                if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.nanoTime()) {
                    // 获取上一个锁的时间
                    String oldValue = opsValue.getAndSet(lockKey, lockValue);
                    if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                        return true;
                    }
                }
                if(totalTime > 1 && sleepTime > 0) {
                	log.debug("【上锁】第【{}/{}】次上锁【{}】失败，等待【{}】毫秒后重试", tryIndex + 1, totalTime, lockKey, sleepTime);
                	try {
                		Thread.sleep(sleepTime);
    				} catch (Exception e) {
    				}
                }
    		}else {
				throw new BizException("redis服务异常");
    		}
		}
        return false;
    }

    /**
     * 解锁
     * <p>(非 Javadoc) </p>
     * <p>
     * <b>Title</b>: unLock
     * </p>
     */
    @Override
    public void unLock(String lockKey, String lockValue) {
        String currentValue = redisTemplate.opsForValue().get(lockKey);
        if (!StringUtils.isEmpty(currentValue) && currentValue.equals(lockValue)) {
            redisTemplate.opsForValue().getOperations().delete(lockKey);
        }
    }

	@Override
	public boolean lock(String lockKey, String lockValue) {
		return lock(lockKey, lockValue, 0, 0);
	}
	/**
	 * 使用Lettuce(Jedis)封装的RedisTemplate配置分布式锁
	 * <p>
	 * <b>Title</b>: lock
	 * </p>
	 *
	 * @param <T>         响应结果
	 * @param lockKey     锁key
	 * @param lockValue   锁value
	 * @param worker      任务执行
	 * @param lockTime    锁定时间
	 * @param waitTime    等待时间
	 * @param unit        时间单位
	 * @param retryTime   重试次数
	 * @param errorCode   异常码
	 * @param defaultLock 上锁失败后是否执行
	 * @throws ServiceException 运行异常
	 * @return 任务结果
	 */
	@SuppressWarnings("all")
	public <T> T lock(String lockKey, String lockValue, AquiredLockWorker<T> worker, long lockTime, long waitTime, TimeUnit unit, int retryTime, boolean defaultLock) {
		// 一开始设置没有锁
		boolean lock = false;
		try {
			for (int i = 0; i < retryTime; i++) {
				// 获取锁并设置锁过期时间: 如果锁已存在, 返回false(保留旧值); 如果锁不存在, 新增一个锁, 并设置锁的过期时间
				log.debug("redisTemplate:{}", redisTemplate);
				log.debug("opsForValue:{}", redisTemplate.opsForValue());
				lock = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, lockTime, unit);
				if (lock) {
					break;
				}
//				else {
//					// 线程休眠waitTime
//					log.info("【锁任务-{}】第【{}】次尝试获取锁, 未获取到锁, 线程休眠【{}】ms", lockKey, i+1, waitTime);
//					Thread.sleep(waitTime);
//				}
			}
			log.info("【锁任务-{}】是否获取到锁: 【{}】", lockKey, lock);
			// 拿到锁,执行业务
			if (lock) {
				return worker.invokeAfterLockAquire();
			} else if (defaultLock) {
				// 没有锁,是否默认锁成功
				log.info("【锁任务-{}】没有获取到锁，默认锁成功，继续执行任务！", lockKey);
				return worker.invokeAfterLockAquire();
			} else {
				// 未获取到共享锁，默认锁为空，抛出指定异常
				log.info("【锁任务-{}】没有获取到锁，不继续执行任务！", lockKey);
				throw new BizException("【锁任务-{}】没有获取到锁，不继续执行任务！");
				// 没有锁return结束方法
				// return null;
			}
//		} catch (InterruptedException e) {
//			// 线程休眠异常
//			e.printStackTrace();
//			log.error("【锁任务-{}】线程等待异常，errMsg：【{}】", lockKey, e.getMessage());
//			throw new ServiceException(ObjectUtils.isEmpty(errorCode) ? SystemErrCode.SYSTEM_UNKNOW_ERROR : errorCode);
		}
		finally {
			// 如果拿到锁,最终释放锁
			if (lock) {
				redisTemplate.delete(lockKey);
				log.info("【锁任务-{}】任务结束，释放锁!", lockKey);
			} else {
				// 如果没有拿到锁,无需释放锁
				log.info("【锁任务-{}】没有获取到锁，无需释放锁!", lockKey);
			}
		}
	}
	@RedisLockHandler(value = "'PARAM::'+#machineColumnConfig.companyId+'::'+#patchId", lockTime = 3*60*1000, tryTime = 3, tryWaitTime = 5*1000)
	@Override
	public void paramLock(String patchId) {
		log.info("--->{}", patchId);
	}
	@RedisLockHandler(value = "'NOTPARAM::TEST'", lockTime = 3*60*1000, tryTime = 3, tryWaitTime = 5*1000)
	@Override
	public void notParamLock() {
		log.info("--->{}", System.currentTimeMillis());
	}
	

	@RedisLockHandler(value = "'PRE::MATCH::'+#loginUser.name+'::'+#goods.name", lockTime = 5*60*1000, tryTime = 3, tryWaitTime = 1000)
	@Override
	public ImportLineRecord twoParamLock(UserInfo loginUser, Goods goods) {
		log.info("--------->{}", loginUser);
		log.info("--------->{}", goods);
		return null;
	}
}
