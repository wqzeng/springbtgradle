package com.wqzeng.springbtgradle.pattern.strategy.storage;

import lombok.Data;

@Data
public class FileVo {
    public FileVo(String fileName, Long size) {
        this.fileName = fileName;
        this.size = size;
    }

    String fileName;
    Long size;
}
