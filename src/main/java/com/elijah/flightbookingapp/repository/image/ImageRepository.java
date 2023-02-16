package com.elijah.flightbookingapp.repository.image;

import com.elijah.flightbookingapp.model.image.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel,String> {
}
