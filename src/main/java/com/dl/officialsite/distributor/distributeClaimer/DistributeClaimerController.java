package com.dl.officialsite.distributor.distributeClaimer;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.distributor.DistributeController;
import com.dl.officialsite.distributor.DistributeService;
import com.dl.officialsite.distributor.vo.AddDistributeClaimerReqVo;
import com.dl.officialsite.distributor.vo.DistributeInfoVo;
import com.dl.officialsite.distributor.vo.GetDistributeByPageReqVo;
import com.dl.officialsite.distributor.vo.GetDistributeClaimerByPageReqVo;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.member.MemberService;
import com.dl.officialsite.redpacket.RedPacket;

import lombok.extern.slf4j.Slf4j;

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

@Slf4j
@RestController
@RequestMapping("/distribute-claimer")
public class DistributeClaimerController {

    @Autowired
    private DistributeClaimerService distributeClaimerService;

    @PostMapping("/add")
    public BaseResponse addDistributeClaimer(@Valid @RequestBody AddDistributeClaimerReqVo param) {
        distributeClaimerService.saveClaimer(param);
        return BaseResponse.success();
    }

    @DeleteMapping("/{id}")
    public BaseResponse deletDistributeClaimer(@PathVariable("id") Long id) {
        distributeClaimerService.deleteDistributeClaimer(id);
        return BaseResponse.success();
    }

    @GetMapping(value = "/page")
    BaseResponse getDistributeByPage(@RequestParam GetDistributeClaimerByPageReqVo param) {
        return BaseResponse.successWithData(distributeClaimerService.queryDistributeClaimerByPage(param));
    }

}