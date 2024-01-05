package com.capstone.fxteam.metal.service.image;

import com.capstone.fxteam.metal.dto.MetalDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MicroImageService {

    MetalDto.MicroImagePostResponseDto post(MetalDto.MicroImagePostRequestDto postRequestDto, List<MultipartFile> image);
}
