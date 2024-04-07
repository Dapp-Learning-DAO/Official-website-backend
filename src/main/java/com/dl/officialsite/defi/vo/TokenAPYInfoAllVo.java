package com.dl.officialsite.defi.vo;

import java.util.List;
import lombok.Data;

/**
 * @ClassName TokenAPYInfoAllVo
 * @Author jackchen
 * @Date 2024/3/13 22:43
 * @Description TokenAPYInfoAllVo
 **/
@Data
public class TokenAPYInfoAllVo {

    private String tokenName;

    private List<ChainAndAPYVo> chainAllAPY;

}
