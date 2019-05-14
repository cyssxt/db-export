package com.cyssxt.dbexport.bean;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class ExportItem {
    String title;
    String fieldName;
    Method writeMethod;
    String column;
    int sort;
    int width;
    public ExportItem(){

    }
    public ExportItem(String title, String fieldName,String column,int sort,int width) {
        this.title = title;
        this.fieldName = fieldName;
        this.sort = sort;
        this.column = column;
        this.width = width;
    }
}
