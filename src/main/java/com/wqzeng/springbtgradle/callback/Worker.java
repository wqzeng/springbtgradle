package com.wqzeng.springbtgradle.callback;

import com.wqzeng.springbtgradle.model.entity.UserInfo;

public class Worker {
    public void doWork() {
        Fetcher fetcher=new FetcherImpl(new UserInfo(1L,"testName","testName","12343"));
        fetcher.fetchData(new FetcherCallback() {
            @Override
            public void onData(UserInfo userInfo) throws Exception {

            }

            @Override
            public void onError(Throwable cause) {

            }
        });
    }
}
