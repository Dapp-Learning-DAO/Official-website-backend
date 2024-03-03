package com.dl.officialsite.sharing.model.bo;

import java.math.BigInteger;
import lombok.Data;

/**
 * @ClassName RankDto
 * @Author jackchen
 * @Date 2024/3/2 19:27
 * @Description RankDto
 **/
@Data
public class RankDto {

    private String presenter;

    private BigInteger shareCount;
}
