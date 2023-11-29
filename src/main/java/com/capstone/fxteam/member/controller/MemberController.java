package com.capstone.fxteam.member.controller;

import com.capstone.fxteam.constant.dto.ApiResponse;
import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.member.dto.MemberDto;
import com.capstone.fxteam.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<MemberDto.SignUpResponseDto>> signUp(@RequestBody MemberDto.SignUpRequestDto joinDto) {
        System.out.println("joinDto = " + joinDto);
        MemberDto.SignUpResponseDto signUpResponseDto = memberService.signUp(joinDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(signUpResponseDto, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/exist/id")
    public ResponseEntity<ApiResponse<MemberDto.CheckDuplicationResponseDto>> checkIdDuplication(@RequestBody MemberDto.CheckIdDuplicationRequestDto idDuplicationRequestDto) {
        MemberDto.CheckDuplicationResponseDto checkIdDuplicationResponseDto = memberService.checkIdDuplication(idDuplicationRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(checkIdDuplicationResponseDto, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/exist/email")
    public ResponseEntity<ApiResponse<MemberDto.CheckDuplicationResponseDto>> checkEmailDuplication(@RequestBody MemberDto.CheckEmailDuplicationRequestDto emailDuplicationRequestDto) {
        MemberDto.CheckDuplicationResponseDto checkIdDuplicationResponseDto = memberService.checkEmailDuplication(emailDuplicationRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(checkIdDuplicationResponseDto, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/exist/nickname")
    public ResponseEntity<ApiResponse<MemberDto.CheckDuplicationResponseDto>> checkNicknameDuplication(@RequestBody MemberDto.CheckNicknameDuplicationRequestDto nicknameDuplicationRequestDto) {
        MemberDto.CheckDuplicationResponseDto checkIdDuplicationResponseDto = memberService.checkNicknameDuplication(nicknameDuplicationRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(checkIdDuplicationResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<MemberDto.SignInResponseDto>> signIn(@RequestBody MemberDto.SignInRequestDto signInRequestDto) {
        System.out.println("signInRequestDto = " + signInRequestDto);
        MemberDto.SignInResponseDto signInResponseDto = memberService.signIn(signInRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(signInResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader("Authorization") String accessToken) {
        memberService.logout(accessToken);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/id")
    public ResponseEntity<ApiResponse<MemberDto.FindLoginIdResponseDto>> findLoginId(@RequestBody MemberDto.FindLoginIdRequestDto findLoginIdRequestDto) {
        System.out.println("findLoginIdRequestDto = " + findLoginIdRequestDto);
        MemberDto.FindLoginIdResponseDto findLoginIdResponseDto = memberService.findLoginId(findLoginIdRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(findLoginIdResponseDto, CustomResponseStatus.SUCCESS));
    }
}
