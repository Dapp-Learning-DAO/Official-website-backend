package com.dl.officialsite.redpacket;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberController;
import com.dl.officialsite.member.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.dl.officialsite.common.converter.StringListConverter.SPLIT_CHAR;
import static org.hibernate.criterion.Restrictions.in;

@RestController
@RequestMapping("/red-packet")
public class RedPacketController {

    @Autowired
    private RedPacketRepository redPacketRepository;
    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);


    //todo add authority
    @PostMapping("/create")
    public BaseResponse createRedPacket(@RequestBody RedPacket redPacket, @RequestParam String address) {
        redPacket.setCreator(address);
        try {
            RedPacket redPacket1 = redPacketRepository.save(redPacket);
            return  BaseResponse.successWithData(redPacket1);
        } catch (Exception e) {
            return BaseResponse.failWithReason("1000",e.getMessage());
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    BaseResponse getMemberById(@RequestParam String id, @RequestParam String address)   {

        Optional<RedPacket> redPacket =  redPacketRepository.findById(id);
        if(!redPacket.isPresent()){
            return BaseResponse.failWithReason("1001", "no user found");
        }
        return BaseResponse.successWithData(redPacket.get());
    }


    @RequestMapping(value = "/query/all", method = RequestMethod.GET)
    BaseResponse getRedpacketAll(@RequestParam String address,
                              @RequestParam(defaultValue = "1") Integer pageNumber,
                              @RequestParam(defaultValue = "10") Integer pageSize)   {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        return BaseResponse.successWithData(redPacketRepository.findAll(pageable));
    }


    @RequestMapping(value = "/query/user", method = RequestMethod.GET)
    BaseResponse getRedpacketByAddress(@RequestParam String address, @RequestParam(required = false) Integer status) {
        List<RedPacket> result;
        if(status!= null) {
             result = redPacketRepository.findByAddressAndStatus("%" + address + "%", status);
        } else {
             result = redPacketRepository.findByAddress("%" + address + "%");

        }
        return BaseResponse.successWithData(result);
    }

// 更新红包状态，已认领过的地址。
    @PostMapping(value = "/query/all")
    BaseResponse getAllMemberByCriteria(@RequestParam String address,
                                        @RequestBody   RedPacketVo redPacket,
                                        @RequestParam(defaultValue = "1") Integer pageNumber,
                                        @RequestParam(defaultValue = "10") Integer pageSize)   {
        logger.info("redpacketV0: "+  redPacket);
        Pageable pageable =  PageRequest.of(pageNumber-1 , pageSize);
        Specification<RedPacket> queryParam = new Specification<RedPacket>() {
            @Override
            public Predicate toPredicate(Root<RedPacket> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (redPacket.getId() != null) {
                    logger.info(redPacket.getId());
                    predicates.add(criteriaBuilder.like(root.get("id"),  "%"+redPacket.getId()+"%") );
                }
                if (redPacket.getName() != null) {
                    predicates.add(criteriaBuilder.like(root.get("name"),  "%"+ redPacket.getName() +"%") );
                }
                if (redPacket.getCreator() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("creator"),  redPacket.getCreator()));
                }
                if (redPacket.getExpireTime() != null) {
                    predicates.add(criteriaBuilder.lessThan(root.get("expireTime"),  redPacket.getExpireTime()));
                }
                if (redPacket.getCreateTime() != null) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("createTime"),  redPacket.getCreateTime()));
                }
                if (redPacket.getStatus() != null) {
                    predicates.add(criteriaBuilder.like(root.get("status"), "%"+ redPacket.getStatus()+ "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return BaseResponse.successWithData(redPacketRepository.findAll(queryParam,  pageable));
    }




}
