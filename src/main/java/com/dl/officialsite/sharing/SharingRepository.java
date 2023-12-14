package com.dl.officialsite.sharing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharingRepository extends JpaRepository<Share, Long>, JpaSpecificationExecutor<Share> {

    @Query(value = "select * from share limit :offset, :limit", nativeQuery = true)
    List<Share> findAllSharesPaged(@Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "select count(*) from share", nativeQuery = true)
    int loadAllCount();

    @Query(value = "select * from share where member_id = :memberId limit :offset, :limit", nativeQuery = true)
    List<Share> findAllSharesByUidPaged(@Param("memberId") long memberId, @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "select count(*) from share where member_id = :memberId", nativeQuery = true)
    int loadCountByUid(@Param("memberId") long memberId);


}
