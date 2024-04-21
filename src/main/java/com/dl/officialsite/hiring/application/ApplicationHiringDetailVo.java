package com.dl.officialsite.hiring.application;

import com.dl.officialsite.member.Member;
import java.util.List;
import lombok.Data;

/**
 * @ClassName ApplicationHiringDetailVo
 * @Author jackchen
 * @Date 2024/3/19 22:57
 * @Description ApplicationHiringDetailVo
 **/
@Data
public class ApplicationHiringDetailVo {

    private long applyPersonNum;

    private List<Member> applyPersonAddress;
    private List<ApplyPersonInfo> applyPersonList;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ApplyPersonInfo {
        private String address;
        private Date applyTime;
    }
}

