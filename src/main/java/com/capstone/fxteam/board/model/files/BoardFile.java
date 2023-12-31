package com.capstone.fxteam.board.model.files;

import com.capstone.fxteam.board.model.Board;
import com.capstone.fxteam.constant.entity.BaseEntity;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardFileId;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private DeleteEnum deleteEnum;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    private BoardFile(String fileUrl, Board board) {
        this.fileUrl = fileUrl;
        this.board = board;
        deleteEnum = DeleteEnum.NOT_DELETE;
    }

    public static BoardFile from(String fileUrl, Board board) {
        return new BoardFile(fileUrl, board);
    }

    public void deleteBoardFile() {
        this.deleteEnum = DeleteEnum.DELETE;
    }
}
