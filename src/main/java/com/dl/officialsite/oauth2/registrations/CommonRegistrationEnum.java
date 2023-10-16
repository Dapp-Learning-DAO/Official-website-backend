package com.dl.officialsite.oauth2.registrations;

public enum CommonRegistrationEnum {

    Github{
        @Override
        public String toString(){
            return "github";
        }
    },

    Twitter {
        @Override
        public String toString(){
            return "twitter";
        }
    }

}
