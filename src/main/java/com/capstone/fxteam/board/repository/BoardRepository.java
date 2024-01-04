package com.capstone.fxteam.board.repository;

import com.capstone.fxteam.board.model.Board;
import com.capstone.fxteam.board.model.enums.BoardCategory;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByDeleteStatusAndBoardCategory(DeleteEnum deleteEnum, BoardCategory category);
}
