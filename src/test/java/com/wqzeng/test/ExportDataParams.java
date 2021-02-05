package com.wqzeng.test;

import java.util.Set;

/**
 *
 */
public class ExportDataParams {
    private String basePath;
    private Set<Integer> dataTypes;//
    private String version;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public Set<Integer> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(Set<Integer> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
