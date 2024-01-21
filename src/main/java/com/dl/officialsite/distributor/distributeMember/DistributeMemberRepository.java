package com.dl.officialsite.distributor.distributeMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DistributeMemberRepository
        extends JpaRepository<DistributeMemberInfo, String>, JpaSpecificationExecutor<DistributeMemberInfo> {

}
