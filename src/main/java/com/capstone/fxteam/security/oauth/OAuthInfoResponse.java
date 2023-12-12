package com.capstone.fxteam.security.oauth;

import com.capstone.fxteam.member.model.enums.OAuthProvider;

public interface OAuthInfoResponse {
    /***
     * AccessToken으로 요청한 외부 API 프로필 응답값을 우리 서비스의 Model로 변환하기 위한 인터페이스이다.
     */
    String getEmail();
    String getNickname();
    OAuthProvider getOauthProvider();
}
