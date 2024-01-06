package com.capstone.fxteam.member.service;

import com.capstone.fxteam.email.dto.EmailDto;
import com.capstone.fxteam.member.dto.MemberDto;

public interface MemberService {

    MemberDto.SignUpResponseDto signUp(MemberDto.SignUpRequestDto joinDto);

    MemberDto.CheckDuplicationResponseDto checkIdDuplication(String id);
    MemberDto.CheckDuplicationResponseDto checkEmailDuplication(String email);
    MemberDto.CheckDuplicationResponseDto checkNicknameDuplication(String nickname);
    MemberDto.SignInResponseDto signIn(MemberDto.SignInRequestDto signDto);
    void logout(String accessToken);
    MemberDto.FindLoginIdResponseDto findLoginId(MemberDto.FindLoginIdRequestDto findLoginIdRequestDto);
    void sendVerificationEmail(EmailDto.EmailRequestDto toEmail);
    EmailDto.EmailVerificationResultDto verifyAuthCode(EmailDto.EmailVerificationRequestDto emailVerificationDto);
    void forceChangePassword(MemberDto.forceChangePasswordRequestDto changePasswordDto);
    void normalChangePassword(MemberDto.normalChangePasswordRequestDto changePasswordDto, String username);
    MemberDto.ReissueResponseDto tokenReissue(String refreshToken);

}
