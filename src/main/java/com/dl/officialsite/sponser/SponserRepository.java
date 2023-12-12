package com.dl.officialsite.sponser;

import com.dl.officialsite.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SponserRepository extends JpaRepository<Sponsor, Long>, JpaSpecificationExecutor <Sponsor>{
    //Optional<Sponser> findByAddress(@Param("address") String  address);
}
