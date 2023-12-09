package com.dl.officialsite.sharing.constant;

import lombok.Getter;

@Getter
public enum SharingMeetingType {

    TENCENT(0),

    GOOGLE(1),

    ZOOM(2),

    TELEGRAM(3),

    DC(4);

    private int code;

    SharingMeetingType(int code){
        this.code = code;
    }

    public static SharingMeetingType codeOf(int meetingType) {
        for(SharingMeetingType m: SharingMeetingType.values()){
            if(m.code == meetingType){
                return m;
            }
        }
        throw new IllegalArgumentException("meetingType "+meetingType);
    }
}
