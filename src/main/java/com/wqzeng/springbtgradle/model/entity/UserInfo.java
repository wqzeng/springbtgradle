package com.wqzeng.springbtgradle.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
    private Long id;
    private String name;
    private String loginName;
    private String mobile;
}
