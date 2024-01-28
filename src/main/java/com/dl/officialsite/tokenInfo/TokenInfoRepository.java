package com.dl.officialsite.tokenInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TokenInfoRepository
        extends JpaRepository<TokenInfo, Long>, JpaSpecificationExecutor<TokenInfo> {


}
