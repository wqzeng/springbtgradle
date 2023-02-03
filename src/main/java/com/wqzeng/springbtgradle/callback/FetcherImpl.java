package com.wqzeng.springbtgradle.callback;

import com.wqzeng.springbtgradle.model.entity.UserInfo;

public class FetcherImpl implements Fetcher {
    private UserInfo userInfo;

    public FetcherImpl(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void fetchData(FetcherCallback callback) {
        try {
            callback.onData(userInfo);
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}
