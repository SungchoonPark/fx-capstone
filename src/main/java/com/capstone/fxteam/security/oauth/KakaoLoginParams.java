package com.capstone.fxteam.security.oauth;

import com.capstone.fxteam.member.model.enums.OAuthProvider;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class KakaoLoginParams implements OAuthLoginParams {
    /***
     * 카카오 API 요청에 필요한 authorizationCode를 갖고있는 클래스이다.
     */
    private String authorizationCode;

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    @Override
    public OAuthProvider oauthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}
