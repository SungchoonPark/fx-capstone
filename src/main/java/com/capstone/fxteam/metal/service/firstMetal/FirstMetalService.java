package com.capstone.fxteam.metal.service.firstMetal;

import com.capstone.fxteam.metal.dto.MetalDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FirstMetalService {
    public MetalDto.MetalPostResponseDto postMetal(MetalDto.FirstMetalPostRequestDto firstMetalPostRequestDto,
                            List<String> imageUrls);

    public MetalDto.MetalPostResponseDto updateMetal(Long metalId,
                                                     MetalDto.FirstMetalPostRequestDto firstMetalPostRequestDto,
                                                     List<MultipartFile> images);
}
