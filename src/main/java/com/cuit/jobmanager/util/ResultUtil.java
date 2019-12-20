package com.cuit.jobmanager.util;


public class ResultUtil {

    public static Result success(Object o){
        Result result = new Result(ResultEnum.SUCCESS, o);
        return result;
    }

    public static Result fail(){
        Result result = new Result(ResultEnum.FAIL);
        return result;
    }

    public static Result fail(ResultEnum resultEnum){
        Result result = new Result(resultEnum);
        return result;
    }
}

