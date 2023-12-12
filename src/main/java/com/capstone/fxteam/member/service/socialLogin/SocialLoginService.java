package com.capstone.fxteam.member.service.socialLogin;

import com.capstone.fxteam.member.dto.MemberDto;
import com.capstone.fxteam.security.oauth.OAuthLoginParams;

public interface SocialLoginService {
    MemberDto.SignInResponseDto login(OAuthLoginParams params);
}
