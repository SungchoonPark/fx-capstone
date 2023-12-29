package com.capstone.fxteam.metal.repository.image;

import com.capstone.fxteam.metal.model.image.FirstMetalImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FirstMetalImageRepository extends JpaRepository<FirstMetalImage, Long> {
    Optional<FirstMetalImage> findByImageUrl(String imageUrl);
}
