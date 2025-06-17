package com.example.chew.storemanage.service;

import com.example.chew.dto.StoreDTO;
import com.example.chew.dto.StoreImageDTO;
import com.example.chew.entity.StoreEntity;
import com.example.chew.entity.StoreImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreManageService {
    void insertstore(StoreEntity storeEntity);

    StoreEntity selectOneStore(int storecode);

    void deleteReviewsByStorecode(int storecode);

    void deleteBookingByStorecode(int storecode);

    void deletelikesByStorecode(int storecode);

    List<StoreImageEntity> detailImages(int storecode);

    void deleteDetailImages(int storecode);

    void deleteStore(int storecode); // 가게 삭제

    void updatemodi1(int storecode, String storename, String storeaddress, String storecategory, String storearea, int parking, float latitude, float longitude, String storehours);

    void updatemodi2(int storecode, String storename, String storeaddress, String storecategory, String storearea, String fname, int parking, float latitude, float longitude, String storehours);

    void updateDetailImage(int storecode, String newFilename, String oldFilename);

    void insertDetailImage(int storecode, String newFilename);

    int countSearchRecords(String search);

    List<StoreDTO> paging(int start, int end);

    StoreImageDTO selectOneDetailImage(int storecode);

    List<StoreEntity> searchList(int start, int end, String search);

    Page<StoreEntity> list(int page);

    Page<StoreEntity> searchList(int page, String search);
}
