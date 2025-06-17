package com.example.chew.Image;

import com.example.chew.entity.StoreImageEntity;
import com.example.chew.entity.StoreImageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<StoreImageEntity, StoreImageId> {

    List<StoreImageEntity> findById_Storecode(int storecode);
}
