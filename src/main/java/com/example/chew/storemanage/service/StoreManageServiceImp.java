package com.example.chew.storemanage.service;

import com.example.chew.dto.StoreDTO;
import com.example.chew.dto.StoreImageDTO;
import com.example.chew.entity.StoreEntity;
import com.example.chew.entity.StoreImageEntity;
import com.example.chew.storemanage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreManageServiceImp implements StoreManageService {
    @Autowired
    StoreManageRepository storeManageRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    MemberLikesRepository memberLikesRepository;
    @Autowired
    StoreImageRepository storeImageRepository;

    @Override
    public void insertstore(StoreEntity storeEntity) {
        storeManageRepository.save(storeEntity);
    }

    @Override
    public StoreEntity selectOneStore(int storecode) {
        return storeManageRepository.findById(storecode).orElse(null);
    }

    @Override
    public void deleteReviewsByStorecode(int storecode) {
        reviewRepository.deleteById(storecode);
    }

    @Override
    public void deleteBookingByStorecode(int storecode) {
        bookingRepository.deleteByStorecode(storecode);
    }

    @Override
    public void deletelikesByStorecode(int storecode) {
        memberLikesRepository.deleteById(storecode);
    }

    @Override
    public List<StoreImageEntity> detailImages(int storecode) {
        return storeImageRepository.findImages(storecode);
    }

    @Override
    public void deleteDetailImages(int storecode) {
        storeImageRepository.deleteDetailImages(storecode);
    }

    @Override
    public void deleteStore(int storecode) {
        storeManageRepository.deleteById(storecode);
    }

    @Override
    public void updatemodi1(int storecode, String storename, String storeaddress, String storecategory, String storearea, int parking, float latitude, float longitude, String storehours) {
        storeManageRepository.updatemodi1(storecode, storename, storeaddress, storecategory, storearea, parking, latitude, longitude, storehours);
    }

    @Override
    public void updatemodi2(int storecode, String storename, String storeaddress, String storecategory, String storearea, String fname, int parking, float latitude, float longitude, String storehours) {
        storeManageRepository.updatemodi2(storecode, storename, storeaddress, storecategory, storearea, fname, parking, latitude, longitude, storehours);
    }

    @Override
    public void updateDetailImage(int storecode, String newFilename, String oldFilename) {
        storeImageRepository.updateDetailImage(storecode, newFilename, oldFilename);
    }

    @Override
    public void insertDetailImage(int storecode, String newFilename) {
        storeImageRepository.insert(storecode,newFilename);
    }

    @Override
    public int countSearchRecords(String search) {
        return storeManageRepository.countSearchRecords(search);
    }

    @Override
    public List<StoreDTO> paging(int start, int end) {
        return storeManageRepository.paging(start,end);
    }

    @Override
    public StoreImageDTO selectOneDetailImage(int storecode) {
        return null;
    }

    @Override
    public List<StoreEntity> searchList(int start, int end, String search) {
        return List.of();
    }

    @Override
    public Page<StoreEntity> list(int page) {
        return storeManageRepository.findAll(PageRequest.of(page, 7, Sort.by(Sort.Direction.DESC, "storecode")));
    }

    @Override
    public Page<StoreEntity> searchList(int page, String search) {
        return storeManageRepository.findByStorenameContaining(search, PageRequest.of(page, 7));
    }
}
