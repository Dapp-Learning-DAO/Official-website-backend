package com.dl.officialsite.wish;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @ClassName WishLike
 * @Author jackchen
 * @Date 2025/1/2 23:10
 * @Description like wish
 **/
@Data
@Entity
@Table(name = "wish_apply")
public class WishApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long wishId;

    private Long memberId;

    private String amount;

    private String tokenSymbol;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
