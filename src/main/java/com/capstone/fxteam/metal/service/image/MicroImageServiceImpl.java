package com.capstone.fxteam.metal.service.image;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.constant.exception.CustomException;
import com.capstone.fxteam.metal.dto.MetalDto;
import com.capstone.fxteam.metal.model.SecondMetal;
import com.capstone.fxteam.metal.model.image.MicroImage;
import com.capstone.fxteam.metal.repository.SecondMetalRepository;
import com.capstone.fxteam.metal.repository.image.MicroImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MicroImageServiceImpl implements MicroImageService{
    private final MicroImageRepository microImageRepository;
    private final SecondMetalRepository secondMetalRepository;
    private final S3Service s3Service;

    @Override
    public MetalDto.MicroImagePostResponseDto post(MetalDto.MicroImagePostRequestDto postRequestDto, List<MultipartFile> image) {
        MicroImage microImage = postRequestDto.toEntity(findSecondMetalBySecondMetalName(postRequestDto.getSecondMetalName()), s3Service.uploadFile(image).get(0));

        microImageRepository.save(microImage);
        return MetalDto.MicroImagePostResponseDto.toDto(microImage.getImageTitle());
    }

    private SecondMetal findSecondMetalBySecondMetalName(String secondMetalName) {
        return secondMetalRepository.findByMetalName(secondMetalName).orElseThrow(()-> {
            throw new CustomException(CustomResponseStatus.METAL_NOT_FOUND);
        });
    }
}
