package example.minitable.domain;

import example.minitable.domain.store.Store;
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
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id")
    private Store store;

    @OneToOne(mappedBy = "booking")
    private Review review;

    private String criterionDate;

    private String completeYn;

    private String forcedCanceledYn;

    private int callCount;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public void setCompleteYn(String completeYn) {
        this.completeYn = completeYn;
    }

    public void setForcedCanceledYn(String forcedCanceledYn) {
        this.forcedCanceledYn = forcedCanceledYn;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public void changeUser(User user) {
        if(user!=null) {
            this.user = user;
            this.user.getBookingList().add(this);
        }
    }

    public void changeStore(Store store) {
        if(store!=null) {
            this.store = store;
            this.store.getBookingList().add(this);
        }
    }

    public Booking(User user, Store store, String criterionDate) {

        changeUser(user);
        changeStore(store);
        this.criterionDate = criterionDate;
        this.completeYn = "N";
        this.forcedCanceledYn = "N";
        this.callCount = 0;

    }


}
