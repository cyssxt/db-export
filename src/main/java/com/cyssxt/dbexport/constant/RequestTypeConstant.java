package com.cyssxt.dbexport.constant;


public enum RequestTypeConstant {
    CLASS((byte)0,"类导出"),
    JSON((byte)1,"json导出"),
    ;

    private Byte value;
    private String msg;

    RequestTypeConstant(Byte value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
