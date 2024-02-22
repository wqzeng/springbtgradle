package com.wqzeng.springbtgradle.service;

import com.wqzeng.springbtgradle.model.entity.Goods;
import com.wqzeng.springbtgradle.model.dto.ThrottleConfig;
import org.springframework.stereotype.Service;

@Service
public class ThrottleService {
    private ThrottleConfig throttleConfig;

    public boolean throttle(Goods goods) {
        if (goods == null) {
            return false;
        }
        refreshThrottleConfig();
        return throttleConfig.getColorEnums().contains(goods.getColorEnum());
    }

    private void refreshThrottleConfig() {
        this.throttleConfig=new ThrottleConfig();
    }
}
