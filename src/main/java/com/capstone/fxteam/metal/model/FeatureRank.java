package com.capstone.fxteam.metal.model;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.constant.exception.CustomException;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureRank {

    private int strength;

    private int ductility;

    private int corrosionResistance;

    private int heatResistance;

    private int biocompatibility;

    private int electricalConductivity;

    private int thermalConductivity;

    private int magnetism;

    private int costEffectiveness;

    public FeatureRank updateRank(FeatureRank featureRank) {
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

    public int getRank(String feature) {
        return switch (feature) {
            case "strength" -> this.getStrength();
            case "ductility" -> this.getDuctility();
            case "corrosionResistance" -> this.getCorrosionResistance();
            case "heatResistance" -> this.getHeatResistance();
            case "biocompatibility" -> this.getBiocompatibility();
            case "electricalConductivity" -> this.getElectricalConductivity();
            case "thermalConductivity" -> this.getThermalConductivity();
            case "magnetism" -> this.getMagnetism();
            case "costEffectiveness" -> this.getCostEffectiveness();
            default -> throw new CustomException(CustomResponseStatus.METAL_NOT_FOUND);
        };
    }
}
