package com.yc.snackoverflow.service.impl;

import com.yc.snackoverflow.data.BookingData;
import com.yc.snackoverflow.data.BookingDetailDto;
import com.yc.snackoverflow.data.BookingDto;
import com.yc.snackoverflow.data.CountBookingReturnData;
import com.yc.snackoverflow.enums.UpsertStatusEnum;
import com.yc.snackoverflow.model.Booking;
import com.yc.snackoverflow.model.BookingDetail;
import com.yc.snackoverflow.model.Member;
import com.yc.snackoverflow.model.Product;
import com.yc.snackoverflow.reposity.BookingDao;
import com.yc.snackoverflow.service.BookingService;
import com.yc.snackoverflow.service.MemberService;
import com.yc.snackoverflow.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;

    private final MemberService memberService;

    private final ProductService productService;

    @Override
    public UpsertStatusEnum createOrUpdate(BookingDto bookingDto) {

        Member member = memberService.list(List.of(bookingDto.getMemberName())).get(0);
        Map<String, Product> productMap = productService.list(bookingDto.getBookingDetailDtoList()
                .stream()
                .map(BookingDetailDto::getProductName)
                .distinct()
                .toList())
                .stream()
                .collect(Collectors.toMap(Product::getName, Function.identity(), (existing, replacement) -> existing));

        Booking booking = Booking.builder()
                .member(member)
                .date(bookingDto.getDate())
                .build();

        List<BookingDetail> bookingDetails = new ArrayList<>();
        for(BookingDetailDto bookingData : bookingDto.getBookingDetailDtoList()){
            BookingDetail bookingDetail = BookingDetail.builder()
                    .booking(booking)
                    .count(bookingData.getCount())
                    .priority(bookingData.getPriority())
                    .product(productMap.get(bookingData.getProductName()))
                    .build();
            bookingDetails.add(bookingDetail);
        }

//        int createOrUpdate = bookingDao.saveOrUpdate(booking);
//        int createOrUpdate = bookingDao.saveOrUpdate(booking);
//        log.info("memberDao.saveOrUpdateMember(booking) return {}", UpsertStatusEnum.lookup(createOrUpdate));
//        return UpsertStatusEnum.lookup(createOrUpdate)
//                .orElseThrow(WebErrorEnum.UPSERT_FAILED::exception);
        return null;
    }

    @Override
    public List<Booking> list(List<String> productNameList) {
        return null;
    }

    @Override
    public List<BookingData> getByMemberAndDate(String memberName, Integer date) {
        return null;
    }

    @Override
    public List<BookingData> getByMember(String memberName) {
        return null;
    }

    @Override
    public void deleteBooking(BookingDto member) {

    }

    @Override
    public List<CountBookingReturnData> countBooking(Integer date, Integer maxPrice) {
        return null;
    }
}