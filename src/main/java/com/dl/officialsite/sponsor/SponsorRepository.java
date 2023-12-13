package com.dl.officialsite.sponsor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SponsorRepository extends JpaRepository<Sponsor, Long>, JpaSpecificationExecutor <Sponsor>{
    //Optional<Sponser> findByAddress(@Param("address") String  address);
}
