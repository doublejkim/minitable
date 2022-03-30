package example.minitable.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import example.minitable.domain.QMenu;
import example.minitable.domain.store.QRestaurant;
import example.minitable.dto.QStoreDto;
import example.minitable.dto.StoreDto;
import example.minitable.dto.condition.StoreSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static example.minitable.domain.store.QRestaurant.*;

public class RestaurantRepositoryCustomImpl implements RestaurantRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public RestaurantRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<StoreDto> getStores(StoreSearchCondition condition, Pageable pageable) {

        List<StoreDto> content =  queryFactory
                .select(
                        new QStoreDto(
                                restaurant.id.as("storeId"),
                                restaurant.storeName,
                                restaurant.address,
                                restaurant.phone,
                                restaurant.startTime,
                                restaurant.endTime,
                                restaurant.bookingLimitYn,
                                restaurant.starAvg,
                                restaurant.reviewCnt,
                                restaurant.randomNo,
                                restaurant.startBreaktime,
                                restaurant.endBreaktime,
                                restaurant.pestControlYn,
                                restaurant.lastPestControlDate
                        )
                )
                .from(restaurant)
                .where(
                        storeNameLike(condition.getStoreName()),
                        phoneLike(condition.getPhone())
                )
                .offset(pageable.getOffset()) // 몇 번 째 부터 시작할 것인지
                .limit(pageable.getPageSize()) // 한페이지에 몇개로 제한할것인지
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(restaurant.count())
                .from(restaurant)
                .where(
                        storeNameLike(condition.getStoreName()),
                        phoneLike(condition.getPhone())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression storeNameLike(String storeName) {
        return StringUtils.hasText(storeName) ? restaurant.storeName.contains(storeName) : null;
    }

    private BooleanExpression phoneLike(String phone) {
        return StringUtils.hasText(phone) ? restaurant.phone.contains(phone) : null;
    }
}
