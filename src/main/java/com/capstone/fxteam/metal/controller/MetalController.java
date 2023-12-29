package com.capstone.fxteam.metal.controller;

import com.capstone.fxteam.constant.dto.ApiResponse;
import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.metal.dto.MetalDto;
import com.capstone.fxteam.metal.model.enums.ImageCategory;
import com.capstone.fxteam.metal.service.firstMetal.FirstMetalService;
import com.capstone.fxteam.metal.service.image.S3Service;
import com.capstone.fxteam.metal.service.secondMetal.SecondMetalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MetalController {
    private final FirstMetalService firstMetalService;
    private final SecondMetalService secondMetalService;
    private final S3Service s3Service;

    @PostMapping("/first-metal")
    public ResponseEntity<ApiResponse<MetalDto.MetalPostResponseDto>> postFirstMetal(
            @RequestPart("firstMetalPostRequestDto") MetalDto.FirstMetalPostRequestDto firstMetalPostRequestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        MetalDto.MetalPostResponseDto metalPostResponseDto = firstMetalService.postMetal(firstMetalPostRequestDto, s3Service.uploadFile(images));

        return ResponseEntity.ok().body(ApiResponse.createSuccess(metalPostResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/second-metal")
    public ResponseEntity<ApiResponse<MetalDto.MetalPostResponseDto>> postSecondMetal(
            @RequestPart("secondMetalPostRequestDto") MetalDto.SecondMetalPostRequestDto secondMetalPostRequestDto,
            @RequestPart("mechaImage") List<MultipartFile> mechanicalPropertiesImage,
            @RequestPart("microImage") List<MultipartFile> microstructureImage,
            @RequestPart("conditionImage") List<MultipartFile> conditionDiagramImage
    ) {
        Map<ImageCategory, List<String>> imageUrlsWithCategory = new HashMap<>();
        imageUrlsWithCategory.put(ImageCategory.MECHANICAL_PROPERTIES, s3Service.uploadFile(mechanicalPropertiesImage));
        imageUrlsWithCategory.put(ImageCategory.MICROSTRUCTURE, s3Service.uploadFile(microstructureImage));
        imageUrlsWithCategory.put(ImageCategory.CONDITION_DIAGRAM, s3Service.uploadFile(conditionDiagramImage));

        MetalDto.MetalPostResponseDto metalPostResponseDto = secondMetalService.postMetal(secondMetalPostRequestDto, imageUrlsWithCategory);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(metalPostResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PutMapping("/first-metal/{metalId}")
    public ResponseEntity<ApiResponse<MetalDto.MetalPostResponseDto>> updateFirstMetal(
            @PathVariable Long metalId,
            @RequestPart("firstMetalPostRequestDto") MetalDto.FirstMetalPostRequestDto firstMetalPostRequestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        MetalDto.MetalPostResponseDto metalPostResponseDto = firstMetalService.updateMetal(metalId, firstMetalPostRequestDto, images);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(metalPostResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PutMapping("/second-metal/{metalId}")
    public ResponseEntity<ApiResponse<MetalDto.MetalPostResponseDto>> updateSecondMetal(
            @PathVariable Long metalId,
            @RequestPart(value = "secondMetalUpdateRequestDto") MetalDto.SecondMetalUpdateRequestDto secondMetalUpdateRequestDto,
            @RequestPart(value ="mechaImage", required = false) List<MultipartFile> mechanicalPropertiesImage,
            @RequestPart(value ="microImage", required = false) List<MultipartFile> microstructureImage,
            @RequestPart(value ="conditionImage", required = false) List<MultipartFile> conditionDiagramImage
    ) {
        Map<ImageCategory, List<MultipartFile>> images = new HashMap<>();
        images.put(ImageCategory.MECHANICAL_PROPERTIES, mechanicalPropertiesImage);
        images.put(ImageCategory.MICROSTRUCTURE, microstructureImage);
        images.put(ImageCategory.CONDITION_DIAGRAM, conditionDiagramImage);

        MetalDto.MetalPostResponseDto metalPostResponseDto = secondMetalService.updateMetal(metalId, secondMetalUpdateRequestDto, images);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(metalPostResponseDto, CustomResponseStatus.SUCCESS));
    }
}
