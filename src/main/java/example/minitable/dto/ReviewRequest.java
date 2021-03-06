package example.minitable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {

    private Long reviewId; // 수정할때만 셋팅
    private Long storeId;
    private Long bookingId;
    private String reviewTitle;
    private String reviewContents;
    private int star;
    private MultipartFile attachFile;
}
