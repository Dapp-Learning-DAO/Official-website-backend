package com.dl.officialsite.hiring;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName HiringSkillRepository
 * @Author jackchen
 * @Date 2023/12/7 00:36
 * @Description HiringSkillRepository
 **/
@Transactional
public interface HiringSkillRepository extends JpaRepository<HiringSkill, Long> {

    @Query(value = "select * from hiring_skill where hiring_id = :hiring_id",nativeQuery = true)
    List<HiringSkill> findByHiringId(@Param("hiring_id")Long hiring_id);

    @Query(value = "select * from hiring_skill where hiring_id in (:hiring_ids)",nativeQuery = true)
    List<HiringSkill> findByHiringId(@Param("hiring_ids") List<Long> hiring_ids);

    @Query(value = "select * from hiring_skill where skill in (:skills)",nativeQuery = true)
    List<HiringSkill> findBySkill(@Param("skills")List<String> skills);

    @Query(value = "delete from hiring_skill where hiring_id = :hiring_id",nativeQuery = true)
    @Modifying
    void deleteByHiringId(@Param("hiring_id")Long hiring_id);
}
