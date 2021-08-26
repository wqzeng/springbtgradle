package com.wqzeng.springbtgradle.service;

import com.wqzeng.springbtgradle.entity.Goods;
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
        if (!throttleConfig.getColorEnums().contains(goods.getColorEnum())){
            return false;
        }
        return true;
    }

    private void refreshThrottleConfig() {
        this.throttleConfig=new ThrottleConfig();
    }
}
