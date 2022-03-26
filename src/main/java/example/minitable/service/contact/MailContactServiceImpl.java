package example.minitable.service.contact;

import example.minitable.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * 이메일 전송이 필요한 경우 아래와같이 서비스 클래스 구현후 전송하면 될듯
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailContactServiceImpl implements Contactable{

    private final JavaMailSender mailSender;

    public void send(MailDto mailDto) {
        send(mailDto.getReceiver(), mailDto.getTitle(), mailDto.getMessage());
    }

    @Override
    public void send(String receiver, String title, String message) {

        // 수신자, 제목, 메시지 설정후 메일전송
        log.debug("     >>> send email!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! to : " + receiver);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(message);

        mailSender.send(simpleMailMessage);
    }


}
