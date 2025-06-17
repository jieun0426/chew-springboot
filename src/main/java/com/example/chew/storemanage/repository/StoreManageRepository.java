package com.example.chew.storemanage.repository;

import com.example.chew.dto.StoreDTO;
import com.example.chew.entity.StoreEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreManageRepository extends JpaRepository<StoreEntity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update store_3 set storename=:storename,storeaddress=:storeaddress,storecategory=:storecategory,storearea=:storearea, " +
            "parking=:parking, latitude=:latitude, longitude=:longitude, storehours=:storehours " +
            "where storecode=:storecode", nativeQuery = true)
    void updatemodi1(int storecode, String storename, String storeaddress, String storecategory, String storearea, int parking, float latitude, float longitude, String storehours);

    @Transactional
    @Modifying
    @Query(value = "update store_3 set storename=:storename,storeaddress=:storeaddress,storecategory=:storecategory,storearea=:storearea, " +
            "storeimage=:fname, parking=:parking, latitude=:latitude, longitude=:longitude, storehours=:storehours " +
            "where storecode=:storecode", nativeQuery = true)
    void updatemodi2(int storecode, String storename, String storeaddress, String storecategory, String storearea, String fname, int parking, float latitude, float longitude, String storehours);

    @Transactional
    @Modifying
    @Query(value = "select count(*) from store_3 where storename like '%'||:search||'%'", nativeQuery = true)
    int countSearchRecords(String search);

    @Transactional
    @Query(value = "select storecode, storename, storeaddress, storecategory, storeimage, storearea from ( " +
            "   select rownum rn, A.* from ( " +
            "       select storecode, storename, storeaddress, storecategory, storeimage, storearea " +
            "           from store_3 order by storecode desc " +
            "       ) A " +
            "   ) where rn between :start and :end", nativeQuery = true)
    List<StoreDTO> paging(int start, int end);

    Page<StoreEntity> findByStorenameContaining(String search, PageRequest of);
}
