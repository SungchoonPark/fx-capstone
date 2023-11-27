package com.capstone.fxteam.member.controller;

import com.capstone.fxteam.constant.dto.ApiResponse;
import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.member.dto.MemberDto;
import com.capstone.fxteam.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<MemberDto.SignUpResponseDto>> signUp(@RequestBody MemberDto.SignUpRequestDto joinDto) {
        System.out.println("joinDto = " + joinDto);
        joinDto.setPassword(encodingPassword(joinDto.getPassword()));
        MemberDto.SignUpResponseDto signUpResponseDto = memberService.signUp(joinDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(signUpResponseDto, CustomResponseStatus.SUCCESS));
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @GetMapping("/exist/id")
    public ResponseEntity<ApiResponse<MemberDto.CheckDuplicationResponseDto>> checkIdDuplication(MemberDto.CheckIdDuplicationRequestDto idDuplicationRequestDto) {
        MemberDto.CheckDuplicationResponseDto checkIdDuplicationResponseDto = memberService.checkIdDuplication(idDuplicationRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(checkIdDuplicationResponseDto, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/exist/email")
    public ResponseEntity<ApiResponse<MemberDto.CheckDuplicationResponseDto>> checkEmailDuplication(MemberDto.CheckEmailDuplicationRequestDto emailDuplicationRequestDto) {
        MemberDto.CheckDuplicationResponseDto checkIdDuplicationResponseDto = memberService.checkEmailDuplication(emailDuplicationRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(checkIdDuplicationResponseDto, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/exist/nickname")
    public ResponseEntity<ApiResponse<MemberDto.CheckDuplicationResponseDto>> checkNicknameDuplication(MemberDto.CheckNicknameDuplicationRequestDto nicknameDuplicationRequestDto) {
        MemberDto.CheckDuplicationResponseDto checkIdDuplicationResponseDto = memberService.checkNicknameDuplication(nicknameDuplicationRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(checkIdDuplicationResponseDto, CustomResponseStatus.SUCCESS));
    }
}
