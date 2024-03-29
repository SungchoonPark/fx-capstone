package com.capstone.fxteam.metal.controller;

import com.capstone.fxteam.chat.dto.ChatRequest;
import com.capstone.fxteam.chat.dto.ChatResponse;
import com.capstone.fxteam.constant.dto.ApiResponse;
import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.constant.exception.CustomException;
import com.capstone.fxteam.metal.dto.MetalDto;
import com.capstone.fxteam.metal.model.enums.ImageCategory;
import com.capstone.fxteam.metal.service.MetalService;
import com.capstone.fxteam.chat.service.chat.ChatService;
import com.capstone.fxteam.metal.service.firstMetal.FirstMetalService;
import com.capstone.fxteam.metal.service.image.MicroImageService;
import com.capstone.fxteam.metal.service.image.S3Service;
import com.capstone.fxteam.metal.service.secondMetal.SecondMetalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
    private final ChatService chatService;
    private final MetalService metalService;
    private final MicroImageService microImageService;

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiUrl;

    @PostMapping("/admin/first-metal")
    public ResponseEntity<ApiResponse<MetalDto.MetalPostAndUpdateResponseDto>> postFirstMetal(
            @RequestPart("firstMetalPostRequestDto") @Valid MetalDto.FirstMetalPostAndUpdateRequestDto firstMetalPostRequestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        MetalDto.MetalPostAndUpdateResponseDto metalPostResponseDto = firstMetalService.postMetal(firstMetalPostRequestDto, s3Service.uploadFile(images));

        return ResponseEntity.ok().body(ApiResponse.createSuccess(metalPostResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/admin/second-metal")
    public ResponseEntity<ApiResponse<MetalDto.MetalPostAndUpdateResponseDto>> postSecondMetal(
            @RequestPart("secondMetalPostRequestDto") @Valid MetalDto.SecondMetalPostRequestDto secondMetalPostRequestDto,
            @RequestPart("mechaFile") List<MultipartFile> mechanicalPropertiesExcelFile,
            @RequestPart("conditionImage") List<MultipartFile> conditionDiagramImage
    ) {
        Map<ImageCategory, List<String>> imageUrlsWithCategory = new HashMap<>();
        imageUrlsWithCategory.put(ImageCategory.MECHANICAL_PROPERTIES, s3Service.uploadFile(mechanicalPropertiesExcelFile));
        imageUrlsWithCategory.put(ImageCategory.CONDITION_DIAGRAM, s3Service.uploadFile(conditionDiagramImage));

        MetalDto.MetalPostAndUpdateResponseDto metalPostResponseDto = secondMetalService.postMetal(secondMetalPostRequestDto, imageUrlsWithCategory);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(metalPostResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PutMapping("/admin/first-metal/{metalId}")
    public ResponseEntity<ApiResponse<MetalDto.MetalPostAndUpdateResponseDto>> updateFirstMetal(
            @PathVariable Long metalId,
            @RequestPart("firstMetalPostRequestDto") @Valid MetalDto.FirstMetalPostAndUpdateRequestDto firstMetalPostRequestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        MetalDto.MetalPostAndUpdateResponseDto metalPostResponseDto = firstMetalService.updateMetal(metalId, firstMetalPostRequestDto, images);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(metalPostResponseDto, CustomResponseStatus.SUCCESS));
    }

    @PutMapping("/admin/second-metal/{metalId}")
    public ResponseEntity<ApiResponse<MetalDto.MetalPostAndUpdateResponseDto>> updateSecondMetal(
            @PathVariable Long metalId,
            @RequestPart(value = "secondMetalUpdateRequestDto") @Valid MetalDto.SecondMetalUpdateRequestDto secondMetalUpdateRequestDto,
            @RequestPart(value ="mechaFile", required = false) List<MultipartFile> mechanicalPropertiesExcelFile,
            @RequestPart(value ="conditionImage", required = false) List<MultipartFile> conditionDiagramImage
    ) {
        Map<ImageCategory, List<MultipartFile>> images = new HashMap<>();
        images.put(ImageCategory.MECHANICAL_PROPERTIES, mechanicalPropertiesExcelFile);
        images.put(ImageCategory.CONDITION_DIAGRAM, conditionDiagramImage);

        MetalDto.MetalPostAndUpdateResponseDto metalPostResponseDto = secondMetalService.updateMetal(metalId, secondMetalUpdateRequestDto, images);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(metalPostResponseDto, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/member/question")
    public ResponseEntity<ApiResponse<List<MetalDto.QuestionResponseDto>>> getMetalFromQuestion(@RequestParam String question){
        String refinedQuestion = chatService.getQuestion(question);
        // create a request
        ChatRequest request = new ChatRequest(model, refinedQuestion);
        // call the API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            throw new CustomException(CustomResponseStatus.GPT_NO_ANSWER);
        }
        String feature = chatService.getAccurateResponse(response);
        List<MetalDto.QuestionResponseDto> metalInfo = metalService.getMetalInfoByFeature(feature);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(metalInfo, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/admin/micro-image")
    public ResponseEntity<ApiResponse<MetalDto.MicroImagePostResponseDto>> postMicroImage(
            @RequestPart(value = "microImageRequestDto") @Valid MetalDto.MicroImagePostRequestDto requestDto,
            @RequestPart(value = "microImage") List<MultipartFile> microImage
    ) {
        MetalDto.MicroImagePostResponseDto postResponseDto = microImageService.post(requestDto, microImage);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(postResponseDto, CustomResponseStatus.SUCCESS));
    }
}
