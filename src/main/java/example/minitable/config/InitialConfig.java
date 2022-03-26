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

    /*
    테스트용 더미데이터등록을 위한 빈 메소드. 어플리케이션 최초 1번만 실행후 Bean으로 등록되지 않게 해야함
     */
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
                        "강원 원주시 개운동",
                "강원 원주시 단구동",
                "강원 춘천지 운교동",
                "강원 춘천시 후평동",
                "경기 고양시 일산동구 장항동",
                "경기 광명시 광명3동",
                "경기 군포시 산본동",
                "경기 부천시 원미구 도당동",
                "경기 부천시 원미구 상동",
                "경기 성남시 분당구 구미동",
                "경기 성남시 분당구 수내동",
                "경기 수원시 영통구 매탄동",
                "경기 시흥시 대야동",
                "경기 안산시 단원구 고잔동",
                "경기 안산시 상록구 본오동",
                "경기 안산시 동안구 비산동",
                "경기 안산시 만안구 안양동",
                "경기 의정부시 의정부1동천",
                "경기 평택시 평택동",
                "경남 거제시 고현동",
                "경남 양산시 중부동",
                "경남 진주시 대안동",
                "경북 구미시 원편동",
                "서울시 강남구 신사동",
                "서울시 강남구 역삼1동",
                "서울시 강서구 화곡동",
                "서울시 관악구 신림동",
                "서울시 동대문구 청량리동",
                "서울시 마포구 서교동",
                "서울시 영등포구 당산동5가",
                "서울시 은평구 불광동",
                "인천 남구 용현5동",
                "인천 연수구 송도동",
                "전남 순천시 연향동",
                "전북 익산시 영등동",
                "전남 목포시 상동",
                "전북 전주시 완산구 평화동1가",
                "제주 제주시 이도1동",
                "충남 당진군 당진읍",
                "충남 서산시 동문동"
                );

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

            String storeName = preName + postName + " " + i;

            int addressIdx = (int)(Math.random() * addressList.size());
            String address = addressList.get(addressIdx);

            String phone = getRegionNum(address) + String.format("%04d", i) + "-" + String.format("%04d", i);

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

        // Restaurant  등록후, 메뉴 정보 등록
        initMenu();
    }


    public void initMenu() {

        // Store(Restaurant) 가 미리 등록되어 있어야함
        for(int i=0; i<5; i++)
            restaurantService.initMenuRandom();

    }

    private String getRegionNum(String address) {

        address = address.trim();

        if(address.startsWith("서울")) {
            return "02-";
        } else if(address.startsWith("인천")) {
            return "032-";
        } else if(address.startsWith("세종")) {
            return "044-";
        } else if(address.startsWith("대전")) {
            return "042-";
        } else if (address.startsWith("대구")) {
            return "053-";
        } else if (address.startsWith("울산")) {
            return "052-";
        } else if (address.startsWith("부산")) {
            return "051-";
        } else if (address.startsWith("광주")) {
            return "062-";
        } else if (address.startsWith("경기")) {
            return "032-";
        } else if (address.startsWith("강원")) {
            return "033-";
        } else if (address.startsWith("충북")) {
            return "043-";
        } else if (address.startsWith("충남")) {
            return "041-";
        } else if (address.startsWith("경북")) {
            return "054-";
        } else if (address.startsWith("전북")) {
            return "063-";
        } else if (address.startsWith("경남")) {
            return "061-";
        } else if (address.startsWith("제주")) {
            return "064-";
        } else {
            return "000-";
        }

    }
}
