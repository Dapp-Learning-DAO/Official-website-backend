package com.dl.officialsite.defi.dao;

import com.dl.officialsite.defi.entity.WhaleChainToken;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName whaleChainTokenRepository
 * @Author jackchen
 * @Date 2024/4/15 22:52
 * @Description TODO
 **/
public interface WhaleChainTokenRepository extends JpaRepository<WhaleChainToken, Long>,
    JpaSpecificationExecutor<WhaleChainToken> {

    List<WhaleChainToken> findByWhaleAddress(String whaleAddress);

    @Query(value = "select * from whale_chain_token order by id DESC limit 1", nativeQuery = true)
    WhaleChainToken findAgoWhaleChainToken();
}
