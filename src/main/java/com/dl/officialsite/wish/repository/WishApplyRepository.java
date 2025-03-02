package com.dl.officialsite.wish.repository;

import com.dl.officialsite.wish.domain.WishApply;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName WishLikeRepository
 * @Author jackchen
 * @Date 2025/1/2 23:16
 * @Description WishLikeRepository
 **/
public interface WishApplyRepository extends JpaRepository<WishApply, Long>,
    JpaSpecificationExecutor<WishApply> {


    @Query(value = "select * from wish_apply where wish_id = ?1 and chain_id = ?2", nativeQuery = true)
    List<WishApply> findByWishIdAndChainId(Long wishId, String chainId);

}
