package com.dl.officialsite.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedList<T>{

    private List<T> data;

    private Pagination pagination;
}
