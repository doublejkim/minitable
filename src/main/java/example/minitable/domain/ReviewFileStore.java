package example.minitable.domain;

import example.minitable.domain.store.Store;
import example.minitable.dto.file.UploadFileDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Review_File_Store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ReviewFileStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rfs_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private String realfileName;

    private String virtualfileName;

    private String ext;

    private String removeYn;

    public ReviewFileStore(User user, Review review, UploadFileDto uploadFileDto) {
        changeUser(user);
        changeReview(review);
        this.realfileName = uploadFileDto.getRealfileName();
        this.virtualfileName = uploadFileDto.getVirtualfileName();
        this.ext = uploadFileDto.getExt();
        this.removeYn = "N";
    }

    public void changeUser(User user) {
        if(user!=null) {
            this.user = user;
            //this.user.getReviewFileStores().add(this);
        }
    }

    public void changeReview(Review review) {
        if(review!=null) {
            this.review = review;
            //this.review.setReviewFileStore(this);
        }
    }

    public void setRemoveYn(String removeYn) {
        this.removeYn = removeYn;
    }

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

}
