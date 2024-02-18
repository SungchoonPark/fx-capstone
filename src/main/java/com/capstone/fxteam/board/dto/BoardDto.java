package com.capstone.fxteam.board.dto;

import com.capstone.fxteam.board.model.Board;
import com.capstone.fxteam.board.model.enums.BoardCategory;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.member.model.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BoardDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardPostRequestDto {
        @NotBlank(message = "제목이 공백입니다.")
        private String title;
        @NotBlank(message = "내용이 공백입니다.")
        private String content;
        @NotBlank(message = "카테고리가 공백입니다.")
        private String boardCategory;


        public Board toEntity(Member member) {
            return Board
                    .builder()
                    .title(title)
                    .content(content)
                    .member(member)
                    .deleteStatus(DeleteEnum.NOT_DELETE)
                    .boardCategory(getCategory(boardCategory))
                    .build();
        }

        private BoardCategory getCategory(String boardCategory) {
            return switch (boardCategory) {
                case "update_information" -> BoardCategory.UPDATE_INFORMATION;
                case "data_list" -> BoardCategory.DATA_LIST;
                case "data_reception" -> BoardCategory.DATA_RECEPTION;
                case "error_data_reception" -> BoardCategory.ERROR_DATA_RECEPTION;
                case "news" -> BoardCategory.NEWS;
                default -> BoardCategory.NONE;
            };
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardGetResponseDto {
        private long boardId;
        private String title;
        private String writer;
        private int viewCount;
        private LocalDate createDate;
        private List<String> fileUrls;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardDetailGetResponseDto {
        private long boardId;
        private String title;
        private String writer;
        private String content;
        private int viewCount;
        private LocalDate createDate;
        private List<String> urlList = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardPostResponseDto {
        private long boardId;

        public BoardPostResponseDto toDto() {
            return BoardPostResponseDto.builder()
                    .boardId(boardId)
                    .build();
        }
    }


}
