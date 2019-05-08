package com.cyssxt.dbexport.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExportTable {
    String name() default "";
    String value() default "";
    String tableName() default "";
    String ip() default "";
    String url() default "";
    String userName() default "";
    String password() default "";
}
