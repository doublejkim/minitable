package example.minitable.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class StoreReviewDetailsDto {

    private Long reviewId;
    private Long reviewerId;
    private String reviewerEmail;
    private String reviewerName;
    private String reviewTitle;
    private String reviewText;
    private int star;
    private String bookingDate;
    private String storeName;
    private List<String> attachFiles; // virtualfile

    @QueryProjection
    public StoreReviewDetailsDto(
            Long reviewId,
            Long reviewerId,
            String reviewerEmail,
            String reviewerName,
            String reviewTitle,
            String reviewText,
            int star,
            LocalDateTime bookingDate,
            String storeName
    ) {

        this.reviewId = reviewId;
        this.reviewerId = reviewerId;
        this.reviewerEmail = reviewerEmail;
        this.reviewerName = reviewerName;
        this.reviewTitle = reviewTitle;
        this.reviewText = reviewText;
        this.star = star;
        this.bookingDate = bookingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.storeName = storeName;

        attachFiles = new ArrayList<>();
    }


}
