package com.dl.officialsite.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponse<T> {

    public static final String SUCCESS_CODE = "0";

    private String code;

    private String msg;

    private T data;

    public static <T> ServerResponse successWithData(T data){
        return new ServerResponse(SUCCESS_CODE, null, data);
    }

    public static <T> ServerResponse failWithReason(String code, String msg){
        return new ServerResponse(code, msg, null);
    }
}
