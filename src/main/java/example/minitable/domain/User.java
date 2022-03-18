package example.minitable.domain;

import example.minitable.dto.UserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String authority;
    private String email;
    private String password;
    private String username;
    private String twitterId;
    private String gender;
    private int age;
    private String enabled;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public User(String authority, String email, String password, String username, String twitterId, String gender, int age) {
        this.authority = authority;
        this.email = email;
        this.password = password;
        this.username = username;
        this.twitterId = twitterId;
        this.gender = gender;
        this.age = age;
        this.enabled = "Y";
    }

    public static User from(UserDto userDto) {
        return new User(
                userDto.getAuthority(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getUsername(),
                userDto.getTwitterId(),
                userDto.getGender(),
                userDto.getAge()
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return "Y".equals(this.enabled);
    }
}
