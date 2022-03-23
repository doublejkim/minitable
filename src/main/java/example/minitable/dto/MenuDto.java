package example.minitable.dto;

import example.minitable.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class MenuDto {

    private Long menuId;
    private String menuName;
    private String menuDesc;

    public static MenuDto from(Menu menu) {
        return new MenuDto(
                menu.getId(),
                menu.getMenuName(),
                menu.getMenuDesc()
        );
    }
}
