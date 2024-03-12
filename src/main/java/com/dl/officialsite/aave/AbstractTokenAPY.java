package com.dl.officialsite.aave;

import com.dl.officialsite.config.ChainInfo;
import java.util.List;

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
    public abstract List<TokenAPYInfo> queryTokenApy(TokenAPYInfoQuery query);

    public abstract HealthInfo getHealthInfo(ChainInfo chainInfo, String address);


}
