package com.dl.officialsite.member;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

      Optional<Member> member =    memberRepository.findByAddress(address);
      return member.get();
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    Page<Member> getAllMember( @RequestParam(defaultValue = "1") Integer pageNumber,
                               @RequestParam(defaultValue = "10") Integer pageSize)   {
        Pageable pageable =  PageRequest.of(pageNumber - 1, pageSize, Sort.by("timestamp"));
        return memberRepository.findAll(pageable);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createMember(@RequestBody Member member) {
        try {
            Member _member = memberRepository
                    .save(member);
           // logger.info(member.getAddress());
           return new ResponseEntity<>(_member, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<Member> updateMember(@PathVariable("id") long id, @RequestBody Member member) {
//        Optional<Member> memberData = memberRepository.findById(id);
//
//        if (memberData.isPresent()) {
//            Member _member = memberData.get();
//            _member.setAddress(member.getAddress());
//            return new ResponseEntity<>(memberRepository.save(_member), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/{address}")
    public ResponseEntity<Member> updateMemberByAddress(@PathVariable("address") String address, @RequestBody Member member) {
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
            if(member.getNickName()!=null) {
                _member.setNickName(member.getNickName());
            }

            return new ResponseEntity<>(memberRepository.save(_member), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
