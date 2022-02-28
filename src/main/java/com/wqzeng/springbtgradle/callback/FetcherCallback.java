package com.wqzeng.springbtgradle.callback;

import com.wqzeng.springbtgradle.entity.UserInfo;

public interface FetcherCallback {
    void onData(UserInfo userInfo) throws Exception;

    void onError(Throwable cause);
}
