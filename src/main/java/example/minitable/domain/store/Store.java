package example.minitable.domain.store;

import example.minitable.domain.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "store")
    private List<Menu> menus;

    @OneToMany(mappedBy = "store")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Booking> bookingList = new ArrayList();

    // TODO : Convenience 데이터를 만들때 관계 기술 필요 (현재는 skip)

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
        this.menus = new ArrayList<>();
    }

    public void setRandomNo(String randomNo) {
        this.randomNo = randomNo;
    }

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;


}
