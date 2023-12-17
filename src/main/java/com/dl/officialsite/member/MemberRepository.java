package com.dl.officialsite.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor <Member>{

    @Query(value = "select * from member where address = :address", nativeQuery = true)
    Optional<Member> findByAddress(@Param("address") String  address);

    List<Member> findByIdIn(List<Long> ids);
}
