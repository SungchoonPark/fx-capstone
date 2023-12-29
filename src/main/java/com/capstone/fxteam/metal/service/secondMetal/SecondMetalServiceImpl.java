package com.capstone.fxteam.metal.service.secondMetal;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.constant.exception.CustomException;
import com.capstone.fxteam.metal.dto.MetalDto;
import com.capstone.fxteam.metal.model.FirstMetal;
import com.capstone.fxteam.metal.model.SecondMetal;
import com.capstone.fxteam.metal.model.enums.ImageCategory;
import com.capstone.fxteam.metal.model.image.SecondMetalImage;
import com.capstone.fxteam.metal.repository.FirstMetalRepository;
import com.capstone.fxteam.metal.repository.SecondMetalRepository;
import com.capstone.fxteam.metal.repository.image.SecondMetalImageRepository;
import com.capstone.fxteam.metal.service.image.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SecondMetalServiceImpl implements SecondMetalService {
    private final FirstMetalRepository firstMetalRepository;
    private final SecondMetalRepository secondMetalRepository;
    private final SecondMetalImageRepository secondMetalImageRepository;
    private final S3Service s3Service;

    @Override
    public MetalDto.MetalPostResponseDto postMetal(
            MetalDto.SecondMetalPostRequestDto secondMetalPostRequestDto,
            Map<ImageCategory, List<String>> imageUrlsWithCategory) {
        FirstMetal firstMetal = firstMetalRepository.findByMetalName(secondMetalPostRequestDto.getFirstMetalName())
                .orElseThrow(() -> {
                    throw new CustomException(CustomResponseStatus.METAL_NOT_FOUND);
                });

        SecondMetal savedSecondMetal = secondMetalRepository.save(secondMetalPostRequestDto.toEntity(firstMetal));

        Set<Map.Entry<ImageCategory, List<String>>> entrySet = imageUrlsWithCategory.entrySet();
        for (Map.Entry<ImageCategory, List<String>> entry : entrySet) {
            secondMetalImageRepository.save(SecondMetalImage.from(entry.getKey(), entry.getValue().get(0), savedSecondMetal));
        }

        return new MetalDto.MetalPostResponseDto(savedSecondMetal.getMetalName());
    }

    @Override
    public MetalDto.MetalPostResponseDto updateMetal(Long metalId,
                                                     MetalDto.SecondMetalUpdateRequestDto secondMetalUpdateRequestDto,
                                                     Map<ImageCategory, List<MultipartFile>> images
                                                     ) {
        SecondMetal secondMetal = secondMetalRepository.findById(metalId).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.METAL_NOT_FOUND);
        });

        secondMetal.updateSecondMetal(secondMetalUpdateRequestDto.getMetalName(), secondMetalUpdateRequestDto.getMetalCharacteristic(), secondMetalUpdateRequestDto.getFeatureRank());

        // 수정된 이미지가 없는 경우
        if (images == null) {
            return new MetalDto.MetalPostResponseDto(secondMetal.getMetalName());
        }

        // s3에서 이미지 삭제
        List<String> imageUrls = secondMetal.getImageUrls();
        for (String imageUrl : imageUrls) {
            s3Service.deleteFile(imageUrl);
        }

        // DB에서 이미지 삭제 (Soft Delete)
        List<SecondMetalImage> secondImages = secondMetal.getSecondImages();
        secondImages.forEach(SecondMetalImage::delete);

        // s3에 이미지 업로드 후 DB에 생성
        Set<Map.Entry<ImageCategory, List<MultipartFile>>> entrySet = images.entrySet();
        for (Map.Entry<ImageCategory, List<MultipartFile>> entry : entrySet) {
            List<String> urls = s3Service.uploadFile(entry.getValue());
            for (String url : urls) {
                secondMetalImageRepository.save(SecondMetalImage.from(entry.getKey(), url, secondMetal));
            }
        }

        return new MetalDto.MetalPostResponseDto(secondMetal.getMetalName());
    }
}
