package com.dl.officialsite.reward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RewardRepository extends JpaRepository<Reward, String>,  JpaSpecificationExecutor <Reward> {



}

