package com.capstone.fxteam.board.service;

import com.capstone.fxteam.board.dto.BoardDto;
import com.capstone.fxteam.board.model.Board;
import com.capstone.fxteam.board.model.files.BoardFile;
import com.capstone.fxteam.board.repository.BoardFileRepository;
import com.capstone.fxteam.board.repository.BoardRepository;
import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.constant.exception.CustomException;
import com.capstone.fxteam.member.model.Member;
import com.capstone.fxteam.member.repository.MemberRepository;
import com.capstone.fxteam.metal.service.image.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final S3Service s3Service;

    @Override
    public BoardDto.BoardPostResponseDto post(BoardDto.BoardPostRequestDto boardPostRequestDto, List<MultipartFile> files, String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        });

        Board savedBoard = boardRepository.save(boardPostRequestDto.toEntity(member));

        List<String> fileUrls = s3Service.uploadFile(files);

        fileUrls.forEach(fileUrl -> boardFileRepository.save(BoardFile.from(fileUrl, savedBoard)));

        return new BoardDto.BoardPostResponseDto(savedBoard.getBoardId());
    }
}
