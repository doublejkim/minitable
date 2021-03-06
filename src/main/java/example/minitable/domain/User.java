package example.minitable.domain;

import example.minitable.domain.store.Store;
import example.minitable.dto.UserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    @OneToOne(mappedBy = "user")
    private Store store;

    @OneToMany(mappedBy = "user")
    List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Booking> bookingList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<ReviewFileStore> reviewFileStores = new ArrayList<>();

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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "Y".equals(this.enabled);
    }

    @Override
    public String toString() {
        return "(email : " + email + ", name : " + username + ", twitId : " + twitterId + ", gender : " + gender + ", age : " + age + ")";
    }
}
