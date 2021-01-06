package com.example.demo1.common;

import lombok.Getter;

@Getter
public enum DynamicDataSourceEnum {
    MASTER("master"),
    SLAVE("slave");
    private String dataSourceName;

    DynamicDataSourceEnum(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }
}
