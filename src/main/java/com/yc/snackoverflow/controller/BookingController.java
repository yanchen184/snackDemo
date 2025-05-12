package com.yc.snackoverflow.controller;


import com.yc.snackoverflow.data.BookingData;
import com.yc.snackoverflow.data.BookingDto;
import com.yc.snackoverflow.data.CountBookingReturnData;
import com.yc.snackoverflow.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("bookings")
public class BookingController {


    private final BookingService bookingService;

    @PutMapping
    public void create(@RequestBody BookingDto bookingDto) {
        bookingService.createOrUpdate(bookingDto);
    }

    @PostMapping
    public void update(@RequestBody BookingDto bookingDto) {
        bookingService.createOrUpdate(bookingDto);
    }

    @GetMapping
    public List<BookingData> listBy(String member, Integer month) {
        return bookingService.getByMemberAndDate(member, month);
    }

    @DeleteMapping
    public void deleteBooking(@RequestBody BookingDto bookingDto) {
        bookingService.deleteBooking(bookingDto);
    }

    @GetMapping("count")
    public List<CountBookingReturnData> countBooking(Integer month, Integer maxPrice) {
        return bookingService.countBooking(month, maxPrice);
    }


    @GetMapping("/all")
    public List<BookingData> getAllBooking() {
//        return bookingService.getAllBooking();
        return null;
    }


}
