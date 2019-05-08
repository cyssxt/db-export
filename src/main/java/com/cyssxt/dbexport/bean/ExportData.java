package com.cyssxt.dbexport.bean;

import lombok.Data;

import java.util.List;

@Data
public class ExportData {
    List<String> fields;
    List<ExportItem> exportItems;
}
