package com.dl.officialsite.redpacket;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/red-packet")
public class RedPacketController {

    @Autowired
    private RedPacketRepository redPacketRepository;


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
    BaseResponse getMemberById(@RequestParam String id)   {

        Optional<RedPacket> redPacket =  redPacketRepository.findById(id);
        if(!redPacket.isPresent()){
            return BaseResponse.failWithReason("1001", "no user found");
        }
        return BaseResponse.successWithData(redPacket.get());
    }
}
