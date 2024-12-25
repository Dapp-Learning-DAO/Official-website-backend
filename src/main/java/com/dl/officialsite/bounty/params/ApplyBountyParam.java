package com.dl.officialsite.bounty.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @ClassName ApplyBountyParam
 * @Author jackchen
 * @Date 2024/12/25 15:54
 * @Description ApplyBountyParam
 **/
@Data
public class ApplyBountyParam {

    @NotNull(message = "bountyId not null")
    private Long bountyId;

    @NotBlank(message = "address not blank")
    private String address;

    @NotBlank(message = "contractAddress not blank")
    private String contractAddress;

    @NotBlank(message = "introduction not blank")
    private String introduction;

}
