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


    public Restaurant(User user, String storeName, String address, String phone, String startTime, String endTime,
                      String startBreaktime, String endBreaktime, String pestControlYn, String lastPestControlDate) {


        super(user, storeName, address, phone, startTime, endTime);

        this.startBreaktime = startBreaktime;
        this.endBreaktime = endBreaktime;
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
                dto.getStartBreaktime(),
                dto.getEndBreaktime(),
                dto.getPestControlYn(),
                dto.getLastPestControlDate()
        );
    }
}
