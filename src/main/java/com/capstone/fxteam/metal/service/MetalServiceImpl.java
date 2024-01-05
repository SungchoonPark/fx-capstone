package com.capstone.fxteam.metal.service;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.constant.exception.CustomException;
import com.capstone.fxteam.metal.dto.MetalDto;
import com.capstone.fxteam.metal.model.FirstMetal;
import com.capstone.fxteam.metal.model.SecondMetal;
import com.capstone.fxteam.metal.model.enums.ImageCategory;
import com.capstone.fxteam.metal.repository.FirstMetalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MetalServiceImpl implements MetalService {
    private final FirstMetalRepository firstMetalRepository;

    @Override
    public List<MetalDto.QuestionResponseDto> getMetalInfoByFeature(String feature) {
        String engFeat = getFeature(feature);

        List<FirstMetal> firstMetals = firstMetalRepository.findByDeleteEnum(DeleteEnum.NOT_DELETE).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.USER_NOT_MATCH);
        });

        return firstMetals.stream()
                .map(firstMetal -> MetalDto.QuestionResponseDto.builder()
                        .metalName(firstMetal.getMetalName())
                        .metalCharacteristic(firstMetal.getMetalCharacteristic())
                        .rank(firstMetal.getFeatureRank().getRank(engFeat))
                        .firstMetalImages(firstMetal.getMetalImageUrl())
                        .secondMetalInfos(getSecondMetalInfoByFirstMetal(firstMetal.getSecondMetals(), engFeat))
                        .build())
                .collect(Collectors.toList());

    }

    private List<MetalDto.SecondMetalInfoDto> getSecondMetalInfoByFirstMetal(List<SecondMetal> secondMetals, String feature) {
        return secondMetals.stream()
                .map(secondMetal -> MetalDto.SecondMetalInfoDto.builder()
                        .metalName(secondMetal.getMetalName())
                        .metalCharacteristic(secondMetal.getMetalCharacteristic())
                        .rank(secondMetal.getFeatureRank().getRank(feature))
                        .mechaExcelUrls(secondMetal.getImageUrlsByCategory(ImageCategory.MECHANICAL_PROPERTIES))
                        .conditionImageUrls(secondMetal.getImageUrlsByCategory(ImageCategory.CONDITION_DIAGRAM))
                        .microImageInfos(getMicroImageInfos(secondMetal))
                        .build())
                .collect(Collectors.toList());
    }

    private List<MetalDto.MicroImageInfo> getMicroImageInfos(SecondMetal secondMetal) {
        return secondMetal.getMicroImages().stream()
                .map(microImage -> MetalDto.MicroImageInfo.builder()
                        .imageTitle(microImage.getImageTitle())
                        .imageCharacteristic(microImage.getImageCharacteristic())
                        .imageUrl(microImage.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    private String getFeature(String feature) {
        return switch (feature) {
            case "강도" -> "strength";
            case "연성" -> "ductility";
            case "내식성" -> "corrosionResistance";
            case "내열성" -> "heatResistance";
            case "생체적합성" -> "biocompatibility";
            case "전기전도성" -> "electricalConductivity";
            case "열전도성" -> "thermalConductivity";
            case "자성" -> "magnetism";
            case "비용절약성" -> "costEffectiveness";
            default -> "none";
        };
    }
}
