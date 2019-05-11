package com.cyssxt;

import com.cyssxt.dbexport.core.ExportHelper;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.sql.SQLException;

public class test {

    @Test
    public void test() throws ClassNotFoundException, SQLException, IntrospectionException, IOException {
        ExportHelper exportHelper = ExportHelper.Builder.create();
        exportHelper.start(TestBean.class,"./export.xlsx");
    }
}
