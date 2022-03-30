package example.minitable.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import example.minitable.domain.*;
import example.minitable.dto.BookingDto;
import example.minitable.dto.QBookingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;


import static example.minitable.domain.QBooking.*;
import static example.minitable.domain.QReview.*;
import static example.minitable.domain.QUser.*;
import static example.minitable.domain.store.QStore.*;


public class BookingRepositoryCustomImpl implements BookingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QUser storeUser = new QUser("storeUser");

    public BookingRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /*
    Page<Booking> findByUserAndCompleteYnAndForcedCanceledYn(
            User user,
            String completeYn,
            String forcedCanceledYn,
            Pageable pageable
    );
     */

    /*
    Customer User 기준으로 최근 1달간 예약 내역 조회
    CompleteYn : Y, ForcedCanceledYn : N 이면서 1달 내의 예약내역
     */
    @Override
    public Page<BookingDto> findBookingInfoByCustomerUser(User customerUser, LocalDateTime afterDate, Pageable pageable) {

        List<BookingDto> content = queryFactory
                .select(new QBookingDto(
                        booking.id,
                        booking.user.id,
                        booking.review.id,
                        booking.user.email,
                        booking.user.username,
                        booking.store.id,
                        booking.store.storeName,
                        booking.criterionDate,
                        booking.completeYn,
                        booking.forcedCanceledYn,
                        booking.callCount,
                        booking.createdAt,
                        booking.modifiedAt
                ))
                .from(booking)
                .join(booking.user, user)
                .join(booking.store, store)
                .leftJoin(booking.review, review)
                .where(
                        customerUserEq(customerUser),
                        createdAtGoe(afterDate),
                        booking.completeYn.eq("Y"),
                        booking.forcedCanceledYn.eq("N")
                )
                .offset(pageable.getOffset()) // 몇 번 째 부터 시작할 것인지
                .limit(pageable.getPageSize()) // 한페이지에 몇개로 제한할것인지
                .fetch();


        JPAQuery<Long> countQuery = queryFactory
                .select(booking.count())
                .from(booking)
                .join(booking.user, user)
                .join(booking.store, store)
                .leftJoin(booking.review, review)
                .where(
                        customerUserEq(customerUser),
                        createdAtGoe(afterDate),
                        booking.completeYn.eq("Y"),
                        booking.forcedCanceledYn.eq("N")
                )
                ;

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    /**
     * Owner User 가 소유한 점포에 해당하는 예약(Booking)를 조회
     * @param user
     * @param pageable
     * @return
     */
    @Override
    public Page<Booking> findBookingListByOwnerUser(User user, Pageable pageable) {

        List<Booking> content = queryFactory.select(booking)
                .from(booking)
                .join(booking.store, store)
                .where(
                        storeUserEq(user),
                        booking.completeYn.eq("N")
                        // criterionDate(기준날짜도 조건에 넣어야하지만 테스트용이성을위해 skip)
                )
                .offset(pageable.getOffset()) // 몇 번 째 부터 시작할 것인지
                .limit(pageable.getPageSize()) // 한페이지에 몇개로 제한할것인지
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(booking.count())
                .from(booking)
                .join(booking.store, store)
                .where(
                        storeUserEq(user)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    /**
     특정 유저 이메일, 과 점포소유자이메일, 예약기준날짜(criterionDate) 를 조건으로
     아직 착석처리되지 않은(CompleteYn : N) 예약 정보를 획득
     */
    @Override
    public Booking findBookingByUserEmailAndStoreOwnerEmailAndCriterionDate(
            String userEmail,
            String storeOwnerEmail,
            String criterionDate
    ) {


        Booking resultBooking = queryFactory
                .select(booking)
                .from(booking)
                .join(booking.user, user)
                .join(booking.store, store)
                .join(booking.store.user, storeUser)
                .where(
                        userEmailEq(userEmail),// userEmail
                        storeEmailEq(storeOwnerEmail),// storeOwnerEmail
                        criterionDateEq(criterionDate),// criterionDate
                        booking.completeYn.eq("N")
                )
                .fetchOne();

        return resultBooking;
    }

    private BooleanExpression createdAtGoe(LocalDateTime dateTime) {

        return dateTime == null ? null : booking.createdAt.goe(dateTime);


    }

    private BooleanExpression criterionDateEq(String criterionDate) {

        return StringUtils.hasText(criterionDate) ? booking.criterionDate.eq(criterionDate) : null;

    }

    private BooleanExpression userEmailEq(String email) {

        return StringUtils.hasText(email) ? user.email.eq(email) : null;
    }

    private BooleanExpression storeEmailEq( String email) {

        return StringUtils.hasText(email) ? storeUser.email.eq(email): null;
    }

    private BooleanExpression storeUserEq(User user) {

        return user==null ? null : store.user.eq(user);

    }

    private BooleanExpression customerUserEq(User user) {
        return user == null ? null : booking.user.eq(user);
    }
}
