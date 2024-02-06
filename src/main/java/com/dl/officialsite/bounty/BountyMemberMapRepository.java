package com.dl.officialsite.bounty;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName BountyMemberMapRepository
 * @Author jackchen
 * @Date 2024/2/3 20:07
 * @Description BountyMemberMapRepository
 **/
public interface BountyMemberMapRepository extends JpaRepository<BountyMemberMap, Long>,
    JpaSpecificationExecutor<BountyMemberMap> {


    Optional<BountyMemberMap> findByBountyIdAndMemberAddress(Long bountyId, String memberAddress);
}
