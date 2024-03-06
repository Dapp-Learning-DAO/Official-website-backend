package com.dl.officialsite.aave;

/**
 * @ClassName AbstractTokenApy
 * @Author jackchen
 * @Date 2024/3/6 20:30
 * @Description AbstractTokenApy
 **/
public abstract class AbstractTokenAPY {

    /**
     * get token apy group by defi provider
     */
    public abstract TokenAPYInfo queryTokenApy(TokenAPYInfo tokenAPYInfo);


}
