package com.capstone.fxteam.metal.service.firstMetal;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.constant.exception.CustomException;
import com.capstone.fxteam.metal.dto.MetalDto;
import com.capstone.fxteam.metal.model.FirstMetal;
import com.capstone.fxteam.metal.model.image.FirstMetalImage;
import com.capstone.fxteam.metal.repository.FirstMetalRepository;
import com.capstone.fxteam.metal.repository.image.FirstMetalImageRepository;
import com.capstone.fxteam.metal.service.image.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FirstMetalServiceImpl implements FirstMetalService {
    private final FirstMetalRepository firstMetalRepository;
    private final FirstMetalImageRepository firstMetalImageRepository;
    private final S3Service s3Service;

    @Override
    public MetalDto.MetalPostResponseDto postMetal(MetalDto.FirstMetalPostRequestDto firstMetalPostRequestDto, List<String> imageUrls) {
        FirstMetal savedFirstMetal = firstMetalRepository.save(firstMetalPostRequestDto.toEntity());

        imageUrls.forEach(v -> firstMetalImageRepository.save(FirstMetalImage.from(v, savedFirstMetal)));

        return new MetalDto.MetalPostResponseDto(savedFirstMetal.getMetalName());
    }

    @Override
    public MetalDto.MetalPostResponseDto updateMetal(Long metalId,
                                                     MetalDto.FirstMetalPostRequestDto firstMetalPostRequestDto,
                                                     List<MultipartFile> images)
    {
        FirstMetal firstMetal = firstMetalRepository.findById(metalId).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.METAL_NOT_FOUND);
        });

        firstMetal.updateFirstMetal(firstMetalPostRequestDto.getMetalName(), firstMetalPostRequestDto.getMetalCharacteristic(), firstMetalPostRequestDto.toFeatureRank());

        if(images == null) {
            return new MetalDto.MetalPostResponseDto(firstMetal.getMetalName());
        }

        // s3 버킷에서 해당 이미지 삭제
        List<String> metalImageUrls = firstMetal.getMetalImageUrl();
        for (String metalImageUrl : metalImageUrls) {
            s3Service.deleteFile(metalImageUrl);
        }
        // DB에서 해당 이미지 삭제 ( Column 값만 변경 )
        List<FirstMetalImage> metalImages = firstMetal.getMetalImages();
        for (FirstMetalImage metalImage : metalImages) {
            metalImage.delete();
        }

        // DB에 이미지 생성함
        List<String> imageUrls = s3Service.uploadFile(images);
        for (String imageUrl : imageUrls) {
            FirstMetalImage newMetalImage = FirstMetalImage.from(imageUrl, firstMetal);
            firstMetalImageRepository.save(newMetalImage);
        }

        return new MetalDto.MetalPostResponseDto(firstMetal.getMetalName());
    }
}
