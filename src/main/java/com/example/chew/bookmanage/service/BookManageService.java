package com.example.chew.bookmanage.service;

import com.example.chew.entity.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BookManageService {
    Page<BookingEntity> allList(int page);

    void updateBooking(BookingEntity entity);

    BookingEntity detailOne(int tablenum);

    Page<BookingEntity> searchList(int page, String search);

    void deleteBookingByTablenum(int tablenum);
}
