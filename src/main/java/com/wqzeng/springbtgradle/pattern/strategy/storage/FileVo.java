package com.wqzeng.springbtgradle.pattern.strategy.storage;

public class FileVo {
    public FileVo(String fileName, Long size) {
        this.fileName = fileName;
        this.size = size;
    }

    String fileName;
    Long size;
}
