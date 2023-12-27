package com.dl.officialsite.member;


import com.dl.officialsite.common.base.BaseResponse;
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
@RequestMapping("/member")
public class MemberController {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    BaseResponse getMemberByAddress(@RequestParam String address)  {

        MemberWithTeam member = memberService.getMemberWithTeamInfoByAddress(address);
        if (member == null) {
            return BaseResponse.failWithReason("1001", "no user found");
        }
        return BaseResponse.successWithData(member);
    }


    @RequestMapping(value = "/query/privacy", method = RequestMethod.GET)
    BaseResponse getMemberDetailInfoByAddress(@RequestParam String address)  {
       MemberVo memberVo =  memberService.getMemberPrivacyInfo(address);
        if(memberVo ==null ) {
                return BaseResponse.failWithReason("1001", "no user found");
        }
        return BaseResponse.successWithData(memberVo);
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    BaseResponse getAllMember(@RequestParam String address, @RequestParam(defaultValue = "1") Integer pageNumber,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("createTime"));
        return BaseResponse.successWithData(memberRepository.findAll(pageable));
    }

    @PostMapping(value = "/all/query")
    BaseResponse getAllMemberByCriteria(@RequestParam String address,
                                        @RequestBody MemberVo member,
                                        @RequestParam(defaultValue = "1") Integer pageNumber,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Specification<Member> queryParam = new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (member.getAddress() != null) {
                    logger.info(member.getAddress());
                    predicates.add(criteriaBuilder.like(root.get("address"), "%" + member.getAddress() + "%"));
                }
                if (member.getNickName() != null) {
                    predicates.add(criteriaBuilder.like(root.get("nickName"), "%" + member.getNickName() + "%"));
                }
                if (member.getGithubId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("githubId"), member.getGithubId()));
                }
                if (member.getTweetId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("tweetId"), member.getTweetId()));
                }
                if (member.getWechatId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("wechatId"), member.getWechatId()));
                }
                if (member.getTechStack() != null) {
                    predicates.add(criteriaBuilder.like(root.get("techStack"), member.getTechStack()));
                }
                if (member.getPrograming() != null) {
                    predicates.add(criteriaBuilder.like(root.get("programing"), "%" + member.getPrograming() + "%"));
                }
                if (member.getCreateTime() != null) {
                    predicates.add(criteriaBuilder.ge(root.get("createTime"), member.getCreateTime()));
                }
                if (member.getCity() != null) {
                    predicates.add(criteriaBuilder.like(root.get("city"), "%" + member.getCity() + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return BaseResponse.successWithData(memberRepository.findAll(queryParam, pageable));
    }

    @PostMapping("/create")
    public BaseResponse createMember(@Valid @RequestBody Member member, @RequestParam String address) {


        if(member.getGithubId()!=null && member.getGithubId().equals("")) {
            member.setGithubId(null);
        }
            MemberVo _member = memberService.save(member);
            return BaseResponse.successWithData(_member);
    }


    @PutMapping("/update")
    public BaseResponse updateMemberByAddress(@RequestParam String address, @RequestBody MemberVo member) {
        Optional<Member> memberData = memberRepository.findByAddress(address);

        if (memberData.isPresent()) {
            Member _member = memberData.get();
            if(member.getGithubId()!=null) {
                _member.setGithubId(member.getGithubId());
            }
            if(member.getTweetId()!=null) {
                _member.setTweetId(member.getTweetId());
            }
            if(member.getWechatId()!=null) {
                _member.setWechatId(member.getWechatId());
            }
            if(member.getTelegramId()!=null) {
                _member.setTelegramId(member.getTelegramId());
            }
            if(member.getNickName()!=null) {
                _member.setNickName(member.getNickName());
            }
            if(member.getTechStack()!= null) {
                _member.setTechStack(member.getTechStack());
            }
            if (member.getPrograming()!=null) {
                _member.setPrograming(member.getPrograming());
            }
            if (member.getEmail()!=null) {
                _member.setEmail(member.getEmail());
            }
            if (member.getCity()!=null) {
                _member.setCity(member.getCity());
            }
            if (member.getInterests()!= null) {
                _member.setInterests(member.getInterests());
            }
            if (member.getAvatar()!= null) {
                _member.setAvatar(member.getAvatar());
            }
            if (member.getResume()!= null) {
                _member.setResume(member.getResume());
            }
            if (member.getWorkStatus()!= null) {
                _member.setWorkStatus(member.getWorkStatus());
            }
            return BaseResponse.successWithData(memberService.save(_member));
        } else {
            return BaseResponse.failWithReason("1001","no user found");
        }
    }

//todo query

    // findByNickName
    private Long getMemberId(HttpSession session) {
        Long memberId = (Long) session
            .getAttribute("memberId");
        return memberId;
    }

}