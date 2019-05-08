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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExportHelper {

    public static void start(Class clazz) throws SQLException, ClassNotFoundException, IntrospectionException, IOException {
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

    public static void export(List<ExportItem> items,DataResult result) throws IOException {
        List<Map<String,Object>> resultItems = result.getItems();
        SXSSFWorkbook hssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = hssfWorkbook.createSheet();
        int rowNum = 0;
        SXSSFRow row = sheet.createRow(rowNum);
        for(int i=0;i<items.size();i++){
            ExportItem item = items.get(i);
            int width = item.getWidth();
            sheet.setColumnWidth(i, (int)((width + 0.72) * 256));
            String title = item.getTitle();
            SXSSFCell cell = row.createCell(i);
            cell.setCellValue(title);
        }
        CellStyle cellStyle = hssfWorkbook.createCellStyle();
        DataFormat dateFormat = hssfWorkbook.createDataFormat();
        cellStyle.setDataFormat(dateFormat.getFormat("yyyy-MM-dd HH:mm"));
        CellStyle textStyle = hssfWorkbook.createCellStyle();
        for(Map<String,Object> item:resultItems){
            rowNum++;
            row = sheet.createRow(rowNum);
            for(int i=0;i<items.size();i++){
                ExportItem ei = items.get(i);
                String column = ei.getColumn();
                if(column==null || "".equals(column)){
                    column = ei.getFieldName();
                }
                column = column.toLowerCase();
                Object value = item.get(column);
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
        File file = new File("export.xlsx");
        System.out.println(file.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(file);
//        System.out.println(fos);
        hssfWorkbook.write(fos);
        hssfWorkbook.close();
    }
}
