package cc.gifthub.room.dto;

import cc.gifthub.category.domain.CategoryEntity;
import cc.gifthub.category.dto.CategoryDto;
import cc.gifthub.category.type.RoomCategory;
import cc.gifthub.room.domain.RoomCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RoomCategoryDto {
    private Long id;
    private Long roomId;
    private CategoryDto category;

    public static RoomCategoryDto from(RoomCategoryEntity roomCategory) {
        return RoomCategoryDto.builder()
                .id(roomCategory.getId())
                .category(CategoryDto.from(roomCategory.getCategory()))
                .build();
    }
}
