package com.capstone.fxteam.board.repository;

import com.capstone.fxteam.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
