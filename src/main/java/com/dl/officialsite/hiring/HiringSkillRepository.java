package com.dl.officialsite.hiring;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName HiringSkillRepository
 * @Author jackchen
 * @Date 2023/12/7 00:36
 * @Description HiringSkillRepository
 **/
public interface HiringSkillRepository extends JpaRepository<HiringSkill, Long> {

    @Query(value = "select * from hiring_skill where hiring_id = :hiring_id",nativeQuery = true)
    List<HiringSkill> findByHiringId(Long hiring_id);
}
