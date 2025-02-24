package com.dl.officialsite.wish.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

/**
 * @ClassName WishLike
 * @Author jackchen
 * @Date 2025/2/24 21:39
 * @Description WishLike
 **/
@Data
@Entity
@Table(name = "wish_like")
public class WishLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long wishId;

    private Long memberId;

    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
}
