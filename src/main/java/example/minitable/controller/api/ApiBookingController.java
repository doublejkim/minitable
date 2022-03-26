package example.minitable.controller.api;

import example.minitable.dto.ApiDataResponse;
import example.minitable.dto.BookingDto;
import example.minitable.dto.SeatDto;
import example.minitable.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class ApiBookingController {

    private final BookingService bookingService;

    @GetMapping("/api/bookinglist")
    public ApiDataResponse<Page<BookingDto>> getBookingList(Pageable pageable) {

        int page = (pageable.getPageNumber() ==0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10); // @@@

        Page<BookingDto> resultPage = bookingService.getBookingList(pageable);

        return ApiDataResponse.of(resultPage);

    }

    @GetMapping("/api/bookinglist-with-email")
    public ApiDataResponse<Page<BookingDto>> getBookingListWithStoreOwnerEmail(
            @NotEmpty String email,
            Pageable pageable)
    {

        int page = (pageable.getPageNumber() ==0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10); // @@@

        Page<BookingDto> resultPage = bookingService.getBookingListWithStoreOwnerEmail(email, pageable);

        return ApiDataResponse.of(resultPage);

    }

    @PostMapping("/api/booking/seatcustomer")
    public ApiDataResponse<SeatDto> seatCustomer(
            @NotEmpty String userEmail,
            @NotEmpty String criterionDate
    ) {

        SeatDto resultDto = bookingService.seatCustomer(userEmail, criterionDate);

        return ApiDataResponse.of(resultDto);

    }
}
