package com.capstone.fxteam.metal.model;

import com.capstone.fxteam.constant.entity.BaseEntity;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.metal.model.enums.ImageCategory;
import com.capstone.fxteam.metal.model.image.SecondMetalImage;
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
public class SecondMetal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long secondMetalId;

    private String metalName;

    @Column(columnDefinition = "LONGTEXT")
    private String metalCharacteristic;

    @Enumerated(EnumType.STRING)
    private DeleteEnum deleteEnum;

    @ManyToOne
    @JoinColumn(name = "firstMetalId")
    private FirstMetal firstMetal;

    @OneToMany(mappedBy = "secondMetal", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<SecondMetalImage> secondMetalImages = new ArrayList<>();

    @Embedded
    private FeatureRank featureRank;

    public void updateSecondMetal(String metalName, String metalCharacteristic, FeatureRank featureRank) {
        this.metalName = metalName;
        this.metalCharacteristic = metalCharacteristic;
        this.featureRank = featureRank.updateRank(featureRank);
    }

    public List<String> getImageUrlsByCategory(ImageCategory category) {
        return this.secondMetalImages.stream()
                .filter(image -> image.getCategory() == category)
                .map(SecondMetalImage::getImageUrl)
                .toList();
    }

    public void delete() {
        this.deleteEnum = DeleteEnum.DELETE;
    }

    public List<String> getImageUrls() {
        List<String> imageUrl = new ArrayList<>();
        secondMetalImages.forEach(images -> imageUrl.add(images.getImageUrl()));
        return imageUrl;
    }
}
