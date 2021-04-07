package com.wqzeng.springbtgradle.pattern.strategy.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储接口
 * @param <Identify> 表示文件唯一标识，可任意类型
 * @param <T> 上传下载返回类型，可任意类型
 */
public interface IStorageService<Identify,T> {
    //策略类型
    String getType();

    T upload(MultipartFile file);

    T download(Identify identify);

    void delete(Identify identify);
}
