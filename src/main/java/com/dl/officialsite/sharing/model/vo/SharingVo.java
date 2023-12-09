package com.dl.officialsite.sharing.model.vo;

import com.dl.officialsite.sharing.constant.SharingLanguageEnum;
import com.dl.officialsite.sharing.constant.SharingMeetingType;
import com.dl.officialsite.sharing.model.db.TbShare;
import lombok.Data;

@Data
public class SharingVo {

    private Long id;

    private String theme;

    private String date;

    private String time;

    private String language;  // 0 Chinese 1 English

    private String presenter;

    private String org;

    private String twitter;

    private String sharingDoc;

    private String label;

    private int locked;

    private String meetingType;

    private String meetingLink;

    public static SharingVo convert(TbShare entity) {
        SharingVo vo = new SharingVo();
        vo.id = entity.getId();
        vo.theme = entity.getTheme();
        vo.date = entity.getDate();
        vo.time = entity.getTime();
        vo.language = SharingLanguageEnum.codeOf(entity.getLanguage()).name();
        vo.presenter = entity.getPresenter();
        vo.org = entity.getOrg();
        vo.twitter = entity.getTwitter();
        vo.sharingDoc = entity.getSharingDoc();
        vo.label = entity.getLabel();
        vo.locked = entity.getLockStatus();
        vo.meetingType = SharingMeetingType.codeOf(entity.getMeetingType()).name();
        return vo;
    }
}
