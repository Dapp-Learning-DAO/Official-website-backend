package com.dl.officialsite.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberManager {

    @Autowired
    private MemberRepository memberRepository;

    public Member getMemberByAddress(String address) {
        Optional<Member> member = memberRepository.findByAddress(address);
        if (member.isPresent()) {
            return member.get();
        }
        return null;
    }
}
