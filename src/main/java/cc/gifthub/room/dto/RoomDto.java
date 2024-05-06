package cc.gifthub.room.dto;

import cc.gifthub.room.domain.RoomEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String title;
    private String code;
    public static RoomDto from(RoomEntity room) {
        return RoomDto.builder()
                .id(room.getId())
                .code(room.getCode())
                .build();
    }
}
