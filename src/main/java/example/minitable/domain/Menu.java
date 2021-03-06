package example.minitable.domain;


import example.minitable.domain.store.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String menuName;

    private String menuDesc;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    // TODO : 추가 속성 확장 가능

    public Menu(Store store, String menuName, String menuDesc) {
        this.menuName = menuName;
        this.menuDesc = menuDesc;
        if(store != null) {
            addStore(store);
        }

    }

    public void addStore(Store store) {
        this.store = store;
        this.store.getMenus().add(this);
    }

    public void removeStore() {
        if(this.store != null) {
            this.store.getMenus().remove(this);
        }
        this.store = null;
    }

}
