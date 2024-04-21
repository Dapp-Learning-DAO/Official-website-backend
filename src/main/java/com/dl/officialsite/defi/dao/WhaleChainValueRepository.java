package com.dl.officialsite.defi.dao;

import com.dl.officialsite.defi.entity.WhaleChainValue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName WhaleChainValueRepository
 * @Author jackchen
 * @Date 2024/4/15 22:27
 * @Description WhaleChainValueRepository
 **/
public interface WhaleChainValueRepository extends JpaRepository<WhaleChainValue, Long>,
    JpaSpecificationExecutor<WhaleChainValue> {

    @Query(value = "select * from whale_chain_value where whale_address = ?1", nativeQuery = true)
    List<WhaleChainValue> findByWhaleAddress(String whaleAddress);
}
