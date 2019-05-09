package com.cyssxt.dbexport.core;

import com.cyssxt.dbexport.bean.ExportItem;

import java.util.Map;

public class DefaultExportCallback implements ExportCallback{
    @Override
    public String getTitle(ExportItem item) {
        return item.getTitle();
    }

    @Override
    public Object getValue(String column, Map<String, Object> item) {
        return item.get(column);
    }
}
