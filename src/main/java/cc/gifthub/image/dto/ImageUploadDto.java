package cc.gifthub.image.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUploadDto {

    private Long category_id;
    private Long user_id;
    private Long room_id;

    @Builder
    public ImageUploadDto(Long category_id, Long user_id, Long room_id) {
        this.category_id = category_id;
        this.user_id = user_id;
        this.room_id = room_id;
    }
}
