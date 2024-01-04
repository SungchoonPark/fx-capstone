package com.capstone.fxteam.metal.repository;

import com.capstone.fxteam.metal.model.FirstMetal;
import com.capstone.fxteam.metal.model.SecondMetal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SecondMetalRepository extends JpaRepository<SecondMetal, Long> {
    Optional<List<SecondMetal>> findByFirstMetal(FirstMetal firstMetal);
}
