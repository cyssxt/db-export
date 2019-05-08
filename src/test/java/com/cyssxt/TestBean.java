package com.cyssxt;

import com.cyssxt.dbexport.annotation.ExportBean;
import com.cyssxt.dbexport.annotation.ExportTable;
import lombok.Data;

import java.util.Date;

@ExportTable(url = "jdbc:mysql://localhost:3308/db_demo?characterEncoding=utf8",
        tableName = "t_data_new",
        userName = "root",
        password = "!QAZ2wsx")
@Data
public class TestBean {
    @ExportBean(title = "标题",width = 70)
    String title;
    @ExportBean(title = "描述",width = 100)
    String description;
    @ExportBean(title = "爬取时间",width = 20)
    Date createTime;
    @ExportBean(title = "地址",width = 50)
    String href;
    @ExportBean(title = "关键词",width = 20)
    String keyWord;
    @ExportBean(title = "正面评分",width = 10)
    Integer pos;
    @ExportBean(title = "负面评分",width = 10)
    Integer neg;

}
