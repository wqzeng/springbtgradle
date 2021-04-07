package com.wqzeng.springbtgradle.pattern.strategy.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class DatabaseStorageServiceImpl implements IStorageService<String, FileVo> {
    @Override
    public String getType() {
        return "DB";
    }

    @Override
    public FileVo upload(MultipartFile file) {
        log.info("{}存储在数据库中",file.getName());
        return new FileVo(file.getName(),file.getSize());
    }

    @Override
    public FileVo download(String s) {
        log.info("{}从数据库中下载",s);
        return new FileVo(s,1000L);
    }

    @Override
    public void delete(String s) {
        log.info("从数据库删除文件");
    }
}
