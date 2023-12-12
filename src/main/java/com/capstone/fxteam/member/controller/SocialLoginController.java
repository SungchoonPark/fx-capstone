package com.capstone.fxteam.member.controller;

import com.capstone.fxteam.constant.dto.ApiResponse;
import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.member.dto.MemberDto;
import com.capstone.fxteam.member.service.socialLogin.SocialLoginService;
import com.capstone.fxteam.security.oauth.KakaoLoginParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class SocialLoginController {
    private final SocialLoginService socialLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<ApiResponse<MemberDto.SignInResponseDto>> kakaoLogin(@RequestBody KakaoLoginParams params) {
        System.out.println("[CODE] params = " + params.getAuthorizationCode());
        MemberDto.SignInResponseDto signInResponseDto = socialLoginService.login(params);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(signInResponseDto, CustomResponseStatus.SUCCESS));
    }
}
