package com.example.filexplorer.dto;

public class ServerResponseDto {
    private boolean success = false;
    private String message;
    private Object data;

    private String responseType;

    public ServerResponseDto() {
    }

    public ServerResponseDto(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.responseType = "message";
    }

    public ServerResponseDto(boolean success, String message, Object data, String responseType) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.responseType = responseType;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
