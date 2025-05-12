package com.yc.snackoverflow.service;

import com.yc.snackoverflow.data.BookingData;
import com.yc.snackoverflow.data.BookingDto;
import com.yc.snackoverflow.data.CountBookingReturnData;
import com.yc.snackoverflow.enums.UpsertStatusEnum;
import com.yc.snackoverflow.model.Booking;

import java.util.List;

public interface BookingService {
    UpsertStatusEnum createOrUpdate(BookingDto bookingDto);

    List<Booking> list(List<String> productNameList);

    List<BookingData> getByMemberAndDate(String memberName, Integer date);

    List<BookingData> getByMember(String memberName);

    void deleteBooking(BookingDto member);

    List<CountBookingReturnData> countBooking(Integer date, Integer maxPrice);

}
