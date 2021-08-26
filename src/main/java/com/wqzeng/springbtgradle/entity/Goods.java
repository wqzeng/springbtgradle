package com.wqzeng.springbtgradle.entity;

import com.wqzeng.springbtgradle.constant.enums.ColorEnum;
import lombok.Data;

@Data
public class Goods {
    private ColorEnum colorEnum;
    private Integer time;
    private String name;
}
