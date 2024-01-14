package com.dl.officialsite.hiring.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ApplicationRepository extends JpaRepository<Application, Long>,
    JpaSpecificationExecutor<Application> {

    Application findByMemberIdAndHiringId(Long memberId, Long hireId);
}
