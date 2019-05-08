package com.cyssxt.dbexport.core;

import com.cyssxt.dbexport.annotation.ExportTable;
import com.cyssxt.dbexport.bean.DataResult;
import lombok.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DbUtil {

    public static DataResult query(ExportTable exportTable) throws ClassNotFoundException, SQLException {
        DataResult dataResult = new DataResult();
        String url = exportTable.url();
        String userName = exportTable.userName();
        String password = exportTable.password();
        String tableName = exportTable.tableName();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(url, userName, password);
            String sql = String.format("select * from %s", tableName);
            ptmt = connection.prepareStatement(sql);
            rs = ptmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            List<String> fieldList = new ArrayList<>();
            int columnCount = rsmd.getColumnCount();
            if (columnCount > 0) {
                for (int i = 1; i <= columnCount; i++) {
                    fieldList.add(rsmd.getColumnName(i));
                }
            }
            dataResult.setFields(fieldList);
            List<Map<String, Object>> result = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                for (String field : fieldList) {
                    item.put(getKeyName(field), rs.getObject(field));
                }
                result.add(item);
            }
            dataResult.setItems(result);
            return dataResult;
        }catch (Exception e){
            throw new SQLException();
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ptmt!=null){
                try {
                    ptmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static String getKeyName(String key){
        return key.replace("_","").toLowerCase();
    }
}
