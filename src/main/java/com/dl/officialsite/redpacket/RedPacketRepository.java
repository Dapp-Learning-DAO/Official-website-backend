package com.dl.officialsite.redpacket;

import com.dl.officialsite.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RedPacketRepository extends JpaRepository<RedPacket, String>,  JpaSpecificationExecutor <RedPacket> {


}

