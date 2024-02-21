package com.dl.officialsite.distributor.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddDistributeClaimerReqVo {
    private Long distributeId;
    private List<NewClaimerInfo> claimerList;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class NewClaimerInfo {
        private String claimer;
        private BigDecimal amount;
    }
}
