package com.capstone.fxteam.metal.service.secondMetal;

import com.capstone.fxteam.metal.dto.MetalDto;
import com.capstone.fxteam.metal.model.enums.ImageCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SecondMetalService {
    MetalDto.MetalPostResponseDto postMetal(MetalDto.SecondMetalPostRequestDto secondMetalPostRequestDto,
                                            Map<ImageCategory, List<String>> imageUrlsWithCategory);

    MetalDto.MetalPostResponseDto updateMetal(Long metalId,
                                              MetalDto.SecondMetalUpdateRequestDto secondMetalUpdateRequestDto,
                                              Map<ImageCategory, List<MultipartFile>> images);
}
