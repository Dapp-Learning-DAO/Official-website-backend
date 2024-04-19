package com.dl.officialsite.activity.bean;

import com.dl.officialsite.activity.constant.TaskTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MemberTaskRecordRepository extends JpaRepository<MemberTaskRecord, Long>, JpaSpecificationExecutor<MemberTaskRecord> {

    List<MemberTaskRecord> findActivityRecordsByAddress(String activityName, String address);

    Optional<MemberTaskRecord> findOne(String activityName, String address, TaskTypeEnum taskType, String target);

}
