package com.capstone.fxteam.metal.service.firstMetal;

import com.capstone.fxteam.metal.dto.MetalDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FirstMetalService {
    MetalDto.MetalPostAndUpdateResponseDto postMetal(MetalDto.FirstMetalPostAndUpdateRequestDto firstMetalPostRequestDto,
                                                     List<String> imageUrls);

    MetalDto.MetalPostAndUpdateResponseDto updateMetal(Long metalId,
                                                       MetalDto.FirstMetalPostAndUpdateRequestDto firstMetalUpdateRequestDto,
                                                       List<MultipartFile> images);
}
