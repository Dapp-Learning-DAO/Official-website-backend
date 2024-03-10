package com.dl.officialsite.aave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName TokenAPYInfoRepository
 * @Author jackchen
 * @Date 2024/3/9 16:39
 * @Description TokenAPYInfoRepository
 **/
public interface TokenAPYInfoRepository extends JpaRepository<TokenAPYInfo, Long>,
    JpaSpecificationExecutor<TokenAPYInfo> {

}
