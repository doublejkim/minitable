package example.minitable.domain.store;

import example.minitable.domain.RandomRegister;
import example.minitable.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String storeName;
    private String address;
    private String phone;
    private String startTime;
    private String endTime;

    private String bookingLimitYn;
    private double starAvg;
    private String randomNo;

    public Store (User user, String storeName, String address, String phone, String startTime, String endTime) {

        this.user = user;
        this.storeName = storeName;
        this.address = address;
        this.phone = phone;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingLimitYn = "N";
        this.starAvg = 0.0;
        this.randomNo =  null;  // 임의의 문자열 등록 필요
    }

    public void setRandomNo(String randomNo) {
        this.randomNo = randomNo;
    }

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;


}
