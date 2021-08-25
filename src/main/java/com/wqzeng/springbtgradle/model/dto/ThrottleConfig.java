package com.wqzeng.springbtgradle.model.dto;

import com.wqzeng.springbtgradle.constant.enums.ColorEnum;
import lombok.Data;

import java.util.List;

/**
 * @author moke.tsang
 * @date 2021年8月25日
 */
@Data
public class ThrottleConfig {
    /**
     * 开启流量控制标志
     */
    private boolean enabled;
    /**
     * 强制卡住所有流量
     */
    private boolean forceStopAll;
    /**
     * 需要卡住的清单
     */
    private List<ColorEnum> colorEnums;

}
