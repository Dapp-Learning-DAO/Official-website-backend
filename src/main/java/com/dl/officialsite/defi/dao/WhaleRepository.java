package com.dl.officialsite.defi.dao;

import com.dl.officialsite.defi.entity.Whale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @ClassName WhaleRepository
 * @Author jackchen
 * @Date 2024/4/4 12:45
 * @Description WhaleRepository
 **/
public interface WhaleRepository extends JpaRepository<Whale, Long>, JpaSpecificationExecutor<Whale> {

    @Query(value = "select * from whale where address = ?1", nativeQuery = true)
    Whale findByAddress(@Param("address")String address);

    @Query(value = "select * from whale order by id DESC limit 1", nativeQuery = true)
    Whale findByAgoWhale();

    @Query(value = "select * from whale order by id DESC limit 1", nativeQuery = true)
    Whale findLastWhale();
}
