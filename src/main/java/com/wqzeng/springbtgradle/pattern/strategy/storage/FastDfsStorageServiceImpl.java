package com.wqzeng.springbtgradle.pattern.strategy.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class FastDfsStorageServiceImpl implements IStorageService<String, FileVo> {
    @Override
    public FileVo upload(MultipartFile file) {
        log.info("{}存储在Dfs中",file.getName());
        return new FileVo(file.getName(),file.getSize());
    }

    @Override
    public FileVo download(String s) {
        log.info("{}从Dfs中下载",s);
        return new FileVo(s,1000L);
    }

    @Override
    public void delete(String s) {
        log.info("从Dfs删除文件");
    }
}
