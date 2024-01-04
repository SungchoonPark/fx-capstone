package com.capstone.fxteam.metal.repository;

import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.metal.model.FirstMetal;
import com.capstone.fxteam.metal.repository.querydsl.MetalRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FirstMetalRepository extends JpaRepository<FirstMetal, Long>, MetalRepositoryCustom {
    Optional<FirstMetal> findByMetalName(String metalName);
    Optional<List<FirstMetal>> findByDeleteEnum(DeleteEnum deleteEnum);
}
