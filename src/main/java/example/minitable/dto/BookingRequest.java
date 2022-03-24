package example.minitable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class BookingRequest {

    //@NotBlank
    //private Long storeId;

    @NotBlank
    private String randomNo;

    //@NotBlank
    //private Long userId;

}
