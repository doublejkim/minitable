package example.minitable.dto;

import lombok.Data;

@Data
public class UserDto {

    private String authority;
    private String email;
    private String password;
    private String username;
    private String twitterId;
    private String gender;
    private int age;
    private String enabled;

    public UserDto(String email, String password, String username, String twitterId, String gender, int age) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.twitterId = twitterId;
        this.gender = gender;
        this.age = age;
    }

    public static UserDto from(SignUpRequest req) {
        return new UserDto(req.getEmail(), req.getPassword(), req.getUsername(), req.getTwitterId(), req.getGender(), req.getAge());
    }

    public static UserDto from(SignUpStoreOwnerRequest req) {
        return new UserDto(req.getEmail(), req.getPassword(), req.getUsername(), req.getTwitterId(), req.getGender(), req.getAge());
    }
}
