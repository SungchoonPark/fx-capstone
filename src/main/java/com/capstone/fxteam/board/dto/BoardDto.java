package com.capstone.fxteam.board.dto;

import com.capstone.fxteam.board.model.Board;
import com.capstone.fxteam.board.model.enums.BoardCategory;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BoardDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardPostRequestDto {
        private String title;
        private String content;

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
    public static class BoardPostResponseDto {
        private long boardId;

        public BoardPostResponseDto toDto() {
            return BoardPostResponseDto.builder()
                    .boardId(boardId)
                    .build();
        }
    }
}
