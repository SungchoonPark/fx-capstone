package com.capstone.fxteam.board.model;

import com.capstone.fxteam.board.model.enums.BoardCategory;
import com.capstone.fxteam.constant.entity.BaseEntity;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.member.model.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
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
}
