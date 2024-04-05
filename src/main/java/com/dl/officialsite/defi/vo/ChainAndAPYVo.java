package com.dl.officialsite.defi.vo;

import lombok.Data;

/**
 * @ClassName ChainAndAPYVo
 * @Author jackchen
 * @Date 2024/3/13 22:45
 * @Description ChainAndAPYVo
 **/
@Data
public class ChainAndAPYVo {

    private String chainName;

    private Double supply;

    private Double borrow;

    private String tokenAddress;
}
