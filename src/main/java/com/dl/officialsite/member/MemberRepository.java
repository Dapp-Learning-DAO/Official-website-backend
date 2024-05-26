package com.dl.officialsite.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

    @Query(value = "select * from member where address = :address", nativeQuery = true)
    Optional<Member> findByAddress(@Param("address") String address);

    @Query(value = "select * from member where nick_name = :nickName", nativeQuery = true)
    Optional<Member> findByNickName(@Param("nickName") String nickName);

    List<Member> findByIdIn(List<Long> ids);

    @Modifying
    @javax.transaction.Transactional
    @Query(value = "update member set github_id=NULL, discord_id=NULL, telegram_user_id=NULL where address = :address", nativeQuery =
        true)
    void removeGitHubTgAndDiscordId(@Param("address") String address);
}
