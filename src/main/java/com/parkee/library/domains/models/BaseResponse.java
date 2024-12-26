package com.parkee.library.domains.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private String message;
    private Integer code;
    private T data;

    public static <R> BaseResponse<R> setSuccess(R data) {
        BaseResponse<R> baseResponse = new BaseResponse<>();
        baseResponse.setCode(200);
        baseResponse.setMessage("SUCCESS");
        baseResponse.setData(data);
        return baseResponse;
    }

    public static <R> BaseResponse<R> setFailed(R data, int code) {
        BaseResponse<R> baseResponse = new BaseResponse<>();
        baseResponse.setCode(code);
        baseResponse.setMessage("FAILED");
        baseResponse.setData(data);
        return baseResponse;
    }
}
