package com.cyssxt.dbexport.bean;

import lombok.Data;

@Data
public class ExportColumn {
//        "title":"", //标题
//        "width":100,//宽度
//        "sort":0,//排序
//        "name":"test" //字段名
    String title;
    Integer width;
    Integer sort;
    String name;
}
