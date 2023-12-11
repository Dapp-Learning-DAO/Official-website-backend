package com.dl.officialsite.sharing.constant;

import lombok.Getter;

@Getter
public enum SharingLanguageEnum {

    CHINESE(0),

    ENGLISH(1),

    FRENCH(2),

    JAPANESE(3);

    private int code;

    SharingLanguageEnum(int code){
        this.code = code;
    }

    public static SharingLanguageEnum codeOf(int languageCode) {
        for(SharingLanguageEnum language: SharingLanguageEnum.values()){
            if(language.code == languageCode){
                return language;
            }
        }
        throw new IllegalArgumentException("languageCode "+languageCode);
    }
}
