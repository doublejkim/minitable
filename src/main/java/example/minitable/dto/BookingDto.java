package example.minitable.dto;

import example.minitable.domain.Booking;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BookingDto {

    private Long bookingId;
    private Long userId;
    private String userEmail;
    private String userName;
    private String userPhone;

    private Long storeId;
    private String criterionDate;
    private String completeYn;
    private String forcedCanceledYn;

    private int callCount;
    private String createdAt;

    public BookingDto (
            Long bookingId,
            Long userId,
            String userEmail,
            String userName,
            Long storeId,
            String criterionDate,
            String completeYn,
            String forcedCanceledYn,
            int callCount,
            LocalDateTime createdAt
    ) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.storeId = storeId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.criterionDate = criterionDate;
        this.completeYn = completeYn;
        this.forcedCanceledYn = forcedCanceledYn;
        this.callCount = callCount;
        this.createdAt =  createdAt.format(DateTimeFormatter.ofPattern("a hh:mm"));



    }

    public static BookingDto from(Booking booking) {

        return new BookingDto(
                booking.getId(),
                booking.getUser().getId(),
                booking.getUser().getEmail(),
                booking.getUser().getUsername(),
                booking.getStore().getId(),
                booking.getCriterionDate(),
                booking.getCompleteYn(),
                booking.getForcedCanceledYn(),
                booking.getCallCount(),
                booking.getCreatedAt()
        );

    }

}
