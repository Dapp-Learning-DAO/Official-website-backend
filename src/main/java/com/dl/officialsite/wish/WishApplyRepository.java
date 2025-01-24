package com.dl.officialsite.wish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName WishLikeRepository
 * @Author jackchen
 * @Date 2025/1/2 23:16
 * @Description WishLikeRepository
 **/
public interface WishApplyRepository extends JpaRepository<WishApply, Long>,
    JpaSpecificationExecutor<WishApply> {

}
