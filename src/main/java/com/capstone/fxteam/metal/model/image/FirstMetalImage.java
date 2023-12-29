package com.capstone.fxteam.metal.model.image;

import com.capstone.fxteam.constant.entity.BaseEntity;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.metal.model.FirstMetal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FirstMetalImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long firstMetalImageId;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private DeleteEnum deleteEnum;

    @ManyToOne
    @JoinColumn(name = "firstMetalId")
    private FirstMetal firstMetal;

    private FirstMetalImage(String imageUrl, FirstMetal firstMetal) {
        this.imageUrl = imageUrl;
        this.firstMetal = firstMetal;
        this.deleteEnum = DeleteEnum.NOT_DELETE;
    }

    public static FirstMetalImage from(String imageUrl, FirstMetal firstMetal) {
        return new FirstMetalImage(imageUrl, firstMetal);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void delete() {
        this.deleteEnum = DeleteEnum.DELETE;
    }
}
