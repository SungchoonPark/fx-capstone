package com.capstone.fxteam.board.model;

import com.capstone.fxteam.board.model.enums.BoardCategory;
import com.capstone.fxteam.board.model.files.BoardFile;
import com.capstone.fxteam.constant.entity.BaseEntity;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.member.model.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Slf4j
public class Board extends BaseEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long boardId;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private DeleteEnum deleteStatus;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    private int viewCount;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<BoardFile> boardFiles;

    public List<String> getBoardFileUrl() {
        List<String> urls = new ArrayList<>();
        this.boardFiles.forEach(boardFile -> urls.add(boardFile.getFileUrl()));
        return urls;
    }

    public void deleteBoard() {
        this.deleteStatus = DeleteEnum.DELETE;
        this.boardFiles.forEach(BoardFile::deleteBoardFile);
    }
}
