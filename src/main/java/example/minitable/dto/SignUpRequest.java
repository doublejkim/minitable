package example.minitable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {

    private String authority;
    private String email;
    private String password;
    private String username;
    private String twitterId;
    private String gender;
    private int age;

}
