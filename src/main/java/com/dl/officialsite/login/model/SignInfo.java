package com.dl.officialsite.login.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class SignInfo {
    @NotNull
    private String  address;
    @NotNull
    private String v;
    @NotNull
    private String r;
    @NotNull
    private String s;
    @NotNull
    private String message;
}
