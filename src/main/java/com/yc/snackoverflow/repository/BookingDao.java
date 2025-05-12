package com.yc.snackoverflow.reposity;

import com.yc.snackoverflow.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingDao extends JpaRepository<Booking, Long> {

//    @Modifying
//    @Query(value = "INSERT INTO BOOKING (date,count ) VALUES (:name, :price, :picture )" +
//            " ON DUPLICATE KEY UPDATE NAME = :name, PRICE = :price, PICTURE = :picture", nativeQuery = true)
//    int saveOrUpdate(String name, int price, String picture);
//
//    default int saveOrUpdate(Booking booking) {
//        return saveOrUpdate(booking.get(), booking.getPrice(), booking.getPicture());
//    }
//    @Modifying
//    @Query(value = "INSERT INTO BOOKING (memberId,date ) VALUES (:memberId, :date  )" +
//            " ON DUPLICATE KEY UPDATE memberId = :memberId, date = :date", nativeQuery = true)
//    int saveOrUpdate(Long memberId, Date date);
//
//    default int saveOrUpdate(Booking booking) {
//        return saveOrUpdate(booking.getMember().getId(), booking.getDate());
//    }

    @Query(value = "SELECT * FROM BOOKING WHERE (:bookingDaoList IS NULL OR NAME IN (:bookingDaoList))", nativeQuery = true)
    List<BookingDao> list(List<String> bookingDaoList);
}

