package com.capstone.fxteam.metal.repository.image;

import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.metal.model.enums.ImageCategory;
import com.capstone.fxteam.metal.model.image.SecondMetalImage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecondMetalImageRepository extends JpaRepository<SecondMetalImage, Long> {
//    List<String> findImageUrlByMetalIdAndDeleteAndCategory(
//            @Param("metalId") String metalId,
//            @Param("delete") DeleteEnum deleteEnum,
//            @Param("category") ImageCategory category
//    );
}
