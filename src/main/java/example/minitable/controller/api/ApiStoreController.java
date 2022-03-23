package example.minitable.controller.api;

import example.minitable.dto.ApiDataResponse;
import example.minitable.dto.StoreDto;
import example.minitable.dto.condition.StoreSearchCondition;
import example.minitable.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiStoreController {

    private final RestaurantService restaurantService;

    @GetMapping("/api/stores")
    public ApiDataResponse<Page<StoreDto>> getStores(StoreSearchCondition searchCondition, Pageable pageable) {

        int page = (pageable.getPageNumber() ==0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10); // @@@

        // TODO : 1) 컨트롤러개발 : 전체 업체 조회
        Page<StoreDto> resultPage  = restaurantService.getStores(searchCondition, pageable);

        return ApiDataResponse.of(resultPage);  // 차후에 store 조회하여 페이징할수있는 store 의 리스트를 조회하도록 변경
    }

    @GetMapping("/api/stores/{storeId}")
    public String getStore(@PathVariable String storeId) {
        log.info("  >>> GET getStore() called!!!!!!!!!, storeId : " + storeId);
        return "store1 - detail";  // 전달받은 storeId 를 기준으로 store 상세 정보 조회
    }

    @PutMapping("/api/stores/{storeId}")
    public Boolean modifyStore(@PathVariable String storeId) {
        log.info("  >>> PUT getStores() modifyStore!!!!!!!!!, storeId : " + storeId);
        return true; // 전달받은 storeId 를 기준으로 store 정보 수정
    }

    @DeleteMapping("/api/stores/{storeId}")
    public Boolean deleteStore(@PathVariable String storeId) {
        log.info("  >>> DELETE deleteStore() modifyStore!!!!!!!!!, storeId : " + storeId);
        return true;
    }
}
