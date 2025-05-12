package com.yc.snackoverflow.controller;

import com.yc.snackoverflow.data.BookingData;
import com.yc.snackoverflow.data.BookingDto;
import com.yc.snackoverflow.data.CountBookingReturnData;
import com.yc.snackoverflow.enums.UpsertStatusEnum;
import com.yc.snackoverflow.handler.ResultData;
import com.yc.snackoverflow.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
@Tag(name = "Booking Management", description = "API endpoints for booking management")
public class BookingController {


    private final BookingService bookingService;

    /**
     * Create a new booking
     */
    @Operation(summary = "Create booking", description = "Create a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResultData<UpsertStatusEnum> createBooking(@Validated @RequestBody BookingDto bookingDto) {
        String productInfo = "none";
        if (bookingDto.getBookingDetailDtoList() != null && !bookingDto.getBookingDetailDtoList().isEmpty()) {
            productInfo = bookingDto.getBookingDetailDtoList().get(0).getProductName();
        }
        log.info("Creating new booking for member: {}, product: {}", bookingDto.getMemberName(), productInfo);
        UpsertStatusEnum status = bookingService.createOrUpdate(bookingDto);
        return ResultData.success("Booking created successfully", status);
    }

    /**
     * Update an existing booking
     */
    @Operation(summary = "Update booking", description = "Update an existing booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/{id}")
    public ResultData<UpsertStatusEnum> updateBooking(
            @PathVariable Long id,
            @Validated @RequestBody BookingDto bookingDto) {
        log.info("Updating booking with ID: {}", id);
        bookingDto.setId(id); // Ensure ID matches path variable
        UpsertStatusEnum status = bookingService.createOrUpdate(bookingDto);
        return ResultData.success("Booking updated successfully", status);
    }

    /**
     * Get bookings by member and month
     */
    @Operation(summary = "Get bookings", description = "Get bookings filtered by member name and month")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResultData<List<BookingData>> getBookings(
            @Parameter(description = "Member name for filtering")
            @RequestParam(required = false) String member,
            @Parameter(description = "Month for filtering (1-12)")
            @RequestParam(required = false) Integer month) {
        log.info("Getting bookings for member: {}, month: {}", member, month);
        List<BookingData> bookings = bookingService.getByMemberAndDate(member, month);
        return ResultData.success(bookings);
    }

    /**
     * Get a booking by ID
     */
    @Operation(summary = "Get booking by ID", description = "Get a specific booking by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    public ResultData<BookingData> getBookingById(
            @Parameter(description = "Booking ID")
            @PathVariable Long id) {
        log.info("Getting booking with ID: {}", id);
        // We'll need to implement this method in the service
        // For now, let's assume the service can return a single booking by ID
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(id);
        List<BookingData> bookings = bookingService.getByMemberAndDate(null, null);
        BookingData booking = bookings.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return ResultData.success(booking);
    }

    /**
     * Delete a booking
     */
    @Operation(summary = "Delete booking", description = "Delete a booking by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    public ResultData<Void> deleteBooking(
            @Parameter(description = "Booking ID")
            @PathVariable Long id) {
        log.info("Deleting booking with ID: {}", id);
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(id);
        bookingService.deleteBooking(bookingDto);
        return ResultData.success("Booking deleted successfully", null);
    }

    /**
     * Count bookings by month and max price
     */
    @Operation(summary = "Count bookings", description = "Count bookings by month and max price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/count")
    public ResultData<List<CountBookingReturnData>> countBooking(
            @Parameter(description = "Month for filtering (1-12)")
            @RequestParam(required = false) Integer month,
            @Parameter(description = "Maximum price for filtering")
            @RequestParam(required = false) Integer maxPrice) {
        log.info("Counting bookings for month: {}, max price: {}", month, maxPrice);
        List<CountBookingReturnData> countData = bookingService.countBooking(month, maxPrice);
        return ResultData.success(countData);
    }

    /**
     * Get all bookings
     */
    @Operation(summary = "Get all bookings", description = "Get all bookings in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/all")
    public ResultData<List<BookingData>> getAllBookings() {
        log.info("Getting all bookings");
        List<BookingData> allBookings = bookingService.getByMemberAndDate(null, null);
        return ResultData.success(allBookings);
    }


}
