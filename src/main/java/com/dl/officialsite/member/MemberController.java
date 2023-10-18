package com.dl.officialsite.member;



import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.ipfs.IPFSService;
import lombok.Data;
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
import org.springframework.web.multipart.MultipartFile;
import org.web3j.protocol.ipc.IpcService;

@RestController
@RequestMapping("/member")
public class MemberController {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private IPFSService ipfsService;


    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    BaseResponse getMemberByAddress(@RequestParam String address)   {

        Optional<Member> member =  memberRepository.findByAddress(address);
        if(!member.isPresent()){
            return BaseResponse.failWithReason("1001", "no user found");
        }
        return BaseResponse.successWithData(member.get());
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    Page<Member> getAllMember(@RequestParam String address,  @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize)   {
        Pageable pageable =  PageRequest.of(pageNumber - 1, pageSize, Sort.by("timestamp"));
        return memberRepository.findAll(pageable);
    }

    @PostMapping("/create")
    public BaseResponse createMember(@RequestBody Member member, @RequestParam String address) {
        try {
            Member _member = memberRepository
                .save(member);
            return  BaseResponse.successWithData(_member);
        } catch (Exception e) {
            return BaseResponse.failWithReason("1000",e.getMessage());
        }
    }

    @PostMapping("/avatar/update")
    public BaseResponse uploadAvatar(@RequestParam String address, @RequestParam("file") MultipartFile file) {
        try {
            String hash = ipfsService.upload(file.getBytes());
            Optional<Member> memberData = memberRepository.findByAddress(address);
            if (memberData.isPresent()) {
                Member _member = memberData.get();
                _member.setAvatar(hash);
                memberRepository.save(_member);
            }
            return BaseResponse.successWithData(null);
        } catch (Exception e) {
            return BaseResponse.failWithReason(CodeEnums.FAIL_UPLOAD_FAIL.getCode(),
                CodeEnums.FAIL_UPLOAD_FAIL.getMsg());
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

    @PutMapping("/update")
    public BaseResponse updateMemberByAddress(@RequestParam String address, @RequestBody Member member) {
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
            if(member.getTechStack()!= 0) {
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
            if (member.getResume()!= null) {
                _member.setResume(member.getResume());
            }

            return BaseResponse.successWithData(memberRepository.save(_member));
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