package com.cyssxt.dbexport.core;

import com.cyssxt.dbexport.bean.ExportItem;

import java.util.Map;

public interface ExportCallback {
    String getTitle(ExportItem item);

    Object getValue(String column, Map<String, Object> item);
}
