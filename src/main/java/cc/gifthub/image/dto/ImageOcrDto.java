package cc.gifthub.image.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageOcrDto {
    private String barcode;
    private LocalDateTime expire;
    @Builder
    public ImageOcrDto(String barcode, LocalDateTime expire) {
        this.barcode = barcode;
        this.expire = expire;
    }
}
