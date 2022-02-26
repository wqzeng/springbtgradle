package com.wqzeng.springbtgradle.pattern.template;

import com.wqzeng.springbtgradle.model.dto.ImportLineRecord;
import org.springframework.stereotype.Component;

@Component
public class VcImportTemplate extends AbstractImportTemplate {
    @Override
    public boolean matchTemplate(boolean isVc, int type) {
        return isVc?true:false;
    }

    @Override
    public int columnCount() {
        return 2;
    }

    @Override
    public ImportLineRecord buildRecordModel(String[] lines) {
        return ImportLineRecord.builder()
                .name(lines[0])
                .id(0)
                .build();
    }
}
