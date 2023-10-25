package com.dl.officialsite.team;

import com.dl.officialsite.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @ClassName TeamMemberRepository
 * @Author jackchen
 * @Date 2023/10/21 17:23
 * @Description TeamMemberRepository
 **/
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    @Query(value = "select member_id from team_member where team_id = :team_id", nativeQuery = true)
    List<Long> findByTeamId(@Param("team_id")Long teamId);

    @Query(value = "select * from team_member where team_id = :team_id and member_id = :member_id",
        nativeQuery = true)
    Optional<TeamMember> findByTeamAndMember(@Param("team_id")Long teamId, @Param("member_id")Long memberId);

    @Query(value = "select member_id from team_member where team_id = :team_id and status = "
        + ":status",
        nativeQuery = true)
    List<Long> findByTeamIdAndStatus(@Param("team_id")Long teamId, @Param("status")int requestTeam);

    @Query(value = "select role from team_member where member_id = :member_id")
    List<Integer> findAuthRolesByMemberId( @Param("member_id")Long memberId);
}
