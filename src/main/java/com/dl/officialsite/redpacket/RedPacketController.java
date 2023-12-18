package com.dl.officialsite.redpacket;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.login.Auth;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberController;
import com.dl.officialsite.member.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import javax.validation.Valid;
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


    @PostMapping("/create")
   //todo  @Auth("admin")
    public BaseResponse createRedPacket(@Valid @RequestBody RedPacket redPacket, @RequestParam String address) {
        redPacket.setCreator(address);
        RedPacket redPacket1 = redPacketRepository.save(redPacket);
        return  BaseResponse.successWithData(redPacket1);

    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    BaseResponse getRedpacketById(@RequestParam String id, @RequestParam String address)   {

        Optional<RedPacket> redPacket =  redPacketRepository.findById(id);
        if(!redPacket.isPresent()){
            return BaseResponse.failWithReason("1001", "no user found");
        }
        return BaseResponse.successWithData(redPacket.get());
    }


    @RequestMapping(value = "/query/all", method = RequestMethod.GET)
    BaseResponse getRedPacketAll(@RequestParam String address,
                                @RequestParam(defaultValue = "1") Integer pageNumber,
                              @RequestParam(defaultValue = "10") Integer pageSize)   {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        return BaseResponse.successWithData(redPacketRepository.findAll(pageable));
    }


    @RequestMapping(value = "/query/user", method = RequestMethod.GET)
    BaseResponse getRedPacketByAddress(@RequestParam String address, @RequestParam(required = false) Integer status) {
        List<RedPacket> result;
        if(status == 0) {
             result = redPacketRepository.findByUnclaimedPacket("%" + address + "%", 0);
        } else {
             result = redPacketRepository.findByClaimedPacket("%" + address + "%");

        }
        return BaseResponse.successWithData(result);
    }

    @PostMapping(value = "/query/all")
    BaseResponse getAllRedPacketByCriteria(@RequestParam String address,
                                        @RequestBody   RedPacketVo redPacket,
                                        @RequestParam(defaultValue = "1") Integer pageNumber,
                                        @RequestParam(defaultValue = "10") Integer pageSize)   {
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
