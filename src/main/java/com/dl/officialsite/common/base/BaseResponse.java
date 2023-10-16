package com.dl.officialsite.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    public static final String SUCCESS_CODE = "0";

    private String code;

    private String msg;

    private T data;

    public static <T> BaseResponse successWithData(T data){
        return new BaseResponse(SUCCESS_CODE, null, data);
    }

    public static <T> BaseResponse failWithReason(String code, String msg){
        return new BaseResponse(code, msg, null);
    }
}
