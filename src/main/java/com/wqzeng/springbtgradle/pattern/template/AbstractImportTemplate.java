package com.wqzeng.springbtgradle.pattern.template;

import com.wqzeng.springbtgradle.model.dto.ImportLineRecord;

import java.util.List;

public abstract class AbstractImportTemplate {
    public abstract boolean matchTemplate(boolean isVc,int type);

    public abstract  int columnCount();

    public abstract ImportLineRecord buildRecordModel(List<String> lines);

    public String validate(boolean isVc, String[] columns) {
        if (columns == null) {
            return "文件头不能为空";
        }
        boolean validateResult = validate(columns.length);
        return validateResult?"success":"导入文件列数必须为" + columnCount();
    }

    /**
     * 校验模板
     */
    private boolean validate(int columnCount) {
        return columnCount == columnCount();
    }
}
