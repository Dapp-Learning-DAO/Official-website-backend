package com.dl.officialsite.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {


    @Autowired
    private MemberRepository memberRepository;

    public Member getMemberByAddress(String address) {
        return memberRepository.findByAddress(address);
    }
}
