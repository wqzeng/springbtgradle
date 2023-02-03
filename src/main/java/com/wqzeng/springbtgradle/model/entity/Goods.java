package com.wqzeng.springbtgradle.model.entity;

import com.wqzeng.springbtgradle.constant.enums.ColorEnum;
import lombok.Data;

@Data
public class Goods {
    private ColorEnum colorEnum;
    private Integer time;
    private String name;
}
