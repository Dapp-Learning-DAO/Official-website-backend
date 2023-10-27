package com.dl.officialsite.login.model;

import com.dl.officialsite.login.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipleData {

    private String address;

    private UserRoleEnum userRole;
}
