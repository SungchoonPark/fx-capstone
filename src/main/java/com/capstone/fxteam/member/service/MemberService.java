package com.capstone.fxteam.member.service;

import com.capstone.fxteam.member.dto.MemberDto;

public interface MemberService {

    MemberDto.SignUpResponseDto signUp(MemberDto.SignUpRequestDto joinDto);

    MemberDto.CheckDuplicationResponseDto checkIdDuplication(MemberDto.CheckIdDuplicationRequestDto idDuplicationRequestDto);
    MemberDto.CheckDuplicationResponseDto checkEmailDuplication(MemberDto.CheckEmailDuplicationRequestDto emailDuplicationRequestDto);
    MemberDto.CheckDuplicationResponseDto checkNicknameDuplication(MemberDto.CheckNicknameDuplicationRequestDto nicknameDuplicationRequestDto);

}
