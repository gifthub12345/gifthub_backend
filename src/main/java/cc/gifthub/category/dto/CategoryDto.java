package cc.gifthub.category.dto;

import cc.gifthub.category.domain.CategoryEntity;
import cc.gifthub.category.type.RoomCategory;
import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto {
    private Long id;
    private String title;
    //private String image;
    private RoomCategory category;

    public static CategoryDto from(CategoryEntity category) {
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                //.image(category.getImage())
                .build();
    }
}
