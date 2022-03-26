package example.minitable.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import example.minitable.domain.Booking;
import example.minitable.domain.QBooking;
import example.minitable.domain.QUser;
import example.minitable.domain.User;
import example.minitable.domain.store.QStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static example.minitable.domain.QBooking.*;
import static example.minitable.domain.QUser.*;
import static example.minitable.domain.store.QStore.*;


public class BookingRepositoryCustomImpl implements BookingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QUser storeUser = new QUser("storeUser");

    public BookingRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Booking> findBookingListByUser(User user, Pageable pageable) {

        List<Booking> content = queryFactory.select(booking)
                .from(booking)
                .join(booking.store, store)
                .where(
                        userEq(user),
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
                        userEq(user)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

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
                ).fetchOne();

        return resultBooking;
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

    private BooleanExpression userEq(User user) {

        return user==null ? null : store.user.eq(user);

    }
}
