package com.capstone.fxteam.metal.service;

import com.capstone.fxteam.metal.dto.MetalDto;

import java.util.List;

public interface MetalService {
    List<MetalDto.QuestionResponseDto> getMetalInfoByFeature(String feature);

}
