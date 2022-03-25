package example.minitable.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import example.minitable.domain.Booking;
import example.minitable.domain.QBooking;
import example.minitable.domain.User;
import example.minitable.domain.store.QStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static example.minitable.domain.QBooking.*;
import static example.minitable.domain.store.QStore.*;


public class BookingRepositoryCustomImpl implements BookingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookingRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Booking> findBookingListByUser(User user, Pageable pageable) {

        List<Booking> content = queryFactory.select(booking)
                .from(booking)
                .join(booking.store, store)
                .where(
                        userEq(user)
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

    private BooleanExpression userEq(User user) {

        return user==null ? null : store.user.eq(user);

    }
}
