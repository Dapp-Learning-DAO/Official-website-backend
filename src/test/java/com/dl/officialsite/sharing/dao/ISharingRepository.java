package com.dl.officialsite.sharing.dao;

import com.dl.officialsite.sharing.model.db.SharingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISharingRepository extends JpaRepository<SharingEntity, Long> {

    @Query(value = "select member_id from team_member where team_id = :team_id", nativeQuery = true)
    List<Long> findByTeamId(@Param("team_id")Long teamId);

    @Query(value = "select member_id from team_member where team_id = :team_id and status = :status",
            nativeQuery =
                    true)
    List<Long> findByTeamIdStatus(@Param("team_id")Long teamId, @Param("status")int status);
}
