package com.capstone.fxteam.board.controller;

import com.capstone.fxteam.board.dto.BoardDto;
import com.capstone.fxteam.board.service.BoardService;
import com.capstone.fxteam.constant.dto.ApiResponse;
import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/member/board")
    public ResponseEntity<ApiResponse<BoardDto.BoardPostResponseDto>> postBoard(
            @RequestPart("boardPostRequest") @Valid BoardDto.BoardPostRequestDto boardPostRequestDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BoardDto.BoardPostResponseDto postResponse = boardService.post(boardPostRequestDto, files, userDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(postResponse, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/member/{category}/boards")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardGetResponseDto>>> getBoars(@PathVariable String category) {
        List<BoardDto.BoardGetResponseDto> boardDtos = boardService.getBoars(category);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardDtos, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/member/board/{boardId}")
    public ResponseEntity<ApiResponse<BoardDto.BoardDetailGetResponseDto>> getBoardDetail(@PathVariable long boardId) {
        BoardDto.BoardDetailGetResponseDto boardDetail = boardService.getBoardDetail(boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardDetail, CustomResponseStatus.SUCCESS));
    }

    @PutMapping("/member/board/{boardId}")
    public ResponseEntity<ApiResponse<BoardDto.BoardPostResponseDto>> updateBoard(
            @PathVariable long boardId,
            @RequestPart("boardPostRequest") @Valid BoardDto.BoardPostRequestDto boardPostRequestDto,
            @RequestPart("files") List<MultipartFile> files
    ) {
        BoardDto.BoardPostResponseDto updateResponseDto = boardService.update(boardId, boardPostRequestDto, files);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(updateResponseDto, CustomResponseStatus.SUCCESS));
    }

    @DeleteMapping("/member/board/{boardId}")
    public ResponseEntity<ApiResponse<String>> deleteBoard(
            @PathVariable long boardId,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boardService.delete(boardId, userDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));

    }
}
