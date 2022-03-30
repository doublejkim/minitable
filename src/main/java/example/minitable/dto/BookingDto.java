package example.minitable.dto;

import com.querydsl.core.annotations.QueryProjection;
import example.minitable.domain.Booking;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BookingDto {

    private Long bookingId;
    private Long userId;
    private Long reviewId;
    private String userEmail;
    private String userName;
    private String userPhone;

    private Long storeId;
    private String storeName;
    private String criterionDate;
    private String completeYn;
    private String forcedCanceledYn;

    private int callCount;
    private String createdAt; // 예약등록 시간
    private String modifiedAt; // completeYn:Y, forcedNcanceledYn:N 일때의 modifiedAt 의시간정보는. 착석완료되었을때의 시간임

    private boolean reviewable;

    @QueryProjection
    public BookingDto(
            Long bookingId,
            Long userId,
            Long reviewId,
            String userEmail,
            String userName,
            Long storeId,
            String storeName,
            String criterionDate,
            String completeYn,
            String forcedCanceledYn,
            int callCount,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this(
                bookingId,
                userId,
                userEmail,
                userName,
                storeId,
                storeName,
                criterionDate,
                completeYn,
                forcedCanceledYn,
                callCount,
                createdAt,
                modifiedAt
        );

        this.reviewId = reviewId;
    }



    public BookingDto (
            Long bookingId,
            Long userId,
            String userEmail,
            String userName,
            Long storeId,
            String storeName,
            String criterionDate,
            String completeYn,
            String forcedCanceledYn,
            int callCount,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.userEmail = userEmail;
        this.userName = userName;
        this.criterionDate = criterionDate;
        this.completeYn = completeYn;
        this.forcedCanceledYn = forcedCanceledYn;
        this.callCount = callCount;
        this.createdAt =  createdAt.format(DateTimeFormatter.ofPattern("a hh:mm"));
        this.modifiedAt = modifiedAt.format(DateTimeFormatter.ofPattern("a hh:mm"));

        // 7일 이내의 예약내역에 관해서는 리뷰 등록/수정 가능 하도록 설정
        if(Duration.between(createdAt.toLocalDate().atStartOfDay(), LocalDateTime.now().toLocalDate().atStartOfDay()).toDays() <= 7) {
            this.reviewable = true;
        } else {
            this.reviewable = false;
        }

    }

    public static BookingDto from(Booking booking) {

        return new BookingDto(
                booking.getId(),
                booking.getUser().getId(),
                booking.getUser().getEmail(),
                booking.getUser().getUsername(),
                booking.getStore().getId(),
                booking.getStore().getStoreName(),
                booking.getCriterionDate(),
                booking.getCompleteYn(),
                booking.getForcedCanceledYn(),
                booking.getCallCount(),
                booking.getCreatedAt(),
                booking.getModifiedAt()
        );

    }

}
