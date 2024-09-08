package com.dl.officialsite.member;

import cn.hutool.core.collection.CollUtil;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.hiring.application.ApplicationRepository;
import com.dl.officialsite.sharing.Share;
import com.dl.officialsite.sharing.SharingRepository;
import com.dl.officialsite.team.Team;
import com.dl.officialsite.team.TeamRepository;
import com.dl.officialsite.team.TeamService;
import com.dl.officialsite.team.teammember.TeamMember;
import com.dl.officialsite.team.teammember.TeamMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dl.officialsite.common.enums.CodeEnums.INVALID_MEMBER;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamService teamService;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private SharingRepository sharingRepository;

    public MemberWithTeam getMemberWithTeamInfoByAddress(String address) {
        Optional<Member> member = memberRepository.findByAddress(address);

        if (member.isPresent()) {
            MemberWithTeam memberWithTeam = new MemberWithTeam();
            BeanUtils.copyProperties(member.get(), memberWithTeam);
            ArrayList teams = new ArrayList();
            List<TeamMember> teamMembers = teamMemberRepository.findByMemberIdAndStatus(member.get().getId(), 0);

            teamMembers.stream().forEach(teamMember -> {
                Team team = teamRepository.findById(teamMember.getTeamId()).get();
                if (team.getTeamName().equals("Dapp-Learning DAO co-founders")) {
                    memberWithTeam.setAdmin(true);
                }
                teams.add(team);
            });
            memberWithTeam.setTeams(teams);
            return memberWithTeam;
        }
        return null;
    }

    public MemberVo save(Member member) {
        memberRepository.save(member);
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(member, memberVo);
        return memberVo;

    }

    public void cleanGitHubTgAndDiscordId(String address) {
        memberRepository.removeGitHubTgAndDiscordId(address);
        log.info("Remove GitHub id, Telegram user id and Discord id to user: {}", address);
    }

    public MemberVo getMemberPrivacyInfo(String address) {
        Optional<Member> member = memberRepository.findByAddress(address);
        if (!member.isPresent()) {
            return null;
        }
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(member.get(), memberVo);

        return memberVo;

    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteMember(String memberAddress) {
        // check address
        Member member = this.memberManager.getMemberByAddress(memberAddress);
        if (Objects.isNull(member)) {
            throw new BizException(INVALID_MEMBER.getCode(), INVALID_MEMBER.getMsg());
        }

        // check supper admin
         String address = UserSecurityUtils.getUserLogin().getAddress();
         if (!teamService.checkMemberIsSuperAdmin(address)) {
         throw new BizException(CodeEnums.NOT_THE_ADMIN.getCode(),
         CodeEnums.NOT_THE_ADMIN.getMsg());
         }

        // delete
        teamMemberRepository.deleteByMemberId(member.getId());
        applicationRepository.deleteByMemberId(member.getId());
        memberRepository.deleteById(member.getId());
    }

    public void freeze(String memberAddress) {
        Member member = this.memberManager.getMemberByAddress(memberAddress);
        if (Objects.isNull(member)) {
            return;
        }
        log.info("freeze member: {}", memberAddress);
        member.setStatus(1);
        memberRepository.save(member);
    }


    public void nickNameExists(String nickName){
        if(StringUtils.isNotBlank(nickName) && this.memberRepository.findByNickName(nickName).isPresent()){
            throw new RuntimeException("Nickname already exists, please change another one.") ;
        }
    }

    public List<Member> queryMemberInfoByAddress(List<String> addressList){
        if(CollUtil.isEmpty(addressList)){
            return new ArrayList<>();
        }
        List<Member> memberList = memberRepository.findByAddressList(addressList);
        //只保留id、address、nickName字段
        List<Member> result = new ArrayList<>();
        for (Member member : memberList) {
            Member filteredMember = new Member();
            filteredMember.setId(member.getId());
            filteredMember.setAddress(member.getAddress());
            filteredMember.setNickName(member.getNickName());
            result.add(filteredMember);
        }
        return result;
    }

    public void updateShareCount(){
        List<Share> shareList = sharingRepository.findAll();
        List<Member> memberList = memberRepository.findAll();
        // 将shareList按照presenter分组统计求和，得到一个map,key为presenter，value为分享次数
        Map<String, Long> presenterShareCountMap = shareList.stream()
                .collect(Collectors.groupingBy(Share::getPresenter, Collectors.counting()));

        // 遍历memberList，根据nickName找到对应的presenter，将分享次数赋值给member的shareCount字段
        for (Member member : memberList) {
            Long shareCount = presenterShareCountMap.get(member.getNickName());
            if (shareCount != null) {
                member.setShareCount(Math.toIntExact(shareCount));
            } else {
                member.setShareCount(0);
            }
        }
        // 保存member
        memberRepository.saveAll(memberList);

    }

}
