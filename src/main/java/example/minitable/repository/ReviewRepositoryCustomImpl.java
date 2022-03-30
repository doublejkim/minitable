package example.minitable.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import example.minitable.domain.QReview;
import example.minitable.dto.QStoreReviewDetailsDto;
import example.minitable.dto.QStoreReviewResponse;
import example.minitable.dto.StoreReviewDetailsDto;
import example.minitable.dto.StoreReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static example.minitable.domain.QBooking.booking;
import static example.minitable.domain.QReview.*;
import static example.minitable.domain.QUser.user;
import static example.minitable.domain.store.QStore.store;

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 특정 점포의 리뷰 리스트를 조회함. 현재는 store Id 만 받아서 한달 이내의 데이터만 최근순으로 조회하도록 함
     * @param storeId
     * @return
     */
    @Override
    public Page<StoreReviewResponse> findStoreReviewListByStoreId(Long storeId, int minusDay, Pageable pageable) {

        List<StoreReviewResponse> content = queryFactory
                .select(
                        new QStoreReviewResponse(
                                review.id,
                                user.email,
                                user.username,
                                review.reviewTitle,
                                review.reviewText,
                                review.star,
                                review.createdAt
                        )
                )
                .from(review)
                .join(review.user, user)
                .where(
                        review.store.id.eq(storeId),
                        review.createdAt.goe(LocalDateTime.now().toLocalDate().atStartOfDay().minusDays(minusDay))
                )
                .offset(pageable.getOffset()) // 몇 번 째 부터 시작할 것인지
                .limit(pageable.getPageSize()) // 한페이지에 몇개로 제한할것인지
                .orderBy(review.createdAt.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .join(review.user, user)
                .where(
                        review.store.id.eq(storeId),
                        review.createdAt.goe(LocalDateTime.now().toLocalDate().atStartOfDay().minusDays(minusDay))
                );


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    /*
    review id 기준으로 리뷰 상세 정보 조회 (리뷰이미지, 유저정보, 스토어 정보 등)
     */
    @Override
    public Optional<StoreReviewDetailsDto> findStoreReviewDetails(Long review_id) {

        StoreReviewDetailsDto storeReviewDetailsDto = queryFactory
                .select(
                        new QStoreReviewDetailsDto(
                            review.id,
                                user.id,
                                user.email,
                                user.username,
                                review.reviewTitle,
                                review.reviewText,
                                review.star,
                                booking.createdAt,
                                store.storeName
                        )
                )
                .from(review)
                .join(review.user, user)
                .join(review.store, store)
                .join(review.booking, booking)
                .where(
                        review.id.eq(review_id)
                )
                .fetchOne();

        return Optional.of(storeReviewDetailsDto);
    }
}
