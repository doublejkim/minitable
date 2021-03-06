package example.minitable.dto;

import com.querydsl.core.annotations.QueryProjection;
import example.minitable.domain.store.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StoreDto {

    private Long storeId;
    private String storeName;
    private String address;
    private String phone;
    private String startTime;
    private String endTime;

    private String bookingLimitYn;
    private double starAvg;
    private int reviewCnt;
    private String randomNo;

    // Restaurant 전용 필드 추가
    private String startBreaktime;
    private String endBreaktime;
    private String pestControlYn;
    private String lastPestControlDate;

    private List<MenuDto> menus = new ArrayList<>();

    @QueryProjection
    public StoreDto(Long storeId, String storeName, String address, String phone, String startTime, String endTime,
                    String bookingLimitYn, double starAvg, int reviewCnt, String randomNo, String startBreaktime, String endBreaktime,
                    String pestControlYn, String lastPestControlDate)
    {
        this.storeId = storeId;
        this.storeName = storeName;
        this.address = address;
        this.phone = phone;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingLimitYn = bookingLimitYn;
        this.starAvg = starAvg;
        this.reviewCnt = reviewCnt;
        this.randomNo = randomNo;
        this.startBreaktime = startBreaktime;
        this.endBreaktime = endBreaktime;
        this.pestControlYn = pestControlYn;
        this.lastPestControlDate = lastPestControlDate;
    }

    public static StoreDto from(Restaurant restaurant) {
        return new StoreDto(
                restaurant.getId(),
                restaurant.getStoreName(),
                restaurant.getAddress(),
                restaurant.getPhone(),
                restaurant.getStartTime(),
                restaurant.getEndTime(),
                restaurant.getBookingLimitYn(),
                restaurant.getStarAvg(),
                restaurant.getReviewCnt(),
                restaurant.getRandomNo(),
                restaurant.getStartBreaktime(),
                restaurant.getEndBreaktime(),
                restaurant.getPestControlYn(),
                restaurant.getLastPestControlDate()
        );
    }


}
