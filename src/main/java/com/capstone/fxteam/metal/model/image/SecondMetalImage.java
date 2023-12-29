package com.capstone.fxteam.metal.model.image;

import com.capstone.fxteam.constant.entity.BaseEntity;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.metal.model.SecondMetal;
import com.capstone.fxteam.metal.model.enums.ImageCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SecondMetalImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long secondMetalImageId;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ImageCategory category;

    @Enumerated(EnumType.STRING)
    private DeleteEnum deleteEnum;

    @ManyToOne
    @JoinColumn(name = "secondMetalId")
    private SecondMetal secondMetal;

    private SecondMetalImage(ImageCategory category, String imageUrl, SecondMetal secondMetal) {
        this.category = category;
        this.imageUrl = imageUrl;
        this.secondMetal = secondMetal;
        this.deleteEnum = DeleteEnum.NOT_DELETE;
    }

    public static SecondMetalImage from(ImageCategory category, String imageUrl, SecondMetal secondMetal) {
        return new SecondMetalImage(category, imageUrl, secondMetal);
    }

    public void delete() {
        this.deleteEnum = DeleteEnum.DELETE;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}