package com.dl.officialsite.hiring;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @ClassName HireRepository
 * @Author jackchen
 * @Date 2023/11/7 10:45
 * @Description 简历
 **/
public interface HireRepository extends JpaRepository<Hiring, Long>,
    JpaSpecificationExecutor<Hiring> {


    /**
     * 根据地址查询所有简历
     */
    @Query(value = "select * from hiring where address = :address", nativeQuery = true)
    List<Hiring> findAllByAddress(@Param("address")String address);
}
