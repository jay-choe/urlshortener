package com.shortener.common.response;

public class ApiResponse<T> {

    private String code;
    private T data;

    private ApiResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ApiResponse of(String code, T data) {
        return new ApiResponse(code, data);
    }
}
