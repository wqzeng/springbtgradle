package com.wqzeng.springbtgradle.annotation;

import com.wqzeng.springbtgradle.exception.BizException;

/**
 * 获取锁后需要处理的逻辑
 */
public interface AquiredLockWorker<T> {
	
	T invokeAfterLockAquire() throws BizException;
}
