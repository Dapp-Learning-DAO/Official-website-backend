package com.dl.officialsite.reward;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.member.MemberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reward")
public class RewardController {

    @Autowired
    private RewardRepository redPacketRepository;
    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);


    @PostMapping("/create")
    public BaseResponse createRedPacket(@Valid @RequestBody Reward redPacket, @RequestParam String address) {
        redPacket.setCreator(address);
        Reward redPacket1 = redPacketRepository.save(redPacket);
        return  BaseResponse.successWithData(redPacket1);

    }




}
