package com.dl.officialsite.defi.service;

import com.dl.officialsite.defi.dao.WhaleProtocolRepository;
import com.dl.officialsite.defi.entity.WhaleProtocol;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @ClassName WhaleProtocolService
 * @Author jackchen
 * @Date 2024/4/14 18:21
 * @Description WhaleProtocolService
 **/
@Service
@Slf4j
public class WhaleProtocolService {

    private final WhaleProtocolRepository whaleProtocolRepository;

    public WhaleProtocolService(WhaleProtocolRepository whaleProtocolRepository) {
        this.whaleProtocolRepository = whaleProtocolRepository;
    }

    public Page<WhaleProtocol> queryWhaleProtocol(String address, Pageable pageable) {
        Specification<WhaleProtocol> queryParam = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(address)) {
                predicates.add(criteriaBuilder.equal(root.get("whaleAddress"), address));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return whaleProtocolRepository.findAll(queryParam, pageable);
    }
}

