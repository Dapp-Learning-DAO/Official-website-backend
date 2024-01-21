package com.dl.officialsite.distributor;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.member.MemberService;
import com.dl.officialsite.redpacket.RedPacket;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/distribute")
public class DistributeController {

    @Autowired
    private DistributeService distributeService;

    public static final Logger logger = LoggerFactory.getLogger(DistributeController.class);

    @PostMapping("/create")
    public BaseResponse createDistributor(@Valid @RequestBody DistributeInfo param) {
        DistributeInfo distribute = distributeService.createDistribute(param);
        return BaseResponse.successWithData(distribute);

    }

}