package com.wqzeng.springbtgradle.pattern.strategy.storage;

import org.springframework.web.multipart.MultipartFile;
public interface FileService {
    FileVo save(MultipartFile file);
}
