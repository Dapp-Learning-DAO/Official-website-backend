package com.dl.officialsite.bounty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName BountyRepository
 * @Author jackchen
 * @Date 2024/1/28 11:17
 * @Description BountyRepository
 **/
public interface BountyRepository extends JpaRepository<Bounty, Long>,
    JpaSpecificationExecutor<Bounty> {

}
