package com.capstone.fxteam.board.service;

import com.capstone.fxteam.board.dto.BoardDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    BoardDto.BoardPostResponseDto post(
            BoardDto.BoardPostRequestDto boardPostRequestDto,
            List<MultipartFile> files,
            String loginId
    );

    List<BoardDto.BoardGetResponseDto> getBoars(String category);

    BoardDto.BoardDetailGetResponseDto getBoardDetail(Long boardId);

    BoardDto.BoardPostResponseDto update(
            Long boardId,
            BoardDto.BoardPostRequestDto boardPostRequestDto,
            List<MultipartFile> files
    );

    void delete(long boardId, String loginId);
}
