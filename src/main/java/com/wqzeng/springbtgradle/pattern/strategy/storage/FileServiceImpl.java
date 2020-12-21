package com.wqzeng.springbtgradle.pattern.strategy.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public class FileServiceImpl implements FileService {
    @Autowired
    private IStorageService<String,FileVo> fastDsfStorageService;
    @Override
    public FileVo save(MultipartFile file) {
        return fastDsfStorageService.upload(file);
    }
}
