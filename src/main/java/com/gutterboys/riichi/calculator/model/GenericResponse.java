package com.gutterboys.riichi.calculator.model;

import java.util.HashSet;
import java.util.Set;

import com.gutterboys.riichi.calculator.yaku.common.CommonYaku;

public class GenericResponse {
    private String status = "200";
    private String message = "Request Completed Successfully!";
    private Set<CommonYaku> yakuList = new HashSet<CommonYaku>();

    public Set<CommonYaku> getYakuList() {
        return yakuList;
    }

    public GenericResponse() {
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
