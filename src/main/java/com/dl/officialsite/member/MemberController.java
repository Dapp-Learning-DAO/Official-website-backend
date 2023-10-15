package com.dl.officialsite.member;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {


    @Autowired
    private MemberRepository memberRepository;

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @RequestMapping(value = "/{address}", method = RequestMethod.GET)
    Member getMemberByAddress(@PathVariable String address)   {

        return memberRepository.findByAddress(address).get();
    }


    @PostMapping("/create")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        try {
            Member _member = memberRepository
                    .save(member);
           // logger.info(member.getAddress());
           return new ResponseEntity<>(_member, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
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

    @PutMapping("/{address}")
    public ResponseEntity<Member> updateMemberByAddress(@PathVariable("address") String address, @RequestBody Member member) {
        Optional<Member> memberData = memberRepository.findByAddress(address);

        if (memberData.isPresent()) {
            Member _member = memberData.get();
            _member.setAddress(member.getAddress());
            return new ResponseEntity<>(memberRepository.save(_member), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//todo query
    private Long getMemberId(HttpSession session) {
        Long memberId = (Long) session
                .getAttribute("memberId");


        return memberId;
    }

}
