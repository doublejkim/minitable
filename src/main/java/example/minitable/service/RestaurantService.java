package example.minitable.service;

import example.minitable.constant.ErrorCode;
import example.minitable.domain.Menu;
import example.minitable.domain.store.Restaurant;
import example.minitable.dto.MenuDto;
import example.minitable.dto.StoreDto;
import example.minitable.dto.condition.StoreSearchCondition;
import example.minitable.exception.GeneralException;
import example.minitable.repository.MenuRepository;
import example.minitable.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public Page<StoreDto> getStores(StoreSearchCondition condition, Pageable pageable) {

        return restaurantRepository.getStores(condition, pageable);

    }

    public StoreDto getStore(Long storeId) {

        Restaurant restaurant = restaurantRepository
                .findById(storeId).orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

        StoreDto storeDto = StoreDto.from(restaurant);

        List<MenuDto> menuList = restaurant.getMenus().stream().map(MenuDto::from).collect(Collectors.toList());
        storeDto.setMenus(menuList);

        return storeDto;

    }


    // test 용...
    @Transactional
    public void initMenuRandom() {

        List<Restaurant> restaurantList = restaurantRepository.findAll();


        List<String> prefixMenu = List.of(
                "해물", "소고기", "돼지고기", "얼큰", "개운", "닭고기", "영양", "자연식", "건강식", "양고기",
                "해장", "달콤", "매콤", "매운", "갈치", "오리", "깔끔", "국물많은", "자작한", "맛있는");

        List<String> postfixMenu = List.of(
                " 국수", " 쌀국수", " 볶음", " 구이", " 불고기", " 찜", " 로스", " 통나무구이", " 백반", " 정식",
                " 비빔", " 탕", " 전통요리", " 퓨전요리", " 중화식요리", " 일식요리", " 하와이식요리", " 태국식요리", " 인도식요리", " 미국식요리"
        );

        List<String> descList = List.of(
                "특급 비법으로 만들어 맛있습니다", "해장에 좋은 영양소가 많아 숙취에 좋습니다",
                "건강에 좋은 재료들만 엄선하여 만들었습니다.건강에 좋습니다",
                "자극적이지 않아 어린이에게 좋습니다",
                "특급 상품들로만 요리하여 영양소가 풍부해요",
                "직접 엄선한 재료로 요리한메뉴입니다",
                "재료 본연의 맛을 잘 살린 음식입니다",
                "해당 요리의 원조방식으로 잘 조리했습니다"
                );

        int cnt = 0;
        for(Restaurant restaurant : restaurantList) {


            int prefixMenuIdx = (int)(Math.random() * prefixMenu.size());
            int postfixMenuIdx = (int)(Math.random() * postfixMenu.size());
            int descIdx = (int)(Math.random() * descList.size());

            Menu menu = new Menu(restaurant, prefixMenu.get(prefixMenuIdx) + postfixMenu.get(postfixMenuIdx), descList.get(descIdx));

            menuRepository.save(menu);
            cnt++;
        }

    }


}
