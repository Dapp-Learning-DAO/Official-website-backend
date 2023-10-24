package com.dl.officialsite.redpacket;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RedPacketVo implements Serializable {

    private String  name;
    private String id;
    private String creator;
    private Long expireTime;
    private Long createTime;
    private String chainId;
    private  Integer status;
    private Long amount;
    private List<String> address;

    //contractAddress???
}
