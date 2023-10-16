package com.dl.officialsite.common.base;

import com.dl.officialsite.common.enums.CodeEnums;
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
        BaseResponse<T> br = new BaseResponse<>();
        br.setCode(CodeEnums.SUCCESSFUL.getCode());
        br.setMsg(CodeEnums.SUCCESSFUL.getMsg());
        br.setData(data);
        return br;
    }

    public static <T> BaseResponse failWithReason(String code, String msg){
        return new BaseResponse(code, msg, null);
    }
}
