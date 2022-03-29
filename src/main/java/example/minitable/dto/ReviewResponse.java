package example.minitable.dto;

import example.minitable.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {

    private Long reviewId;
    private Long storeId;
    private Long bookingId;
    private String reviewTitle;
    private String reviewContents;
    private int star;

    public static ReviewResponse from(Review review) {

        return new ReviewResponse(
                review.getId(),
                review.getStore().getId(),
                review.getBooking().getId(),
                review.getReviewTitle(),
                review.getReviewText(),
                review.getStar()
        );

    }

}
