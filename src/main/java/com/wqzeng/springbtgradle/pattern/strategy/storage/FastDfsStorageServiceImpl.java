package com.wqzeng.springbtgradle.pattern.strategy.storage;

import com.google.common.collect.Lists;
import com.wqzeng.springbtgradle.model.dto.ImportLineRecord;
import com.wqzeng.springbtgradle.pattern.template.AbstractImportTemplate;
import com.wqzeng.springbtgradle.pattern.template.ImportTemplateFactory;
import com.wqzeng.springbtgradle.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Slf4j
@Service
public class FastDfsStorageServiceImpl implements IStorageService<String, FileVo> {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    private ImportTemplateFactory importTemplateFactory;
    @Override
    public String getType() {
        return "Dfs";
    }

    @Override
    public FileVo upload(MultipartFile file) {
        File uploadFile = ExcelUtils.uploadFile(file);
        try (FileInputStream inputStream = new FileInputStream(uploadFile)) {
            ExcelUtils.ExcelReader reader = new ExcelUtils.ExcelReader(inputStream, 0);
            // 忽略文件头
            String[] header = reader.next();
            List<ImportLineRecord> recordLists = Lists.newArrayList();
            AbstractImportTemplate template = importTemplateFactory.getTemplate(true,1);
            while (reader.hasNext()) {
                String[] line=reader.next();
                ImportLineRecord importLineRecord = template.buildRecordModel(line);
                recordLists.add(importLineRecord);
            }
        } catch (Exception e) {
            logger.error("导入提报商品异常", e);
        }
        log.info("{}存储在Dfs中",uploadFile.getName());
        return new FileVo(uploadFile.getName(),file.getSize());
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
