package example.minitable.domain.store;

import example.minitable.domain.User;
import example.minitable.dto.SignUpStoreOwnerRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RESTAURANT")
@NoArgsConstructor
@Getter
public class Restaurant extends Store{

    private String startBreaktime;
    private String endBreaktime;
    private String pestControlYn;
    private String lastPestControlDate;

    @Override
    public void setRandomNo(String randomNo) {
        super.setRandomNo(randomNo);
    }

    public Restaurant(User user, String storeName, String address, String phone, String startTime, String endTime,
                      String startBreakTime, String endBreakTime, String pestControlYn, String lastPestControlDate) {


        super(user, storeName, address, phone, startTime, endTime);

        this.startBreaktime = startBreakTime;
        this.endBreaktime = endBreakTime;
        this.pestControlYn = pestControlYn;
        this.lastPestControlDate = lastPestControlDate;

    }

    public static Restaurant from(User user, SignUpStoreOwnerRequest dto) {
        return new Restaurant(
                user,
                dto.getStoreName(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getStartBreakTime(),
                dto.getEndBreakTime(),
                dto.getPestControlYn(),
                dto.getLastPestControlDate()
        );
    }
}
