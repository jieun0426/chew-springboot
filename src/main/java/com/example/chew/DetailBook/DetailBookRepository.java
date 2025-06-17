package com.example.chew.DetailBook;

import com.example.chew.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailBookRepository extends JpaRepository<BookingEntity,Integer> {


}
