package com.dl.officialsite.reward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, String>,  JpaSpecificationExecutor <Reward> {


    List<Reward> findByStatus(Integer status);

}

