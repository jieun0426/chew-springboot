package com.example.chew.DetailBook;

import com.example.chew.entity.BookingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailBookImpl implements DetailBookService {

    @Autowired
    DetailBookRepository detailBookRepository;

    @Override
    public void saveBooking(BookingEntity bookingEntity) {
        detailBookRepository.save(bookingEntity);
    }
}
