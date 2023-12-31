package com.capstone.fxteam.board.repository;

import com.capstone.fxteam.board.model.files.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
}
