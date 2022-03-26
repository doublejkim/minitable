package example.minitable.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailDto {

    private String receiver;
    private String title;
    private String message;

    public MailDto(String receiver, String title, String message) {
        this.receiver = receiver;
        this.title = title;
        this.message = message;
    }

    public static MailDto from(String receiver, String title, String message){
        return new MailDto(receiver, title, message);
    }
}
