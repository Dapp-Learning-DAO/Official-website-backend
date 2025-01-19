package com.dl.officialsite.wish;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName WishRepository
 * @Author jackchen
 * @Date 2024/12/25 16:57
 * @Description WishRepository
 **/
public interface WishRepository extends JpaRepository<Wish, Long>,
    JpaSpecificationExecutor<Wish> {

    @Query(value = "select * from wish where   (status IN (0)   or status is null)   and chain_id = ?1  order by create_time desc", nativeQuery = true)
    List<Wish> findByChainId(String chainId);
}
