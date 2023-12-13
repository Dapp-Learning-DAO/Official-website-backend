package com.dl.officialsite.sponsor;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.team.TeamService;
import com.dl.officialsite.team.vo.TeamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SponsorService {

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private TeamService teamService;


    public Sponsor add(String address , Sponsor sponsor) {
        Sponsor sponsorNew = null;
        if(teamService.checkMemberIsAdmin(address)) {
             sponsorNew = sponsorRepository.save(sponsor);

        } else {
            throw new BizException(CodeEnums.NOT_THE_ADMIN.getCode(),
                    CodeEnums.NOT_THE_ADMIN.getMsg());
        }
        return sponsorNew;
    }


}
