package com.dl.officialsite.hiring.application;

import com.dl.officialsite.hiring.Hiring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ApplicationRepository extends JpaRepository<Application, Long>,
    JpaSpecificationExecutor<Application> {

}
