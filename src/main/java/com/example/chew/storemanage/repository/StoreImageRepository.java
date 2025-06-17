package com.example.chew.storemanage.repository;

import com.example.chew.entity.StoreImageEntity;
import com.example.chew.entity.StoreImageId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreImageRepository extends JpaRepository<StoreImageEntity, StoreImageId> {
    @Transactional
    @Query(value = "select * from store_image_3 where storecode=:storecode", nativeQuery = true)
    List<StoreImageEntity> findImages(@Param("storecode") int storecode);

    @Transactional
    @Modifying
    @Query(value = "delete from store_image_3 where storecode=:storecode", nativeQuery = true)
    void deleteDetailImages(int storecode);

    @Transactional
    @Modifying
    @Query(value = "update store_image_3 set image_filename=:newFilename where storecode=:storecode and image_filename=:oldFilename", nativeQuery = true)
    void updateDetailImage(int storecode, String newFilename, String oldFilename);

    @Transactional
    @Modifying
    @Query(value = "insert into store_image_3 values(:storecode, :newFilename)", nativeQuery = true)
    void insert(int storecode, String newFilename);
}
