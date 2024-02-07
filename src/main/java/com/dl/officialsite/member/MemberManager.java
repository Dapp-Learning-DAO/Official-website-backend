package com.dl.officialsite.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;

import java.util.Objects;
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

    public Member requireMemberAddressExist(String address) {
        Member member = getMemberByAddress(address);
        if (Objects.isNull(member))
            throw new BizException(CodeEnums.NOT_FOUND_MEMBER);
        return member;
    }

    public Member requireMembeIdExist(Long id) {
        Optional<Member> optionalRsp = memberRepository.findById(id);
        if (!optionalRsp.isPresent())
            throw new BizException(CodeEnums.NOT_FOUND_MEMBER);

        return optionalRsp.get();
    }
}
