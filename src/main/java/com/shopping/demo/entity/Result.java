package com.shopping.demo.entity;

public class Result<T> {
    private Integer code;
    private String message;
    private Object data;

    public Result() {}

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(Object data) {
        return new Result<T>(200, "success", data);
    }

    public static <T> Result<T> success(Object data, String message) {
        return new Result<T>(200, message, data);
    }

    public static <T>Result<T> error(String message) {
        return new Result<T>(500, message, null);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<T>(code, message, null);
    }

    // Getters and Setters
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}