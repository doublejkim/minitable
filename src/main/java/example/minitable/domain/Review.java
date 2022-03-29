package example.minitable.domain;

import example.minitable.domain.store.Store;
import example.minitable.dto.ReviewRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(mappedBy = "review")
    private ReviewFileStore reviewFileStore;

    private LocalDateTime bookingAt;

    private int star;
    private String reviewTitle;
    private String reviewText;
    private String removeYn;

    public void setStar(int star) {
        this.star = star;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public void setReviewFileStore(ReviewFileStore reviewFileStore) {
        this.reviewFileStore = reviewFileStore;
    }

    public void changeUser(User user) {
        this.user = user;
        //this.user.getReviews().add(this);
    }

    public void changeBooking(Booking booking) {
        if(booking!=null) {
            this.booking = booking;
            this.booking.setReview(this);
        }
    }

    public void changeStore(Store store) {
        if(store!=null) {
            this.store = store;
          //  this.store.getReviews().add(this);
        }

    }

    public Review(User user, Booking booking, Store store,
                  ReviewRequest reviewRequest) {

        this.changeUser(user);
        this.changeBooking(booking);
        this.changeStore(store);

        this.reviewTitle = reviewRequest.getReviewTitle();
        this.reviewText = reviewRequest.getReviewContents();
        this.star = reviewRequest.getStar();
        this.removeYn = "N";
    }

    public static Review of(User user, Booking booking, Store store, ReviewRequest reviewRequest) {

        return new Review(user, booking, store, reviewRequest);
    }
}
