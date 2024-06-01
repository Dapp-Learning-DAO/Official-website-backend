package com.dl.officialsite.defi.dao;

import com.dl.officialsite.defi.entity.WhaleProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName WhaleProtocolRepository
 * @Author jackchen
 * @Date 2024/4/14 18:19
 * @Description WhaleProtocolRepository
 **/
public interface WhaleProtocolRepository extends JpaRepository<WhaleProtocol, Long>,
    JpaSpecificationExecutor<WhaleProtocol> {

    @Query(value = "select * from whale_protocol order by id DESC limit 1", nativeQuery = true)
    WhaleProtocol findAgoWhaleTxRow();
}
