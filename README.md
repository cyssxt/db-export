# 代码导出方式
## @ExportTable注解 类注解
- url:数据库连接url
- tableName：数据库表明
- userName:数据库用户名
- password:数据连接密码
## ExportBean 字段注解
- title:excel标题
- width:列宽度
- column:对应数据库字段名 如果没有设置，则与字段同名
- sort:排序字段 增量排序
示例:
<pre><code>
@ExportTable(url = "jdbc:mysql://localhost:3308/db_demo?characterEncoding=utf8",
        tableName = "t_data_new",
        userName = "root",
        password = "")
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
</code></pre>

## 导出用法
<pre><code>
  ExportHelper exportHelper = ExportHelper.Builder.create();
  exportHelper.start(TestBean.class,"./export.xlsx");//第二个参数是导出文件地址
</code></pre>

## ExportCallback
可以通过次导出回调来设置导出字段值
<pre><code>
  String getTitle(ExportItem item);//获取字段标题
  Object getValue(String column, Map<String, Object> item);//获取value值
</code></pre>

DefaultExportCallback 默认实现类 设置excel标题和value值


# 接口导出方式
接口地址：
<pre><code>
    http://ip:port/export
</code></pre>
<pre><code>
{
    "fileName":"",//导出文件名称，不填为默认名称
    "dbInfo":{
        "url":"",//地址
        "userName":"",//用户名
        "password":""//密码
    },
    items:[{
        "title":"", //标题
        "width":100,//宽度
        "sort":0,//排序
        "column":"test" //字段名
    }]
}
</code></pre>


