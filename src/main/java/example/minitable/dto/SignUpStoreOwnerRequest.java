package example.minitable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpStoreOwnerRequest {

    private String authority;
    private String email;
    private String password;
    private String username;
    private String twitterId;
    private String gender;
    private int age;

    private String storeName;
    private String address;
    private String phone;
    private String startTime;
    private String endTime;
    private String startBreakTime;
    private String endBreakTime;
    private String pestControlYn;
    private String lastPestControlDate;

}
