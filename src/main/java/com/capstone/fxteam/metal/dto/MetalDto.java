package com.capstone.fxteam.metal.dto;

import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.metal.model.FeatureRank;
import com.capstone.fxteam.metal.model.FirstMetal;
import com.capstone.fxteam.metal.model.SecondMetal;

import com.capstone.fxteam.metal.model.image.MicroImage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MetalDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FirstMetalPostAndUpdateRequestDto {
        @NotBlank(message = "금속명이 공백입니다.")
        private String metalName;
        @NotBlank(message = "금속특징이 공백입니다.")
        private String metalCharacteristic;
        @NotBlank(message = "금속분류특징이 공백입니다.")
        private String metalClassCharacteristic;
        @NotBlank(message = "물성 순위가 공백입니다.")
        private FeatureRank featureRank;

        public FirstMetal toEntity() {
            return FirstMetal.builder()
                    .metalName(metalName)
                    .metalCharacteristic(metalCharacteristic)
                    .metalClassCharacteristic(metalClassCharacteristic)
                    .featureRank(toFeatureRank())
                    .deleteEnum(DeleteEnum.NOT_DELETE)
                    .build();
        }

        public FeatureRank toFeatureRank() {
            return FeatureRank.builder()
                    .strength(featureRank.getStrength())
                    .ductility(featureRank.getDuctility())
                    .corrosionResistance(featureRank.getCorrosionResistance())
                    .heatResistance(featureRank.getHeatResistance())
                    .biocompatibility(featureRank.getBiocompatibility())
                    .electricalConductivity(featureRank.getElectricalConductivity())
                    .thermalConductivity(featureRank.getThermalConductivity())
                    .magnetism(featureRank.getMagnetism())
                    .costEffectiveness(featureRank.getCostEffectiveness())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SecondMetalPostRequestDto {
        @NotBlank(message = "1차 금속명이 공백입니다.")
        private String firstMetalName;
        @NotBlank(message = "금속명이 공백입니다.")
        private String metalName;
        @NotBlank(message = "금속특징이 공백입니다.")
        private String metalCharacteristic;
        @NotBlank(message = "물성 순위가 공백입니다.")
        private FeatureRank featureRank;

        public SecondMetal toEntity(FirstMetal firstMetal) {
            return SecondMetal.builder()
                    .firstMetal(firstMetal)
                    .metalName(metalName)
                    .metalCharacteristic(metalCharacteristic)
                    .featureRank(toFeatureRank())
                    .deleteEnum(DeleteEnum.NOT_DELETE)
                    .build();
        }

        public FeatureRank toFeatureRank() {
            return FeatureRank.builder()
                    .strength(featureRank.getStrength())
                    .ductility(featureRank.getDuctility())
                    .corrosionResistance(featureRank.getCorrosionResistance())
                    .heatResistance(featureRank.getHeatResistance())
                    .biocompatibility(featureRank.getBiocompatibility())
                    .electricalConductivity(featureRank.getElectricalConductivity())
                    .thermalConductivity(featureRank.getThermalConductivity())
                    .magnetism(featureRank.getMagnetism())
                    .costEffectiveness(featureRank.getCostEffectiveness())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetalPostAndUpdateResponseDto {
        private String metalName;

        public static MetalPostAndUpdateResponseDto toDto(String metalName) {
            return MetalPostAndUpdateResponseDto.builder()
                    .metalName(metalName)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SecondMetalUpdateRequestDto {
        @NotBlank(message = "금속명이 공백입니다.")
        private String metalName;
        @NotBlank(message = "금속특징이 공백입니다.")
        private String metalCharacteristic;
        @NotBlank(message = "물성 순위가 공백입니다.")
        private FeatureRank featureRank;

        public SecondMetal toEntity(FirstMetal firstMetal) {
            return SecondMetal.builder()
                    .firstMetal(firstMetal)
                    .metalName(metalName)
                    .metalCharacteristic(metalCharacteristic)
                    .featureRank(toFeatureRank())
                    .deleteEnum(DeleteEnum.NOT_DELETE)
                    .build();
        }

        public FeatureRank toFeatureRank() {
            return FeatureRank.builder()
                    .strength(featureRank.getStrength())
                    .ductility(featureRank.getDuctility())
                    .corrosionResistance(featureRank.getCorrosionResistance())
                    .heatResistance(featureRank.getHeatResistance())
                    .biocompatibility(featureRank.getBiocompatibility())
                    .electricalConductivity(featureRank.getElectricalConductivity())
                    .thermalConductivity(featureRank.getThermalConductivity())
                    .magnetism(featureRank.getMagnetism())
                    .costEffectiveness(featureRank.getCostEffectiveness())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionResponseDto {
        private String metalName;
        private String metalCharacteristic;
        private int rank;
        private String metalClassCharacteristic;
        private List<String> firstMetalImages;
        private List<SecondMetalInfoDto> secondMetalInfos;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SecondMetalInfoDto {
        private String metalName;
        private String metalCharacteristic;
        private int rank;
        private List<String> conditionImageUrls;
        private List<String> mechaExcelUrls;
        private List<MicroImageInfo> microImageInfos;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MicroImageInfo {
        private String imageTitle;
        private String imageCharacteristic;
        private String imageUrl;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MicroImagePostRequestDto {
        @NotBlank(message = "2차 금속명이 공백입니다.")
        private String secondMetalName;
        @NotBlank(message = "미세조직 이미지 제목이 공백입니다.")
        private String imageTitle;
        @NotBlank(message = "미세조직 특징이 공백입니다.")
        private String imageCharacteristic;

        public MicroImage toEntity(SecondMetal secondMetal, String imageUrl) {
            return MicroImage.builder()
                    .imageTitle(imageTitle)
                    .imageCharacteristic(imageCharacteristic)
                    .deleteEnum(DeleteEnum.NOT_DELETE)
                    .secondMetal(secondMetal)
                    .imageUrl(imageUrl)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MicroImagePostResponseDto {
        private String imageTitle;

        public static MicroImagePostResponseDto toDto(String imageTitle) {
            return MicroImagePostResponseDto.builder()
                    .imageTitle(imageTitle)
                    .build();
        }
    }

}
