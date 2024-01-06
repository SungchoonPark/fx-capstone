package com.capstone.fxteam.metal.model;

import com.capstone.fxteam.constant.entity.BaseEntity;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.metal.model.image.FirstMetalImage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FirstMetal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long firstMetalId;

    private String metalName;
    @Column(columnDefinition = "LONGTEXT")
    private String metalCharacteristic;

    @Column(columnDefinition = "LONGTEXT")
    private String metalClassCharacteristic;

    @Enumerated(EnumType.STRING)
    private DeleteEnum deleteEnum;

    @Embedded
    private FeatureRank featureRank;

    @OneToMany(mappedBy = "firstMetal", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<SecondMetal> secondMetals = new ArrayList<>();

    @OneToMany(mappedBy = "firstMetal", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<FirstMetalImage> metalImages = new ArrayList<>();

    public List<String> getMetalImageUrl() {
        List<String> imageUrls = new ArrayList<>();
        metalImages.forEach(image -> imageUrls.add(image.getImageUrl()));
        return imageUrls;
    }

    public void updateFirstMetal(String metalName, String metalCharacteristic, FeatureRank featureRank) {
        this.metalName = metalName;
        this.metalCharacteristic = metalCharacteristic;
        this.featureRank = featureRank.updateRank(featureRank);
    }

    public void delete() {
        this.deleteEnum = DeleteEnum.DELETE;
    }
}
