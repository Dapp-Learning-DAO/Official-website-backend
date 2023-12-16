package com.dl.officialsite.team;

import com.dl.officialsite.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName TeamRepository
 * @Author jackchen
 * @Date 2023/10/21 17:22
 * @Description TeamRepository
 **/
public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {





}
