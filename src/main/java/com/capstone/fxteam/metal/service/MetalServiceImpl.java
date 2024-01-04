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

import java.util.ArrayList;
import java.util.List;

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

        List<MetalDto.QuestionResponseDto> questionResponseDtos = new ArrayList<>();
        for (FirstMetal firstMetal : firstMetals) {
            questionResponseDtos.add(MetalDto.QuestionResponseDto.builder()
                    .metalName(firstMetal.getMetalName())
                    .metalCharacteristic(firstMetal.getMetalCharacteristic())
                    .rank(firstMetal.getFeatureRank().getRank(engFeat))
                    .firstMetalImages(firstMetal.getMetalImageUrl())
                    .secondMetalInfos(getSecondMetalInfoByFirstMetal(firstMetal.getSecondMetals(), engFeat))
                    .build());
        }

        return questionResponseDtos;
    }

    private List<MetalDto.SecondMetalInfoDto> getSecondMetalInfoByFirstMetal(List<SecondMetal> secondMetals, String feature) {
        List<MetalDto.SecondMetalInfoDto> secondMetalInfoDtos = new ArrayList<>();

        for (SecondMetal secondMetal : secondMetals) {
            secondMetalInfoDtos.add(MetalDto.SecondMetalInfoDto.builder()
                            .metalName(secondMetal.getMetalName())
                            .metalCharacteristic(secondMetal.getMetalCharacteristic())
                            .rank(secondMetal.getFeatureRank().getRank(feature))
                            .microImageUrls(secondMetal.getImageUrlsByCategory(ImageCategory.MICROSTRUCTURE))
                            .mechaExcelUrls(secondMetal.getImageUrlsByCategory(ImageCategory.MECHANICAL_PROPERTIES))
                            .conditionImageUrls(secondMetal.getImageUrlsByCategory(ImageCategory.CONDITION_DIAGRAM))
                    .build());
        }

        return secondMetalInfoDtos;
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
