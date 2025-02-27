package com.mohil_bansal.assignment.feign.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> {
    private boolean success;
    private int statusCode;
    private T data;
    private String message;

    public CommonResponse() {}

    public CommonResponse(boolean success, int statusCode, T data, String message) {
        this.success = success;
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
    }

    public static <T> CommonResponse<T> success(T data, int statusCode, String message) {
        return new CommonResponse<>(true, statusCode, data, message);
    }

    public static <T> CommonResponse<T> failure(String message, int statusCode) {
        return new CommonResponse<>(false, statusCode, null, message);
    }

}
