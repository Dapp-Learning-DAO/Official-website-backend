package com.dl.officialsite.defi.dao;

import com.dl.officialsite.defi.entity.WhaleTxRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName WhaleTxRowRepository
 * @Author jackchen
 * @Date 2024/4/4 12:46
 * @Description WhaleTxRowRepository
 **/
public interface WhaleTxRowRepository extends JpaRepository<WhaleTxRow, Long>,
    JpaSpecificationExecutor<WhaleTxRow> {


    @Query(value = "select * from whale_tx_row order by create_time desc limit 1", nativeQuery = true)
    WhaleTxRow findLastWhaleTxRow();

    @Query(value = "select * from whale_tx_row order by id DESC limit 1", nativeQuery = true)
    WhaleTxRow findAgoWhaleTxRow();
}
