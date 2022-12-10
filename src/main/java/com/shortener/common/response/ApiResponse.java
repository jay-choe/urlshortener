package com.shortener.common.response;

import com.shortener.common.enums.ApiResponseCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private String code;
    private T data;

    private ApiResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ApiResponse of(ApiResponseCode responseCode, T data) {
        return new ApiResponse(responseCode.name(), data);
    }
}
