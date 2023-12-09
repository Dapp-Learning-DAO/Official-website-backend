package com.dl.officialsite.sharing.model.resp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PreCheckSharingRewardResp {

    private String chainName;

    private String wallet;

    private BigDecimal reward;

}
