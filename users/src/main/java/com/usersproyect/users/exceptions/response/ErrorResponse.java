package com.usersproyect.users.exceptions.response;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private LocalDateTime errorDate;
    private String error;
    private String message;
    private String path;


    public ErrorResponse(int status, LocalDateTime errorDate, String error, String message, String path) {
        this.status = status;
        this.errorDate = errorDate;
        this.error = error;
        this.message = message;
        this.path = path;
    }


    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public LocalDateTime getErrorDate() {
        return errorDate;
    }


    public void setErrorDate(LocalDateTime errorDate) {
        this.errorDate = errorDate;
    }


    public String getError() {
        return error;
    }


    public void setError(String error) {
        this.error = error;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }


}
