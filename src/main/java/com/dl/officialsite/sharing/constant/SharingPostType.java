package com.dl.officialsite.sharing.constant;

public enum SharingPostType {

    CLASSIC(0);

    private int code;

    SharingPostType(int code){
        this.code = code;
    }

    public static SharingPostType codeOf(int code){
        for(SharingPostType postType: SharingPostType.values()){
            if(postType.code == code){
                return postType;
            }
        }
        throw new IllegalArgumentException("SharingPostType "+code);
    }
}
