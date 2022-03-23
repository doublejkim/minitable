package example.minitable.config;

import example.minitable.dto.SignUpStoreOwnerRequest;
import example.minitable.dto.StoreDto;
import example.minitable.repository.RandomRegisterRepository;
import example.minitable.service.RestaurantService;
import example.minitable.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class InitialConfig {

    private final RandomRegisterRepository randomRegisterRepository;
    private final SignUpService signUpService;
    private final RestaurantService restaurantService;

    //@Bean
    public void initStore() {

        List<String> namePrefixList
                = List.of(
                        "제일", "도레미", "강북", "지리산", "전주", "맛나", "제주", "으뜸", "쩔어", "한라산",
                        "바닷가", "산속", "부산", "전주", "영종도", "강화도", "철수네", "한라봉", "마라도", "여의도");
        List<String> namePostfixList
                = List.of(
                        " 고깃집", " 횟집", " 분식점", " 빵집", " 치킨", " 오리로스", " 백반", " 차돌박이", " 피자", " 스파게티",
                        " 닭발", " 마라탕", " 만두", " 면옥", " 한우", " 닭갈비", " 카페", " 족발", " 쌀국수", " 반점");

        List<String> addressList
                = List.of(
                        "서울", "부산", "대구", "전주", "광주", "일산", "수원", "제주", "강릉", "대전",
                        "거제", "포항", "삼척", "파주", "횡성", "서귀포", "울진", "인천", "충주", "여수");

        List<String> startTimeList = List.of("07", "08", "09", "10", "11", "12", "13", "14");
        List<String> endTimeList = List.of("20", "21", "22", "23", "24", "25");

        // d
        for (int i = 1; i <= 113; i++) {

            String email = "store" + i + "@test.com";
            String password = "store" + i;

            String username = "store" + i + " user";
            String gender = (i%2)==0 ? "men" : "woman";
            int age = i % 100;

            int prefixIdx = (int)(Math.random() * namePrefixList.size());
            int postfixIdx = (int)(Math.random() * namePostfixList.size());

            String preName = namePrefixList.get(prefixIdx);
            String postName = namePostfixList.get(postfixIdx);

            String storeName = preName + postName + i;

            int addressIdx = (int)(Math.random() * addressList.size());
            String address = addressList.get(addressIdx);

            String phone = "" + String.format("%04d", i) + String.format("%04d", i);

            int starTimeIdx = (int)(Math.random() * startTimeList.size());
            String startTime = startTimeList.get(starTimeIdx);

            int endTimeIdx = (int)(Math.random() * endTimeList.size());
            String endTime = endTimeList.get(endTimeIdx);


            SignUpStoreOwnerRequest req = new SignUpStoreOwnerRequest();
            req.setEmail(email);
            req.setPassword(password);
            req.setUsername(username);
            req.setGender(gender);
            req.setAge(age);
            req.setStoreName(storeName);
            req.setAddress(address);
            req.setPhone(phone);
            req.setStartTime(startTime);
            req.setEndTime(endTime);
            req.setPestControlYn("Y");
           // signUpReq.setEmail();


            signUpService.signupStoreOwner(req);
        }

    }

    //@Bean
    public void initMenu() {
        // Store(Restaurant) 가 미리 등록되어 있어야함

        restaurantService.initMenuRandom();

    }
}
