package com.sch.library;

import lombok.Getter;

public class Success {
    @Getter
    private int success;
    public static final Success SUCCESS = new Success(1);
    public static final Success UNSUCCESS = new Success(0);
    private Success(int success){
        this.success = success;
    }
}
