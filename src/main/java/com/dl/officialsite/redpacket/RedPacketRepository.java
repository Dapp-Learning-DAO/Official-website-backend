package com.dl.officialsite.redpacket;

import com.dl.officialsite.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RedPacketRepository extends JpaRepository<RedPacket, String>,  JpaSpecificationExecutor <RedPacket> {


    @Query(value = "select * from red_packet where address_list like ?1", nativeQuery = true)
    List<RedPacket> findByAddress(@Param("address") String address);

    @Query(value = "select * from red_packet where address_list like ?1 and  status = ?2 ", nativeQuery = true)
    List<RedPacket> findByAddressAndStatus(@Param("address") String address, @Param("status") Integer status);


    @Query(value = "select * from red_packet where claimed_address like ?1 ", nativeQuery = true)
    List<RedPacket> findByClaimedPacket(@Param("address") String address);



    @Query(value = "select * from red_packet where address_list like ?1 and  claimed_address not like ?1 ", nativeQuery = true)
    List<RedPacket> findByUnclaimedPacket(@Param("address") String address);


    RedPacket findByIdAndStatus(@Param("id") String id,  @Param("status") Integer status);

    List<RedPacket> findByStatus(@Param("status") Integer status);

}

