package com.cyssxt.dbexport.core;

import com.cyssxt.dbexport.annotation.ExportBean;
import com.cyssxt.dbexport.annotation.ExportTable;
import com.cyssxt.dbexport.bean.DataResult;
import com.cyssxt.dbexport.bean.ExportItem;
import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.Date;

import com.cyssxt.dbexport.config.ExportConfig;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExportHelper {
    private SXSSFWorkbook sxssfWorkbook;
    private ExportConfig exportConfig;
    private int rowNum=-1;
    private int nextRow(){
        return ++rowNum;
    }

    public static class Builder{
        public static ExportHelper create(){
            ExportConfig exportConfig = new ExportConfig();
            ExportHelper exportHelper = new ExportHelper();
            exportHelper.setExportConfig(exportConfig);
            return exportHelper;
        }

        public static ExportHelper create(ExportConfig config){
            ExportHelper exportHelper = new ExportHelper();
            exportHelper.setExportConfig(config);
            return exportHelper;
        }
    }

    private ExportHelper(){

    }

    public void setExportConfig(ExportConfig config){
        this.exportConfig = config;
    }

    public void write(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        if(null!=sxssfWorkbook){
            sxssfWorkbook.write(fos);
            sxssfWorkbook.close();
        }

    }

    public void start(Class clazz,String path) throws SQLException, ClassNotFoundException, IntrospectionException, IOException {
        start(clazz);
        write(path);
    }
    void initConfig(){
        this.exportConfig = Optional.ofNullable(this.exportConfig).orElse(new ExportConfig(true));
    }
    public void start(Class clazz) throws SQLException, ClassNotFoundException{
        initConfig();
        ExportTable exportTable = (ExportTable) clazz.getAnnotation(ExportTable.class);
        if(exportTable!=null){
            DataResult dataResult = DbUtil.query(exportTable);
            Field[] fields = clazz.getDeclaredFields();
            List<ExportItem> exportItems = new ArrayList<>();
            for(Field field:fields){
                String fieldName = field.getName();
                ExportBean exportBean = field.getDeclaredAnnotation(ExportBean.class);
                String column = exportBean.column();
                String title = exportBean.title();
                int sort = exportBean.sort();
                int width = exportBean.width();
                ExportItem exportItem = new ExportItem(title,fieldName,column,sort,width);
                exportItems.add(exportItem);
            }
            Collections.sort(exportItems, Comparator.comparingInt(ExportItem::getSort));
            export(exportItems,dataResult);
        }
    }

    public SXSSFWorkbook export(List<ExportItem> items,DataResult result,ExportCallback exportCallback){
        List<Map<String,Object>> resultItems = result.getItems();
        sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxssfWorkbook.createSheet();
        SXSSFRow row;
        if(this.exportConfig.getShowTitleFlag()){
            row = sheet.createRow(nextRow());
            for(int i=0;i<items.size();i++){
                ExportItem item = items.get(i);
                int width = item.getWidth();
                sheet.setColumnWidth(i, (int)((width + 0.72) * 256));
                String title = exportCallback.getTitle(item);
                SXSSFCell cell = row.createCell(i);
                cell.setCellValue(title);
            }
        }
        CellStyle cellStyle = sxssfWorkbook.createCellStyle();
        DataFormat dateFormat = sxssfWorkbook.createDataFormat();
        cellStyle.setDataFormat(dateFormat.getFormat("yyyy-MM-dd HH:mm"));
        CellStyle textStyle = sxssfWorkbook.createCellStyle();
        for(Map<String,Object> item:resultItems){
            row = sheet.createRow(nextRow());
            for(int i=0;i<items.size();i++){
                ExportItem ei = items.get(i);
                String column = ei.getColumn();
                if(column==null || "".equals(column)){
                    column = ei.getFieldName();
                }
                column = column.toLowerCase();
                Object value = exportCallback.getValue(column,item);
                SXSSFCell cell = row.createCell(i);
                if(value instanceof Date){
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue((Date) value);
                }else{
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(value+"");
                }
            }
        }
        return sxssfWorkbook;
    }

    public SXSSFWorkbook export(List<ExportItem> items,DataResult result) {
        return export(items,result,new DefaultExportCallback());
    }
}
