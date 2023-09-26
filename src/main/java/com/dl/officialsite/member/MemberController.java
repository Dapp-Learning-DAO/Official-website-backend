package com.dl.officialsite.member;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("member")
public class MemberController {


    @Autowired
    private MemberRepository memberRepository;

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);


    @RequestMapping(value = "/{address}", method = RequestMethod.GET)
    Member getMemberByAddress(@PathVariable String address) throws Exception {
        return memberRepository.findByAddress(address);
    }


    @PostMapping("/create")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
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

}
