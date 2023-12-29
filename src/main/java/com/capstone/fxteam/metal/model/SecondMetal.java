package com.capstone.fxteam.metal.model;

import com.capstone.fxteam.constant.entity.BaseEntity;
import com.capstone.fxteam.constant.enums.DeleteEnum;
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

    private String metalCharacteristic;

    @Enumerated(EnumType.STRING)
    private DeleteEnum deleteEnum;

    @ManyToOne
    @JoinColumn(name = "firstMetalId")
    private FirstMetal firstMetal;

    @OneToMany(mappedBy = "secondMetal", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<SecondMetalImage> secondImages = new ArrayList<>();

    @Embedded
    private FeatureRank featureRank;

    public void updateSecondMetal(String metalName, String metalCharacteristic, FeatureRank featureRank) {
        this.metalName = metalName;
        this.metalCharacteristic = metalCharacteristic;
        this.featureRank = featureRank.updateRank(featureRank);
    }

    public void delete() {
        this.deleteEnum = DeleteEnum.DELETE;
    }

    public List<String> getImageUrls() {
        List<String> imageUrl = new ArrayList<>();
        secondImages.forEach(images -> imageUrl.add(images.getImageUrl()));
        return imageUrl;
    }
}
