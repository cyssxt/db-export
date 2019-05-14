package com.cyssxt.dbexport.bean;

import lombok.Data;

import java.util.List;

//
//{
//        "fileName":"",//导出文件名称，不填为默认名称
//        "dbInfo":{
//        "url":"",//地址
//        "userName":"",//用户名
//        "password":""//密码
//        },
//        columns:[{
//        "title":"", //标题
//        "width":100,//宽度
//        "sort":0,//排序
//        "name":"test" //字段名
//        }]
//        }
@Data
public class ExportParamReq {
    String fileName;
    DbInfo dbInfo;
    List<ExportItem> items;
}
