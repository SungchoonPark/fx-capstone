package com.capstone.fxteam.board.controller;

import com.capstone.fxteam.board.dto.BoardDto;
import com.capstone.fxteam.board.service.BoardService;
import com.capstone.fxteam.constant.dto.ApiResponse;
import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/user/board")
    public ResponseEntity<ApiResponse<BoardDto.BoardPostResponseDto>> postBoard(
            @RequestPart("boardPostRequest") BoardDto.BoardPostRequestDto boardPostRequestDto,
            @RequestPart("files") List<MultipartFile> files,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BoardDto.BoardPostResponseDto postResponse = boardService.post(boardPostRequestDto, files, userDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(postResponse, CustomResponseStatus.SUCCESS));
    }
}
