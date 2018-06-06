package com.sch.library.base;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
public class Result {
    public Result(int status,String message){
        this.status = status;
        this.message = message;
    }
    @Getter
    private Integer status;
    @Getter
    private String message;
    @Getter
    @Setter
    private Object data=null;


}
