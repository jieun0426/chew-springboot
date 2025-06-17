package com.example.chew.storemanage.repository;

import com.example.chew.entity.BookingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from booking_3 where storecode=:storecode", nativeQuery = true)
    void deleteByStorecode(int storecode);
}
