package cc.gifthub.image.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageS3GetDto {
    private String url;

    @Builder
    public ImageS3GetDto(String url) {
        this.url = url;
    }


}
