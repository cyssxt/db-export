package com.cyssxt.dbexport.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DataResult {
    List<String> fields;

    List<Map<String,Object>> items;

}
