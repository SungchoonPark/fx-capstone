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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardServiceImpl implements BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final S3Service s3Service;

    @Override
    public BoardDto.BoardPostResponseDto post(BoardDto.BoardPostRequestDto boardPostRequestDto, List<MultipartFile> files, String loginId) {
        Member member = findMemberByLoginId(loginId);

        Board savedBoard = boardRepository.save(boardPostRequestDto.toEntity(member));

        List<String> fileUrls = s3Service.uploadFile(files);

        fileUrls.forEach(fileUrl -> boardFileRepository.save(BoardFile.from(fileUrl, savedBoard)));

        return new BoardDto.BoardPostResponseDto(savedBoard.getBoardId());
    }

    @Override
    public BoardDto.BoardPostResponseDto update(Long boardId, BoardDto.BoardPostRequestDto boardPostRequestDto, List<MultipartFile> files) {
        Board board = findBoardByBoardId(boardId);
        board.updateBoard(boardPostRequestDto.getTitle(), boardPostRequestDto.getContent());

        if (files == null) {
            return new BoardDto.BoardPostResponseDto(board.getBoardId());
        }

        // s3에서 삭제
        List<String> boardFileUrl = board.getBoardFileUrl();
        boardFileUrl.forEach(s3Service::deleteFile);

        // DB 에서 삭제 (Column 업데이트)
        List<BoardFile> boardFiles = board.getBoardFiles();
        boardFiles.forEach(BoardFile::deleteBoardFile);

        // s3에 새로운 파일 업로드 및 DB에 저장
        List<String> uploadFileUrls = s3Service.uploadFile(files);
        uploadFileUrls.forEach(uploadFileUrl -> boardFileRepository.save(BoardFile.from(uploadFileUrl, board)));

        return new BoardDto.BoardPostResponseDto(board.getBoardId());
    }

    @Override
    public void delete(long boardId, String loginId) {
        Member member = findMemberByLoginId(loginId);

        Board board = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.BOARD_NOT_FOUND);
        });

        if (!member.isSameMember(loginId)) {
            throw new CustomException(CustomResponseStatus.AUTHORIZATION_FAILED);
        }

        board.deleteBoard();
    }

    private Member findMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        });
    }

    private Board findBoardByBoardId(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.BOARD_NOT_FOUND);
        });
    }
}
