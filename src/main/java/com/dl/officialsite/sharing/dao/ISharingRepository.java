package com.dl.officialsite.sharing.dao;

import com.dl.officialsite.sharing.model.db.TbShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISharingRepository extends JpaRepository<TbShare, Long> {

    @Query(value = "select * from tb_share limit :offset, :limit", nativeQuery = true)
    List<TbShare> findAllSharesPaged(@Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "select count(*) from tb_share", nativeQuery = true)
    int loadAllCount();

    @Query(value = "select * from tb_share where member_id = :memberId limit :offset, :limit", nativeQuery = true)
    List<TbShare> findAllSharesByUidPaged(@Param("memberId") long memberId, @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "select count(*) from tb_share where member_id = :memberId", nativeQuery = true)
    int loadCountByUid(@Param("memberId") long memberId);


}
