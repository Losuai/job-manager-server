package com.cuit.jobmanager.util;

import lombok.Data;


@Data
public class Result<T> {
    private Integer code;

    private String msg;

    private T data;

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public Result(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = data;
    }

    public Result(Integer code,  String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


}
