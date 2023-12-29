package com.capstone.fxteam.metal.dto;

import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.metal.model.FeatureRank;
import com.capstone.fxteam.metal.model.FirstMetal;
import com.capstone.fxteam.metal.model.SecondMetal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MetalDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FirstMetalPostRequestDto {
        private String metalName;
        private String metalCharacteristic;
        private FeatureRank featureRank;

        public FirstMetal toEntity() {
            return FirstMetal.builder()
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
    public static class SecondMetalPostRequestDto {
        private String firstMetalName;
        private String metalName;
        private String metalCharacteristic;
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
    public static  class MetalPostResponseDto {
        private String metalName;

        public static MetalPostResponseDto toDto(String metalName) {
            return MetalPostResponseDto.builder()
                    .metalName(metalName)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SecondMetalUpdateRequestDto {
        private String metalName;
        private String metalCharacteristic;
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
}
