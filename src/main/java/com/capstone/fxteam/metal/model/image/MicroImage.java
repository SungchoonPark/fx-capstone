package com.capstone.fxteam.metal.model.image;

import com.capstone.fxteam.constant.entity.BaseEntity;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.metal.model.SecondMetal;
import com.capstone.fxteam.metal.model.enums.ImageCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MicroImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long microImageId;

    private String imageTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String imageCharacteristic;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private DeleteEnum deleteEnum;

    @ManyToOne
    @JoinColumn(name = "secondMetalId")
    private SecondMetal secondMetal;

    public void delete() {
        this.deleteEnum = DeleteEnum.DELETE;
    }
}
