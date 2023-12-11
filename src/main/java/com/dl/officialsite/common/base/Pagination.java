package com.dl.officialsite.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    /**
     * 总数据条目
     */
    private int totalCount;

    /**
     * 总共页数
     */
    private int totalPages;

    /**
     * 当前页码
     */
    private int currentPage;

    /**
     * 当前页面包含条目数
     */
    private int currentPageSize;

    /**
     * 是否还有下一页
     */
    private boolean hasNext;
}
