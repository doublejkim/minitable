package example.minitable.service;

import example.minitable.constant.ErrorCode;
import example.minitable.domain.Booking;
import example.minitable.domain.Review;
import example.minitable.domain.ReviewFileStore;
import example.minitable.domain.User;
import example.minitable.domain.store.Restaurant;
import example.minitable.dto.ReviewRequest;
import example.minitable.dto.ReviewResponse;
import example.minitable.dto.StoreReviewDetailsDto;
import example.minitable.dto.StoreReviewResponse;
import example.minitable.dto.file.UploadFileDto;
import example.minitable.exception.GeneralException;
import example.minitable.repository.BookingRepository;
import example.minitable.repository.RestaurantRepository;
import example.minitable.repository.ReviewFileStoreRepository;
import example.minitable.repository.ReviewRepository;
import example.minitable.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final RestaurantRepository restaurantRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewFileStoreRepository reviewFileStoreRepository;

    private final FileUtil fileUtil;

    /**
     * 리뷰 생성 서비스
     * @param reviewRequest storeId, bookingId 정보
     * @return true : 성공, false : 실패
     */
    @Transactional
    public boolean createReviewDo(ReviewRequest reviewRequest) {

        // 유저 정보 획득
        // CUSTOMER 권한 유저 정보
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Store(Restaurant) 정보 획득
        Restaurant restaurant = restaurantRepository.findById(reviewRequest.getStoreId()).orElse(null);
        if(restaurant==null) {
            return false;  // view 에서 응답이 필요하므로 일단 false 리턴. 차후 api 호출이 되면 exception throw 하자
        }

        // star 계산
        restaurant.calcStarAvg(reviewRequest.getStar());

        // Booking 정보 획득
        Booking booking = bookingRepository.findById(reviewRequest.getBookingId()).orElse(null);
        if (booking == null) {
            return false;
        }

        Review review = new Review(user, booking, restaurant, reviewRequest);
        reviewRepository.save(review);

        UploadFileDto uploadFileDto = null;
        try {

            uploadFileDto = fileUtil.storeFile(reviewRequest.getAttachFile());

        } catch (IOException e) {

            log.error("파일저장시 IOException 발생 : {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_ERROR);
        }

        ReviewFileStore reviewFileStore = new ReviewFileStore(user, review, uploadFileDto);
        reviewFileStoreRepository.save(reviewFileStore);

        return true;
    }

    /*
    이전에 작성했던 리뷰 내용 획득 하는 서비스 메소드
     */
    public ReviewResponse getMyReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new GeneralException(ErrorCode.NOT_FOUND));

        return ReviewResponse.from(review);

    }

    /**
     * 등록했던 리뷰 내용 수정
     */
    @Transactional
    public boolean modifyReviewDo(ReviewRequest reviewRequest) {

        Review review = reviewRepository.getById(reviewRequest.getReviewId());

        review.setReviewTitle(reviewRequest.getReviewTitle());
        review.setReviewText(reviewRequest.getReviewContents());
        review.setStar(reviewRequest.getStar());

        return true;
    }

    /**
     * 특정 점포 (Store) 의 리뷰 리스트 조회
     * @param storeId
     * @param pageable
     * @return
     */
    public Page<StoreReviewResponse> getStoreReviewList(Long storeId, Pageable pageable) {

        // 30 일 이전 데이터만 가져오도록 가정
        return reviewRepository.findStoreReviewListByStoreId(storeId, 30, pageable);

    }

    public StoreReviewDetailsDto getStoreReviewDetails(Long reviewId) {

        StoreReviewDetailsDto resultDto =  reviewRepository.findStoreReviewDetails(reviewId)
               .orElseThrow(()->new GeneralException(ErrorCode.INTERNAL_ERROR));


        log.debug("      >>> 파일 찾아 보자  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        List<ReviewFileStore> resultList = reviewFileStoreRepository.findByReviewId(reviewId);


        resultList.forEach(value ->{
            resultDto.getAttachFiles().add(value.getVirtualfileName());
        });

        return resultDto;
    }

}
