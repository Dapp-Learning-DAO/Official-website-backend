package com.dl.officialsite.hiring.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ApplicationRepository extends JpaRepository<Application, Long>,
        JpaSpecificationExecutor<Application> {

    Application findByMemberIdAndHiringId(Long memberId, Long hireId);

    @Modifying
    @Query(value ="delete from application where memeber_id = :member_id", nativeQuery = true)
    void deleteByMemberId(@Param("member_id")Long memberId);
}
