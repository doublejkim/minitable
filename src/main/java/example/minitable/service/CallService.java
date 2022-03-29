package example.minitable.service;

import example.minitable.domain.User;
import example.minitable.dto.MailDto;
import example.minitable.repository.UserRepository;
import example.minitable.service.contact.MailContactServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallService {

    private final UserRepository userRepository;

    private final MailContactServiceImpl mailContactService;

    public boolean callToCustomer(String userEmail) {

        User user = userRepository.findByEmail(userEmail);

        // 입력받은 email 로 검색되는 유저정보가 존재하지 않음
        if(user==null) {
            return false;  // 혹은 차후 에 예외 발생해서 종료시키는것도 고려
        }

        // 1) Email 전송
        // test email 일 경우에는 로그만 발생시키고 skip

        if(isTestEmail(user.getEmail())) {

            log.debug("     >>> 입장 준비 메시지 전송이 완료되었습니다. 해당 메일은 테스트 메일 계정입니다. email : " + user.getEmail());

        } else {

            MailDto mailDto = MailDto.from(user.getEmail(), "입장 준비 부탁드립니다", generateCallMessage(user));
            mailContactService.send(mailDto);
        }


        // 2) 다른 연락수단 전송 필요. 현재 erd 설계상 twitter id 가 존재하는데, telegram 으로 변경 계획중


        return true;
    }


    private String generateCallMessage(User user) {

        StringBuffer sb = new StringBuffer();

        sb.append(user.getUsername());
        sb.append(" (");
        sb.append(user.getEmail());
        sb.append(" ) 님 순서가 되었습니다. 입장 준비해주세요.");

        return sb.toString();

    }

    private boolean isTestEmail(String email) {

        return  email.contains("test");

    }

}
