package com.yc.snackoverflow.data;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Data
public class BookingDto {

    private Long id;

    private String memberName;

    private Date date;

    private List<BookingDetailDto> bookingDetailDtoList;
}
