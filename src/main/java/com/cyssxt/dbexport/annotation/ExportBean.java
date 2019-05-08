package com.cyssxt.dbexport.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExportBean {
    String column() default "";
    String value() default "";
    int sort() default 0;
    String title() default "";
    int width() default 50;
}
