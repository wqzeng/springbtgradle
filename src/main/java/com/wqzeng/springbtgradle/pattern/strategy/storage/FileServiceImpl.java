package com.wqzeng.springbtgradle.pattern.strategy.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service("fileServiceImpl")
public class FileServiceImpl implements FileService {
    @Override
    public FileVo save(MultipartFile file) {
        IStorageService<String,FileVo> fastDsfStorageService=StorageServiceFactory.getStorageService("Dfs");
        FileVo upload = fastDsfStorageService.upload(file);
        return upload;
    }
}
