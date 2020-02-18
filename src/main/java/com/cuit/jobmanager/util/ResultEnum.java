package com.cuit.jobmanager.util;

public enum  ResultEnum {
    SUCCESS(200,"success"),
    FAIL(500,"system error"),

    FROZEN(10001,"FROZEN"),
    UNFROZEN(10002,"UNFROZEN"),

    SAVE_FAIL(12222 , "SAVE FAIL"),

    UPDATE_FAIL(111111, "UPDATE FAIL"),
    DELETE_JOB_SUCCESS(11001, "DELETE JOB SUCCESS"),
    DELETE_JOB_FAIL(11000, "DELETE JOB ERROR");


    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
