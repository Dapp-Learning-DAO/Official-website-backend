package com.dl.officialsite.nft.bean;

import com.dl.officialsite.nft.constant.ContractNameEnum;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "member_nft_mint_record", schema = "dl", uniqueConstraints = {
    @UniqueConstraint(name = "unique_mint_record", columnNames = {
        "address", "contractName", "chainId"
    })
})
public class MemberNFTMintRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 42)
    @NotNull
    private String address;

    @NotNull
    @Column(length = 16)
    @Enumerated(EnumType.STRING)
    private ContractNameEnum contractName;

    @Column(length = 32)
    @NotNull
    private String chainId;

    @ColumnDefault("-1")
    private int tokenId = -1;

    @ColumnDefault("-1")
    private int rankValue = -1;

    @CreatedDate
    @Column(updatable = false)
    private Long mintTime;
}
