package com.capstone.fxteam.member.controller;

import com.capstone.fxteam.constant.dto.ApiResponse;
import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.email.dto.EmailDto;
import com.capstone.fxteam.member.dto.MemberDto;
import com.capstone.fxteam.member.service.MemberService;
import com.capstone.fxteam.member.service.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<MemberDto.SignUpResponseDto>> signUp(@RequestBody MemberDto.SignUpRequestDto joinDto) {
        MemberDto.SignUpResponseDto signUpResponseDto = memberService.signUp(joinDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(signUpResponseDto, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/exist/id")
    public ResponseEntity<ApiResponse<MemberDto.CheckDuplicationResponseDto>> checkIdDuplication(@RequestParam String id) {
        MemberDto.CheckDuplicationResponseDto checkIdDuplicationResponseDto = memberService.checkIdDuplication(id);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(checkIdDuplicationResponseDto, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/exist/email")
    public ResponseEntity<ApiResponse<MemberDto.CheckDuplicationResponseDto>> checkEmailDuplication(@RequestParam String email) {
        MemberDto.CheckDuplicationResponseDto checkIdDuplicationResponseDto = memberService.checkEmailDuplication(email);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(checkIdDuplicationResponseDto, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/exist/nickname")
    public ResponseEntity<ApiResponse<MemberDto.CheckDuplicationResponseDto>> checkNicknameDuplication(@RequestParam String nickname) {
        MemberDto.CheckDuplicationResponseDto checkIdDuplicationResponseDto = memberService.checkNicknameDuplication(nickname);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(checkIdDuplicationResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<MemberDto.SignInResponseDto>> signIn(@RequestBody MemberDto.SignInRequestDto signInRequestDto) {
        MemberDto.SignInResponseDto signInResponseDto = memberService.signIn(signInRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(signInResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/member/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader("Authorization") String accessToken) {
        memberService.logout(accessToken);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/id")
    public ResponseEntity<ApiResponse<MemberDto.FindLoginIdResponseDto>> findLoginId(@RequestBody MemberDto.FindLoginIdRequestDto findLoginIdRequestDto) {
        MemberDto.FindLoginIdResponseDto findLoginIdResponseDto = memberService.findLoginId(findLoginIdRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(findLoginIdResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/email/request")
    public ResponseEntity<ApiResponse<String>> sendEmail(@RequestBody EmailDto.EmailRequestDto emailDto) {
        memberService.sendVerificationEmail(emailDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/email/verification")
    public ResponseEntity<ApiResponse<EmailDto.EmailVerificationResultDto>> verificationAuthCodeInEmail(
            @RequestBody EmailDto.EmailVerificationRequestDto emailVerificationRequestDto
    ) {
        EmailDto.EmailVerificationResultDto emailVerificationResultDto = memberService.verifyAuthCode(emailVerificationRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(emailVerificationResultDto, CustomResponseStatus.SUCCESS));
    }

    @PutMapping("/force/pwd")
    public ResponseEntity<ApiResponse<String>> forceChangePassword(@RequestBody MemberDto.forceChangePasswordRequestDto changePasswordDto) {
        memberService.forceChangePassword(changePasswordDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @PutMapping("/member/pwd")
    public ResponseEntity<ApiResponse<String>> normalChangePassword(
            @RequestBody MemberDto.normalChangePasswordRequestDto changePasswordDto,
            Authentication authentication
    ) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        memberService.normalChangePassword(changePasswordDto, principal.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/member/reissue")
    public ResponseEntity<ApiResponse<MemberDto.ReissueResponseDto>> accessTokenReissue(@RequestHeader("Authorization") String refreshToken) {
        MemberDto.ReissueResponseDto reissueResponseDto = memberService.tokenReissue(refreshToken);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(reissueResponseDto, CustomResponseStatus.SUCCESS));

    }

}
