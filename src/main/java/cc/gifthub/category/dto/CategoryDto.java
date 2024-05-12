package cc.gifthub.category.dto;

import lombok.*;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryDto {
    private String title;

    @Builder
    public CategoryDto(String title) {
        this.title = title;
    }



}
