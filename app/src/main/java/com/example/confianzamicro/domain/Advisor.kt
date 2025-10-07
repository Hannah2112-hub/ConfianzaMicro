package com.example.confianzamicro.domain;

public class Advisor {
    private final String code;
    private final String fullName;

    public Advisor(String code, String fullName){
        this.code = code;
        this.fullName = fullName;
    }

    public String getCode(){
        return code;
    }

    public String getFullName(){
        return fullName;
    }
}
