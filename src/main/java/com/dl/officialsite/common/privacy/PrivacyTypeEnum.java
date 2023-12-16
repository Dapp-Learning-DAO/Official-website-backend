package com.dl.officialsite.common.privacy;


import lombok.Getter;

@Getter
public enum PrivacyTypeEnum {

    /** 自定义（此项需设置脱敏的范围）,也可以设置脱敏使用的字符*/
    CUSTOMER,


    /** 邮箱 */
    EMAIL,
}
