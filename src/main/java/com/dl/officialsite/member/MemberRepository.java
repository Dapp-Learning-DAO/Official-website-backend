package com.dl.officialsite.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByAddress(@Param("address") String  address);
}
