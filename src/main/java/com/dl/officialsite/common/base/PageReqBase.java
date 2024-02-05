package com.dl.officialsite.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageReqBase {
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
}
