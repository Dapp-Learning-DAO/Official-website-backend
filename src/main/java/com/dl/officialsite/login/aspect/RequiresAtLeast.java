package com.dl.officialsite.login.aspect;

import com.dl.officialsite.login.enums.UserRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresAtLeast {

    /**
     * 操作类型
     *
     * @return
     */
    UserRoleEnum value() default UserRoleEnum.NORMAL;
}