package com.capstone.fxteam.board.service;

import com.capstone.fxteam.board.dto.BoardDto;
import com.capstone.fxteam.constant.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    BoardDto.BoardPostResponseDto post(
            BoardDto.BoardPostRequestDto boardPostRequestDto,
            List<MultipartFile> files,
            String loginId
    );

    Page<BoardDto.BoardGetResponseDto> getBoars(String category);

    BoardDto.BoardPostResponseDto update(
            Long boardId,
            BoardDto.BoardPostRequestDto boardPostRequestDto,
            List<MultipartFile> files
    );

    void delete(long boardId, String loginId);
}
