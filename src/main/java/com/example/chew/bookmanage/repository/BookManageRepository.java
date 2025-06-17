package com.example.chew.bookmanage.repository;

import com.example.chew.entity.BookingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface BookManageRepository extends JpaRepository<BookingEntity, Integer>, JpaSpecificationExecutor<BookingEntity> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE booking_3 " +
            "       SET " +
            "           SARAMSU = :saramsu, " +
            "           STATE = :state, " +
            "           BOOKINGDATE = :bookingdate, " +
            "           BOOKINGTIME = :bookingtime " +
            "       WHERE tablenum = :tablenum ", nativeQuery = true)
    void updateBooking(int tablenum, int saramsu, String state, Date bookingdate, String bookingtime);

    Page<BookingEntity> findByIdContaining(String search, PageRequest of);
}
