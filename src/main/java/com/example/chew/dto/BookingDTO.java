package com.example.chew.dto;

import com.example.chew.entity.BookingEntity;
import com.example.chew.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDTO {
    int tablenum;
    int storecode;
    String id;
    int saramsu;
    String state;
    Date bookingdate;
    String bookingtime;

    public BookingEntity entity(){
        return BookingEntity.builder()
                .tablenum(tablenum)
                .store(StoreEntity.builder().storecode(storecode).build())
                .id(id)
                .saramsu(saramsu)
                .state(state)
                .bookingdate(bookingdate)
                .bookingtime(bookingtime)
                .build();
    }
}
