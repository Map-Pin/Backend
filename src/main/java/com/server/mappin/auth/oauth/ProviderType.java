package com.server.mappin.auth.oauth;

public enum ProviderType {
    KAKAO, GOOGLE;

    @Override
    public String toString(){
        switch (this){
            case KAKAO:
                return "Kakao";
            case GOOGLE:
                    return "Google";
            default:
                throw new IllegalArgumentException();
        }
    }
}
