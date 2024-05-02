package com.dl.officialsite.config.bean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServerConfigRepository extends JpaRepository<ServerConfig, Long>, JpaSpecificationExecutor<ServerConfig> {
    @Query(value = "select * from server_config where config_name=:configName", nativeQuery = true)
    Optional<ServerConfig> findOneByConfigName(@Param("configName") String configName);

}
