package com.dl.officialsite.member;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {


    @Autowired
    private MemberRepository memberRepository;

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);


    @PostMapping("/login")
    public String login(@RequestBody SignInfo sign, HttpSession session) {

        if (checkSignature(sign)) {
            session.setAttribute("member", sign.getAddress());
            return "log in successfully";
        }

        return "faile to check sign message";
    }

    private boolean checkSignature(SignInfo sign) {
        // ECVERIFY
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 退出登录就是将用户信息删除
        session.removeAttribute("user");
        return "log out successfully";
    }



    @RequestMapping(value = "/{address}", method = RequestMethod.GET)
    Member getMemberByAddress(@PathVariable String address, HttpSession session) throws Exception {
        logger.info(session.getAttributeNames().toString());
        logger.info(session.getId());
        return memberRepository.findByAddress(address);
    }


    @PostMapping("/create")
    public ResponseEntity<Member> createMember(@RequestBody Member member,  HttpServletRequest request) {
        try {
            Member _member = memberRepository
                    .save(member);
            logger.info(member.getAddress());
            return new ResponseEntity<>(_member, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/member/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable("id") long id, @RequestBody Member member) {
        Optional<Member> memberData = memberRepository.findById(id);

        if (memberData.isPresent()) {
            Member _member = memberData.get();
            _member.setAddress(member.getAddress());
            return new ResponseEntity<>(memberRepository.save(_member), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    private Long getMemberId(HttpSession session) {
        Long memberId = (Long) session
                .getAttribute("memberId");


        return memberId;
    }

}
