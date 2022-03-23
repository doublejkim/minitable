package example.minitable.controller;

import example.minitable.dto.StoreDto;
import example.minitable.dto.condition.StoreSearchCondition;
import example.minitable.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
업체(Store) 의 경우 현재는 식당(Restaurant) 형태만 가정하고 개발 했지만,
차후 다른 종류의 업체가 생긴다면 StoreController 에서 타입을 구분하여 각 xxxController 를 호출하는
형태로 개발 예정.
현재는 StoreController 에서 RestaurantService 를 바로 이용하도록 한다.
 */
@Controller
@RequiredArgsConstructor
@Validated
@Slf4j
public class StoreController {

    private final RestaurantService restaurantService;


    @GetMapping("/stores")
    public ModelAndView getStores(StoreSearchCondition searchCondition, Pageable pageable) {
        log.info("  >>> GET getStores() called!!!!!!!!!");
        Map<String, Object> map = new HashMap<>();

        int page = (pageable.getPageNumber() ==0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10); // @@@

        // TODO : 1) 컨트롤러개발 : 전체 업체 조회
        Page<StoreDto> resultPage  = restaurantService.getStores(searchCondition, pageable);

        map.put("stores", resultPage);
        //map.put("maxPage", 10);

        return new ModelAndView("stores/index", map);
    }

    @GetMapping("stores/{storeId}")
    public ModelAndView getStore(@PathVariable String storeId) {
        log.info("  >>> GET getStore() called!!!!!!!!!, storeId : " + storeId);
        //return "store1 - detail";  // 전달받은 storeId 를 기준으로 store 상세 정보 조회
        Map<String, Object> map = new HashMap<>();





        return new ModelAndView("stores/details", map);
    }



}
