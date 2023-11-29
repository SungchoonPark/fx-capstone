package com.capstone.fxteam.member.service;

import com.capstone.fxteam.email.dto.EmailDto;
import com.capstone.fxteam.member.dto.MemberDto;

public interface MemberService {

    MemberDto.SignUpResponseDto signUp(MemberDto.SignUpRequestDto joinDto);

    MemberDto.CheckDuplicationResponseDto checkIdDuplication(MemberDto.CheckIdDuplicationRequestDto idDuplicationRequestDto);
    MemberDto.CheckDuplicationResponseDto checkEmailDuplication(MemberDto.CheckEmailDuplicationRequestDto emailDuplicationRequestDto);
    MemberDto.CheckDuplicationResponseDto checkNicknameDuplication(MemberDto.CheckNicknameDuplicationRequestDto nicknameDuplicationRequestDto);
    MemberDto.SignInResponseDto signIn(MemberDto.SignInRequestDto signDto);
    void logout(String accessToken);
    MemberDto.FindLoginIdResponseDto findLoginId(MemberDto.FindLoginIdRequestDto findLoginIdRequestDto);
    void sendVerificationEmail(EmailDto.EmailRequestDto toEmail);
    EmailDto.EmailVerificationResultDto verifyAuthCode(EmailDto.EmailVerificationRequestDto emailVerificationDto);
    void forceChangePassword(MemberDto.forceChangePasswordRequestDto changePasswordDto);
    void normalChangePassword(MemberDto.normalChangePasswordRequestDto changePasswordDto, String username);

}
