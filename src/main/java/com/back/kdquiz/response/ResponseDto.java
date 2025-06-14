package com.back.kdquiz.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class ResponseDto <T> implements Serializable {
    private String code;
    private int status;
    private String message;
    private T data;

    public static <T> ResponseDto<T> setSuccess(String code, String message, T data){
        return new ResponseDto<>(code, 200, message, data);
    }

    public static <T> ResponseDto<T> setSuccess(String code, String message){
        return new ResponseDto<>(code, 200, message, null);
    }
    public static <T> ResponseDto<T> setFailed(String code, String message){
        return new ResponseDto<>(code, 500, message, null);
    }

    public static <T> ResponseDto<T> setFailedMessage(String code, String message, T data){
        return new ResponseDto<>(code,0,message, data);
    }
}
