package com.dl.officialsite.redpacket;


import com.dl.officialsite.common.converter.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RedPacketVo {

    private String  name;
    private String id;
    private String creator;
    private Long expireTime;
    private Long createTime;
    private String chainId;
    private  int statue;
    private Long amount;
    //contractAddress???
}
