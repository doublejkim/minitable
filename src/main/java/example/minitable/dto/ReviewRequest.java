package example.minitable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {

    private String reviewTitle;
    private String reviewContents;
    private int star;
    private MultipartFile attachFile;
}
