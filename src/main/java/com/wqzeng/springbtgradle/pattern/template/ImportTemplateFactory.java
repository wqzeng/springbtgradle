package com.wqzeng.springbtgradle.pattern.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class ImportTemplateFactory {
    @Autowired
    private List<AbstractImportTemplate> templateList;

    public AbstractImportTemplate getTemplate(boolean isVc,int type) {
        Optional<AbstractImportTemplate> templateOptional = templateList.stream().filter(template -> template.matchTemplate(isVc, type)).findFirst();
        return templateOptional.orElse(null);
    }
}
