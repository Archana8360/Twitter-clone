package com.example.Twitter.repositories;

import com.example.Twitter.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {


    Optional<Image> findByImageName(String imageName);
}
