package com.gutterboys.riichi.calculator.model;

public class GenericResponse {
    private String status = "200";
    private String message = "Request Completed Successfully!";

    public GenericResponse() {
    }

    public GenericResponse(String status, String message) {
        this.status = status;
        this.message = message;
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
