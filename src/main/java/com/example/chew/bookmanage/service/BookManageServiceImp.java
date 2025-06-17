package com.example.chew.bookmanage.service;

import com.example.chew.bookmanage.repository.BookManageRepository;
import com.example.chew.entity.BookingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookManageServiceImp implements BookManageService {
    @Autowired
    BookManageRepository bookManageRepository;

    @Override
    public Page<BookingEntity> allList(int page) {
        return bookManageRepository.findAll(PageRequest.of(page, 10));
    }

    @Override
    public void updateBooking(BookingEntity entity) {
        bookManageRepository.updateBooking(entity.getTablenum(), entity.getSaramsu(),
                entity.getState(), entity.getBookingdate(), entity.getBookingtime());
    }

    @Override
    public BookingEntity detailOne(int tablenum) {
        return bookManageRepository.findById(tablenum).orElse(null);
    }

    @Override
    public Page<BookingEntity> searchList(int page, String search) {
        return bookManageRepository.findByIdContaining(search, PageRequest.of(page, 10));
    }

    @Override
    public void deleteBookingByTablenum(int tablenum) {
        bookManageRepository.deleteById(tablenum);
    }
}
